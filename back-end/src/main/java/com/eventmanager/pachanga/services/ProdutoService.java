package com.eventmanager.pachanga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.dtos.ItemEstoqueTO;
import com.eventmanager.pachanga.dtos.ProdutoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.ProdutoFactory;
import com.eventmanager.pachanga.repositories.EstoqueRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.ProdutoRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@Service
@Transactional
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FestaRepository festaRepository;
	
	@Autowired
	private EstoqueRepository estoqueRepository;
	
	@Autowired
	GrupoRepository grupoRepository;

//add_____________________________________________________________________________________________________
	public Produto addProduto(ProdutoTO produtoTO, int codFesta) {		
		Produto produto = ProdutoFactory.getProduto(produtoTO);
		produto.setCodProduto(produtoRepository.getNextValMySequence());
		produto.setCodFesta(codFesta);
		
		this.validarFesta(codFesta);
		produtoRepository.save(produto);
		
		return produto;
	}
	
	public ItemEstoque addProdutoEstoque(ItemEstoqueTO itemEstoqueTO, int codProduto, int codEstoque) throws ValidacaoException {
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax();
		int quantidadeAtual = itemEstoqueTO.getQuantiadadeAtual();
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();
		
		Estoque estoque = this.validarEstoque(codEstoque);
		Produto produto = this.validarProduto(codProduto);

		if(estoque.getFesta().getCodFesta() != produto.getCodFesta()) throw new ValidacaoException("Produto não pertence a mesma festa do estoque");
		this.validarFesta(estoque.getFesta().getCodFesta());
		ItemEstoque itemEstoque = this.verificarProdutoEstoque(codEstoque, codProduto);
		if(itemEstoque != null) throw new ValidacaoException("Produto já cadastrado");
		if(quantidadeMax <= 0 || quantidadeMax < quantidadeAtual) throw new ValidacaoException("QMAXINV"); //quantidade máxima inválida
		if(porcentagemMin < 0) throw new ValidacaoException("PMININV"); //porcentagemMin inválida
		if(quantidadeAtual < 0) throw new ValidacaoException("QATUAINV"); //quantidadeAtual inválida
		
		produtoRepository.saveProdutoEstoque(codProduto, codEstoque, produto.getCodFesta(), quantidadeMax, quantidadeAtual, porcentagemMin);
		itemEstoque = new ItemEstoque();
		itemEstoque.setCodEstoque(codEstoque);
		itemEstoque.setCodFesta(produto.getCodFesta()); 
		itemEstoque.setCodProduto(codProduto);
		itemEstoque.setQuantidadeMax(quantidadeMax);   //eu sei, mal escrito
		itemEstoque.setQuantiadadeAtual(quantidadeAtual);
		itemEstoque.setPorcentagemMin(porcentagemMin);
		
		return itemEstoque;
	}

//remover_____________________________________________________________________________________________________
	public Produto removerProduto(int codProduto) {
		Produto produto = this.validarProduto(codProduto);
		
		List<Estoque> estoques = produtoRepository.findEstoquesComProduto(codProduto);
		if(estoques != null) throw new ValidacaoException("PRODEMUSO"); // PRODUTO EM USO
		
		produtoRepository.delete(produto);
		return produto;	
	}
	
	public ItemEstoque removerProdutoEstoque(int codProduto, int codEstoque) {
		ItemEstoque retorno = this.validarProdutoEstoque(codEstoque, codProduto);
		produtoRepository.deleteProdutoEstoque(codProduto, codEstoque);
		
		return retorno;	
	}

//editar_____________________________________________________________________________________________________
	public Produto editarProduto(ProdutoTO produtoTO) {
		Produto produto = this.validarProduto(produtoTO.getCodProduto());
		produto.setPrecoMedio(produtoTO.getPrecoMedio());
		produto.setMarca(produtoTO.getMarca());
		
		produtoRepository.save(produto);
		return produto;
	}
	
	public ItemEstoque editarProdutoEstoque(ItemEstoqueTO itemEstoqueTO) {
		int codProduto = itemEstoqueTO.getCodProduto();
		int codEstoque = itemEstoqueTO.getCodEstoque();
		ItemEstoque retorno = this.validarProdutoEstoque(codEstoque, codProduto);
		
		retorno.setQuantidadeMax(itemEstoqueTO.getQuantidadeMax());
		retorno.setPorcentagemMin(itemEstoqueTO.getPorcentagemMin());
		retorno.setQuantiadadeAtual(itemEstoqueTO.getQuantiadadeAtual());
		
		if(retorno.getQuantidadeMax() < 0 || retorno.getQuantidadeMax() < retorno.getQuantiadadeAtual()) throw new ValidacaoException("QMAXINV"); //quantidade máxima inválida
		if(retorno.getPorcentagemMin() < 0)  throw new ValidacaoException("PMININV"); //porcentagemMin inválida
		if(retorno.getQuantiadadeAtual() < 0)  throw new ValidacaoException("QATUAINV"); //quantidadeAtual inválida
		
		produtoRepository.updateProdutoEstoque(codProduto, codEstoque, retorno.getQuantidadeMax(), retorno.getQuantiadadeAtual(), retorno.getPorcentagemMin());
		return retorno;
	}

//baixa/recarga_____________________________________________________________________________________________________

	public ItemEstoque baixaProduto(int codProduto, int codEstoque, int quantidade) {
		if(quantidade <=0 ) throw new ValidacaoException("OPERAINV"); //quantidade inválida;
		
		ItemEstoque itemEstoque = this.validarProdutoEstoque(codEstoque, codProduto);
		int quantidadeAtual = itemEstoque.getQuantiadadeAtual() - quantidade;
		if(quantidadeAtual < 0) throw new ValidacaoException("QATUAINV"); //quantidadeAtual inválida
		
		produtoRepository.updateProdutoEstoqueQuantidade(codProduto, codEstoque, quantidadeAtual);
		itemEstoque.setQuantiadadeAtual(quantidadeAtual);
		return itemEstoque;
	}
	
	public ItemEstoque recargaProduto(int codProduto, int codEstoque, int quantidade) {
		if(quantidade <=0 ) throw new ValidacaoException("OPERAINV"); //quantidade inválida;
		
		ItemEstoque itemEstoque = this.validarProdutoEstoque(codEstoque, codProduto);
		int quantidadeAtual = itemEstoque.getQuantiadadeAtual() + quantidade;
		if(quantidadeAtual > itemEstoque.getQuantidadeMax()) throw new ValidacaoException("QATUAINV"); //quantidade inválida
		
		produtoRepository.updateProdutoEstoqueQuantidade(codProduto, codEstoque, quantidadeAtual);
		itemEstoque.setQuantiadadeAtual(quantidadeAtual);
		return itemEstoque;
	}

//gets_____________________________________________________________________________________________________
	/*
	private Produto getProdutoById(int codProduto) {
		//Produto produto = produtoRepository.findById(codProduto);
		return produtoRepository.findById(codProduto);
	}
	*/
	
//validadores________________________________________________________________________________________________
	public Festa validarFesta(int codFesta) {
		Festa festa = festaRepository.findById(codFesta);
		if(festa == null) throw new ValidacaoException("FSTANFOU");
		return festa;
	}
	
	public Produto validarProduto(int codProduto) {
		Produto produto = produtoRepository.findById(codProduto);
		if(produto == null) throw new ValidacaoException("PRODNFOU");
		return produto;
	}
	
	public Estoque validarEstoque(int codEstoque) {
		Estoque estoque = estoqueRepository.findByEstoqueCodEstoque(codEstoque);
		if(estoque == null) {
			throw new ValidacaoException("ESTONFOU");// estoque não encontrado
		}
		return estoque;
	}
	
	public ItemEstoque validarProdutoEstoque(int codEstoque, int codProduto) {
		this.validarEstoque(codEstoque);
		this.validarProduto(codProduto);
		ItemEstoque itemEstoque = produtoRepository.findItemEstoque(codEstoque, codProduto);
		if(itemEstoque == null) {
			throw new ValidacaoException("ESTNPRD");// estoque não possui o produto
		}
		return itemEstoque;
	}
	
	public ItemEstoque verificarProdutoEstoque(int codEstoque, int codProduto) {
		ItemEstoque itemEstoque = null;
		
		this.validarEstoque(codEstoque);
		this.validarProduto(codProduto);
		itemEstoque = produtoRepository.findItemEstoque(codEstoque, codProduto);
		return itemEstoque;
	}
	
	public void validarUsuarioPorFesta(int codUsuario, int codFesta) {
		Grupo grupo = grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, TipoPermissao.ADDMEMBE.getCodigo());
		if(grupo == null) {
			throw new ValidacaoException("USESPERM");// usuário sem permissão
		}
	}
	
	public void validarUsuarioPorEstoque(int codUsuario, int codEstoque) {
		Estoque estoque = this.validarEstoque(codEstoque);
		this.validarUsuarioPorFesta(codUsuario, estoque.getFesta().getCodFesta());
	}
	
}
