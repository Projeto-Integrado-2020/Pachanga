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
import com.eventmanager.pachanga.factory.ItemEstoqueFactory;
import com.eventmanager.pachanga.factory.ProdutoFactory;
import com.eventmanager.pachanga.repositories.EstoqueRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.ItemEstoqueRepository;
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
	private GrupoRepository grupoRepository;

	@Autowired
	private ProdutoFactory produtoFactory;

	@Autowired
	private ItemEstoqueFactory itemEstoqueFactory;

	@Autowired
	private ItemEstoqueRepository itemEstoqueRepository;

	//add_____________________________________________________________________________________________________
	public Produto addProduto(ProdutoTO produtoTO, Integer codFesta, Integer idUsuarioPermissao) {
		this.validarUsuarioPorFesta(codFesta,idUsuarioPermissao, TipoPermissao.CADAESTO.getCodigo());
		this.validarProduto(produtoTO.getMarca(), produtoTO.getCodFesta());
		Produto produto = produtoFactory.getProduto(produtoTO);
		produto.setCodProduto(produtoRepository.getNextValMySequence());
		produto.setCodFesta(codFesta);
		this.validarFesta(codFesta);
		produtoRepository.save(produto);
		return produto;
	}

	public ItemEstoque addProdutoEstoque(ItemEstoqueTO itemEstoqueTO, Integer codEstoque, Integer idUsuarioPermissao){
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax();
		int quantidadeAtual = itemEstoqueTO.getQuantidadeAtual();
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();
		
		this.validarUsuarioPorEstoque(idUsuarioPermissao, codEstoque, TipoPermissao.CADAESTO.getCodigo());

		ItemEstoque produtoEstoqueExistentes =  this.validarEstoqueProduto(codEstoque, itemEstoqueTO.getCodProduto());
		
		this.validarFesta(itemEstoqueTO.getCodFesta());
		
		this.verificarProdutoEstoque(codEstoque, itemEstoqueTO.getCodProduto());

		this.validaQuantAndPorcent(quantidadeMax, quantidadeAtual, porcentagemMin);

		ItemEstoque itemEstoque = itemEstoqueFactory.getItemEstoque(itemEstoqueTO, produtoEstoqueExistentes.getProduto(), produtoEstoqueExistentes.getEstoque());

		itemEstoqueRepository.save(itemEstoque);

		return itemEstoque;
	}

	//remover_____________________________________________________________________________________________________
	public void removerProduto(Integer idUsuarioPermissao, Integer codFesta, Integer codProduto) {
		Produto produto = this.validarProduto(codProduto);
		
		this.validarUsuarioPorFesta(codFesta, idUsuarioPermissao, TipoPermissao.DELMESTO.getCodigo());

		List<Produto> produtos = produtoRepository.findEstoquesComProduto(codProduto);
		if(!produtos.isEmpty()) { 
			throw new ValidacaoException("PRODEUSO"); // PRODUTO EM USO
		}

		produtoRepository.delete(produto);
	}

	public void removerProdutoEstoque(Integer codProduto, Integer codEstoque, Integer idUsuarioPermissao) {
		this.validarUsuarioPorEstoque(idUsuarioPermissao, codEstoque, TipoPermissao.DELMESTO.getCodigo());
		this.validarProdutoEstoque(codEstoque, codProduto);
		produtoRepository.deleteProdutoEstoque(codProduto, codEstoque);
	}

	//editar_____________________________________________________________________________________________________
	public Produto editarProduto(ProdutoTO produtoTO, Integer idUsuarioPermissao) {
		this.validarUsuarioPorFesta(produtoTO.getCodFesta(), idUsuarioPermissao, TipoPermissao.EDIMESTO.getCodigo());
		Produto produto = this.validarProduto(produtoTO.getCodProduto());
		produto.setPrecoMedio(produtoTO.getPrecoMedio());
		this.validarProduto(produtoTO.getMarca(), produtoTO.getCodFesta());
		produto.setMarca(produtoTO.getMarca());
		produtoRepository.save(produto);
		return produto;
	}

	public ItemEstoque editarProdutoEstoque(ItemEstoqueTO itemEstoqueTO, Integer idUsuarioPermissao) {
		this.validarUsuarioPorEstoque(idUsuarioPermissao, itemEstoqueTO.getCodEstoque(), TipoPermissao.EDIMESTO.getCodigo());
		int codProduto = itemEstoqueTO.getCodProduto();
		int codEstoque = itemEstoqueTO.getCodEstoque();
		ItemEstoque retorno = this.validarProdutoEstoque(codEstoque, codProduto);

		retorno.setQuantidadeMax(itemEstoqueTO.getQuantidadeMax());
		retorno.setPorcentagemMin(itemEstoqueTO.getPorcentagemMin());

		this.validaQuantAndPorcent(retorno.getQuantidadeMax(), retorno.getQuantidadeAtual(), retorno.getPorcentagemMin());

		itemEstoqueRepository.save(retorno);

		return retorno;
	}

	//baixa/recarga_____________________________________________________________________________________________________

	public ItemEstoque baixaProduto(int codProduto, int codEstoque, int quantidade) {

		this.validarQuantInformada(quantidade);

		ItemEstoque itemEstoque = this.validarProdutoEstoque(codEstoque, codProduto);

		int quantidadeAtual = itemEstoque.getQuantidadeAtual() - quantidade;

		this.validaQuantAndPorcent(itemEstoque.getQuantidadeMax(), quantidadeAtual, itemEstoque.getPorcentagemMin());
		
		itemEstoque.setQuantidadeAtual(quantidadeAtual);
		
		itemEstoqueRepository.save(itemEstoque);
		
		return itemEstoque;
	}

	public ItemEstoque recargaProduto(int codProduto, int codEstoque, int quantidade) {

		this.validarQuantInformada(quantidade);

		ItemEstoque itemEstoque = this.validarProdutoEstoque(codEstoque, codProduto);
		int quantidadeAtual = itemEstoque.getQuantidadeAtual() + quantidade;
		if(quantidadeAtual > itemEstoque.getQuantidadeMax()) 
			throw new ValidacaoException("QATMMAXI"); //quantidade total fará que o estoque fique com uma quantidade maior que a máxima
		
		itemEstoque.setQuantidadeAtual(quantidadeAtual);
		
		itemEstoqueRepository.save(itemEstoque);

		return itemEstoque;
	}

	//gets_____________________________________________________________________________________________________

	public List<Produto> listaProduto(Integer codFesta, Integer codUsuario) {
		this.validarUsuarioPorFesta(codFesta, codUsuario, TipoPermissao.VISUESTO.getCodigo());
		return produtoRepository.findProdutoByCodFesta(codFesta);
	}

	public Produto getProduto(Integer codFesta, Integer codUsuario, Integer codProduto) {
		this.validarUsuarioPorFesta(codFesta, codUsuario, TipoPermissao.VISUESTO.getCodigo());
		return this.validarProduto(codProduto);
	}

	//validadores________________________________________________________________________________________________
	private Festa validarFesta(int codFesta) {
		Festa festa = festaRepository.findById(codFesta);
		if(festa == null) 
			throw new ValidacaoException("FSTANFOU");
		return festa;
	}

	private Produto validarProduto(int codProduto) {
		Produto produto = produtoRepository.findById(codProduto);
		if(produto == null) 
			throw new ValidacaoException("PRODNFOU");
		return produto;
	}

	private Estoque validarEstoque(int codEstoque) {
		Estoque estoque = estoqueRepository.findByEstoqueCodEstoque(codEstoque);
		if(estoque == null) {
			throw new ValidacaoException("ESTONFOU");// estoque não encontrado
		}
		return estoque;
	}

	private ItemEstoque validarProdutoEstoque(int codEstoque, int codProduto) {
		this.validarEstoque(codEstoque);
		this.validarProduto(codProduto);
		ItemEstoque itemEstoque = itemEstoqueRepository.findItemEstoque(codEstoque, codProduto);
		if(itemEstoque == null) {
			throw new ValidacaoException("ESTNPROD");// estoque não possui o produto
		}
		return itemEstoque;
	}

	private void verificarProdutoEstoque(int codEstoque, int codProduto) {
		if(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto) != null) {
			throw new ValidacaoException("PRODCADA");//Produto já cadastrado
		}
		
	}

	private void validarUsuarioPorFesta(int codFesta, int codUsuario, int tipoPermissao) { //TipoPermissao.CADAESTO.getCodigo()
		Grupo grupo = grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, tipoPermissao);
		if(grupo == null) {
			throw new ValidacaoException("USESPERM");// usuário sem permissão
		}
	}

	private void validarUsuarioPorEstoque(int codUsuario, int codEstoque, int tipoPermissao) {
		Estoque estoque = this.validarEstoque(codEstoque);
		this.validarUsuarioPorFesta(estoque.getFesta().getCodFesta(), codUsuario , tipoPermissao);
	}

	private ItemEstoque validarEstoqueProduto(int codEstoque, int codProduto) {
		Estoque estoque = this.validarEstoque(codEstoque);
		Produto produto = this.validarProduto(codProduto);

		if(estoque.getFesta().getCodFesta() != produto.getCodFesta()) 
			throw new ValidacaoException("PRODNEST");//Produto não pertence a mesma festa do estoque
		
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setEstoque(estoque);
		itemEstoque.setProduto(produto);
		return itemEstoque;
	}

	private void validarQuantInformada(int quantidade) {
		if(quantidade <= 0 ) {
			throw new ValidacaoException("OPERAINV"); //quantidade inválida
		}
	}

	private void validaQuantAndPorcent(int quantidadeMax, int quantidadeAtual, int porcentagemMin) {

		if(quantidadeMax <= 0 || quantidadeMax < quantidadeAtual) 
			throw new ValidacaoException("QMAXINV"); //quantidade máxima inválida

		if(porcentagemMin <= 0) 
			throw new ValidacaoException("PCMININV"); //porcentagemMin inválida

		if(quantidadeAtual < 0)
			throw new ValidacaoException("QATUAINV"); //quantidadeAtual inválida
	}
	
	private void validarProduto(String marca, int codFesta) {
		Produto produtoMarcaIgual = produtoRepository.findByMarca(marca, codFesta);
		if(produtoMarcaIgual != null) {
			throw new ValidacaoException("PROMIGUA");// produto com a mesma marca cadastrado na festa
		}
	}

}
