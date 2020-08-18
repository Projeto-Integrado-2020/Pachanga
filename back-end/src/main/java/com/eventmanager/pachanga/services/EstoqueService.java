package com.eventmanager.pachanga.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.dtos.EstoqueTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.EstoqueFactory;
import com.eventmanager.pachanga.repositories.EstoqueRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.ProdutoRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@Service
@Transactional
public class EstoqueService {

	@Autowired
	private FestaRepository festaRepository;

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private EstoqueRepository estoqueRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstoqueFactory estoqueFactory;


	public List<Estoque> estoquesFesta(int codFesta, int idUsuario){
		this.validarFesta(codFesta);
		this.validarUsuario(idUsuario, codFesta, TipoPermissao.VISUESTO.getCodigo());
		return estoqueRepository.findEstoqueByCodFesta(codFesta);
	}

	public List<Estoque> estoquesFestaComProdutos(int codFesta, int idUsuario){
		this.validarFesta(codFesta);
		this.validarUsuario(idUsuario, codFesta, TipoPermissao.VISUESTO.getCodigo());
		List<Estoque> estoques = estoqueRepository.findEstoqueByCodFesta(codFesta);
		for(Estoque estoque : estoques) {
			Set<Produto> produtosEstoque = new HashSet<>();
			produtosEstoque.addAll(produtoRepository.findProdutosPorEstoque(estoque.getCodEstoque()));
//			estoque.setProdutos(produtosEstoque);
		}
		
		return estoques;
	}

	public Estoque addEstoque(String nomeEstoque, int codFesta, boolean principal, int codUsuario) {
		Festa festa = this.validarFesta(codFesta);
		Estoque estoque = estoqueFactory.getEstoque(nomeEstoque, principal);
		this.validarUsuario(codUsuario, codFesta, TipoPermissao.CADAESTO.getCodigo());
		estoque.setCodEstoque(estoqueRepository.getNextValMySequence());
		estoque.setFesta(festa);
		estoqueRepository.save(estoque);
		return estoque;
	}

	public Estoque updateEstoque(EstoqueTO estoqueTo, int codFesta, int codUsuario) {
		this.validarFesta(codFesta);
		this.validarUsuario(codUsuario, codFesta, TipoPermissao.EDITESTO.getCodigo());
		Estoque estoque = this.validarEstoque(estoqueTo.getCodEstoque());
		Estoque estoqueNome = estoqueRepository.findByNomeEstoque(estoqueTo.getNomeEstoque());
		if(estoqueNome != null) {
			throw new ValidacaoException("ESTONOME");// estoque com o mesmo nome
		}
		estoque.setNomeEstoque(estoqueTo.getNomeEstoque());			
		estoqueRepository.save(estoque);
		return estoque;
	}

	public void deleteEstoque(int codEstoque, int codFesta, int codUsuario) {
		this.validarFesta(codFesta);
		this.validarUsuario(codUsuario, codFesta, TipoPermissao.DELEESTO.getCodigo());
		Estoque estoque = this.validarEstoque(codEstoque);
		if(estoque.isPrincipal()) {
			throw new ValidacaoException("ESTOPRIN");//estoque principal
		}
		estoqueRepository.delete(estoque);
	}

	private Estoque validarEstoque(int codEstoque) {
		Estoque estoque = estoqueRepository.findByEstoqueCodEstoque(codEstoque);
		if(estoque == null) {
			throw new ValidacaoException("ESTONFOU");// estoque não encontrado
		}
		return estoque;
	}

	private void validarUsuario(int idUsuario, int codFesta, int tipoPermissao) {
		Grupo grupo = grupoRepository.findGrupoPermissaoUsuario(codFesta, idUsuario, tipoPermissao);
		if(grupo == null) {
			throw new ValidacaoException("USESPERM");// usuário sem permissão
		}

	}

	private Festa validarFesta(int codFesta) {
		Festa festa = festaRepository.findById(codFesta);
		if(festa == null) {
			throw new ValidacaoException("FESTNFOU");// festa não encontrada
		}
		return festa;
	}

}
