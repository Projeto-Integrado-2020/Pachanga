package com.eventmanager.pachanga.services;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.domains.CategoriasFesta;
import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.FestaFactory;
import com.eventmanager.pachanga.repositories.CategoriaRepository;
import com.eventmanager.pachanga.repositories.CategoriasFestaRepository;
import com.eventmanager.pachanga.repositories.ConvidadoRepository;
import com.eventmanager.pachanga.repositories.EstoqueRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.ProdutoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoCategoria;
import com.eventmanager.pachanga.tipo.TipoGrupo;
import com.eventmanager.pachanga.tipo.TipoPermissao;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@Service
@Transactional
public class FestaService {

	@Autowired
	private FestaRepository festaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private ConvidadoRepository convidadoRepository;

	@Autowired
	private CategoriasFestaRepository categoriasFestaRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired 
	private EstoqueRepository estoqueRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private EstoqueService estoqueService;

	public List<Festa> procurarFestas(){
		return festaRepository.findAll();
	}

	public List<Festa> procurarFestasPorUsuario(int idUser){
		this.validarUsuarioFesta(idUser, 0);
		Usuario usuario = usuarioRepository.findById(idUser);
		return festaRepository.findByUsuarios(usuario.getCodUsuario());
	}

	public Festa addFesta(FestaTO festaTo, int idUser) {
		this.validarUsuarioFesta(idUser, 0);
		Usuario usuario = usuarioRepository.findById(idUser);
		festaTo.setCodFesta(festaRepository.getNextValMySequence());
		festaTo.setStatusFesta(TipoStatusFesta.PREPARACAO.getValor());
		this.validarFesta(festaTo);
		this.validacaoCategorias(festaTo.getCodPrimaria(), festaTo.getCodSecundaria());
		Festa festa =  FestaFactory.getFesta(festaTo);
		festaRepository.save(festa);
		Grupo grupo = grupoService.addGrupo(festa.getCodFesta(), TipoGrupo.ORGANIZADOR.getValor(), true, null);
		grupoRepository.saveUsuarioGrupo(usuario.getCodUsuario(), grupo.getCodGrupo());
		estoqueService.addEstoque(festa.getNomeFesta(), festa.getCodFesta(), true, idUser);
		this.criacaoCategoriaFesta(festaTo);
		return festa;
	}

	private void criacaoCategoriaFesta(FestaTO festaTo) {
		this.validarCategoria(festaTo.getCodPrimaria());
		categoriasFestaRepository.addCategoriasFesta(festaTo.getCodFesta(), festaTo.getCodPrimaria(), TipoCategoria.PRIMARIO.getDescricao());
		if(festaTo.getCodSecundaria() != 0) {
			this.validarCategoria(festaTo.getCodSecundaria());
			categoriasFestaRepository.addCategoriasFesta(festaTo.getCodFesta(), festaTo.getCodSecundaria(), TipoCategoria.SECUNDARIO.getDescricao());
		}
	}

	private void validarUsuarioFesta(int idUsuario, int idFesta) {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		if(usuario == null) {
			throw new ValidacaoException("USERNFOU");
		}
		if(idFesta != 0) {
			usuario = usuarioRepository.findBycodFestaAndUsuario(idFesta, idUsuario);
			if(usuario == null) {
				throw new ValidacaoException("USERNFES");// usuário não relacionado a festa
			}
		}
	}

	public void deleteFesta(int idFesta, int idUser) {
		validarPermissaoUsuario(idUser, idFesta, TipoPermissao.DELEFEST.getCodigo());
		List<Grupo> grupos = grupoRepository.findGruposFesta(idFesta);
		List<Convidado> convidados = convidadoRepository.findConvidadosByCodFesta(idFesta);
		for(Grupo grupo : grupos) {
			for(Convidado conv : convidados) {
				grupoRepository.deleteConvidadoGrupo(conv.getCodConvidado(), grupo.getCodGrupo());
				Integer convxgrup = grupoRepository.existsConvidadoGrupo(conv.getCodConvidado());
				if(convxgrup == null) {
					convidadoRepository.deleteConvidado(conv.getCodConvidado());
				}
			}
			grupoRepository.deleteGrupo(grupo.getCodGrupo());
		}
		Set<CategoriasFesta> categorias = categoriasFestaRepository.findCategoriasFesta(idFesta);
		categoriasFestaRepository.deleteAll(categorias);
		
		List<Estoque> estoques = estoqueRepository.findEstoqueByCodFesta(idFesta);
		
		for(Estoque estoque : estoques) {
		estoqueRepository.deleteProdEstoque(idFesta, estoque.getCodEstoque());
		}
		produtoRepository.deleteProdFesta(idFesta);
		estoqueRepository.deleteEstoque(idFesta);
		festaRepository.deleteById(idFesta);
	}

	public Festa updateFesta(FestaTO festaTo, int idUser) {
		Festa festa = festaRepository.findById(festaTo.getCodFesta());
		if(festa == null) {
			throw new ValidacaoException("FESTNFOU");//festa nn encontrada
		}
		this.validarPermissaoUsuario(idUser, festaTo.getCodFesta(), TipoPermissao.EDITDFES.getCodigo());
		this.validarFesta(festaTo);
		Festa festaMudanca = validarMudancas(festaTo, festa);
		festaRepository.save(festaMudanca);
		return festaMudanca;
	}

	private Festa validarMudancas(FestaTO festaTo, Festa festa) {
		if(!festa.getNomeFesta().equals(festaTo.getNomeFesta())) {
			festa.setNomeFesta(festaTo.getNomeFesta());
		}
		if(!festa.getDescricaoFesta().equals(festaTo.getDescricaoFesta())) {
			festa.setDescricaoFesta(festaTo.getDescricaoFesta());
		}
		if(!festa.getCodEnderecoFesta().equals(festaTo.getCodEnderecoFesta())) {
			festa.setCodEnderecoFesta(festaTo.getCodEnderecoFesta());
		}
		if(festa.getHorarioInicioFesta().compareTo(festaTo.getHorarioInicioFesta()) != 0) {
			festa.setHorarioInicioFesta(festaTo.getHorarioInicioFesta());
		}
		if(festa.getHorarioFimFesta().compareTo(festaTo.getHorarioFimFesta()) != 0) {
			festa.setHorarioFimFesta(festaTo.getHorarioFimFesta());
		}
		if(!festa.getOrganizador().equals(festaTo.getOrganizador())) {
			festa.setOrganizador(festaTo.getOrganizador());
		}
		if(!festa.getDescOrganizador().equals(festaTo.getDescOrganizador())) {
			festa.setDescOrganizador(festaTo.getDescOrganizador());
		}
		mudarCategoriaFesta(festa, festaTo);
		return festa;
	}

	private Festa mudarCategoriaFesta(Festa festa, FestaTO festaTo) {
		this.validacaoCategorias(festaTo.getCodPrimaria(), festaTo.getCodSecundaria());
		CategoriasFesta categoriasFesta = categoriasFestaRepository.findCategoriasFestaTipoCategoria(festa.getCodFesta(), TipoCategoria.PRIMARIO.getDescricao());
		if(TipoCategoria.PRIMARIO.getDescricao().equals(categoriasFesta.getTipCategoria()) && categoriasFesta.getCategoria().getCodCategoria() != festaTo.getCodPrimaria()) {
			Categoria categoria = validarCategoria(festaTo.getCodPrimaria());
			categoriasFestaRepository.delete(categoriasFesta);
			categoriasFesta.setCategoria(categoria);
			categoriasFestaRepository.addCategoriasFesta(categoriasFesta.getFesta().getCodFesta(), categoriasFesta.getCategoria().getCodCategoria(), TipoCategoria.PRIMARIO.getDescricao());
		}
		categoriasFesta = categoriasFestaRepository.findCategoriasFestaTipoCategoria(festa.getCodFesta(), TipoCategoria.SECUNDARIO.getDescricao());
		Categoria categoria = categoriaRepository.findByCodCategoria(festaTo.getCodSecundaria());
		if(categoriasFesta != null) {
			categoriasFestaRepository.delete(categoriasFesta);
			if(categoria != null) {
				categoriasFesta.setCategoria(categoria);
				categoriasFestaRepository.addCategoriasFesta(categoriasFesta.getFesta().getCodFesta(), categoriasFesta.getCategoria().getCodCategoria(), TipoCategoria.SECUNDARIO.getDescricao());
			}
		}else if(categoria != null) {
			categoriasFestaRepository.addCategoriasFesta(festa.getCodFesta(), festaTo.getCodSecundaria(), TipoCategoria.SECUNDARIO.getDescricao());
		}
		return festa;
	}

	private Categoria validarCategoria(int codCategoria) {
		Categoria categoria = categoriaRepository.findByCodCategoria(codCategoria);
		if(categoria == null) {
			throw new ValidacaoException("CATNFOUN");
		}
		return categoria;
	}

	private void validacaoCategorias(int codCategoriaPrincipal, int codCategoriaSecundario) {
		if(codCategoriaPrincipal == codCategoriaSecundario) {
			throw new ValidacaoException("FESTMCAT"); //categoria principal e secundaria identicas
		}
	}

	private void validarPermissaoUsuario(int idUser, int idFesta, int codPermissao) {
		Grupo grupo = grupoRepository.findGrupoPermissaoUsuario(idFesta, idUser, codPermissao);
		if(grupo == null) {
			throw new ValidacaoException("USERSPER");//Usuário sem permissão de fazer essa ação
		}
		Festa festa = festaRepository.findById(idFesta);
		boolean festaFinalizadaDelete = TipoStatusFesta.FINALIZADO.getValor().equals(festa.getStatusFesta()) && TipoPermissao.DELEFEST.getCodigo() == codPermissao;
		if(!TipoStatusFesta.PREPARACAO.getValor().equals(festa.getStatusFesta())
				&& !festaFinalizadaDelete) {
			throw new ValidacaoException("FESTINIC");//Não pode ser feita essa operação com a festa iniciada
		}
	}

	private void validarFesta(FestaTO festaTo) {
		if(festaTo.getHorarioFimFesta().isBefore(festaTo.getHorarioInicioFesta()) || 
				Duration.between(festaTo.getHorarioInicioFesta(), festaTo.getHorarioFimFesta()).isZero()) {
			throw new ValidacaoException("DATEINFE");//data inicial ou final incorreta
		}
		Festa festa = festaRepository.findByNomeFesta(festaTo.getNomeFesta());
		if(festa != null && festa.getCodFesta() != festaTo.getCodFesta() && festa.getNomeFesta().equals(festaTo.getNomeFesta())) {
			throw new ValidacaoException("FESTNOME");//outra festa está usando o msm nome 
		}
		if(festaTo.getCodEnderecoFesta() == null) {
			throw new ValidacaoException("FESTNEND");//Festa sem código de endereço
		}
		if(festaTo.getCodPrimaria() == 0) {
			throw new ValidacaoException("FESTSCAT");//festa sem categoria primaria
		}
	}

	public Festa procurarFesta(int idFesta, int idUsuario) {
		this.validarUsuarioFesta(idUsuario, idFesta);
		return festaRepository.findByCodFesta(idFesta);
	}

	public String funcionalidadeFesta(int codFesta, int codUsuario) {
		List<String> funcionalidades = grupoRepository.findFuncionalidade(codFesta, codUsuario);
		StringBuilder listFuncionalidade = new StringBuilder();
		for(String funcionalidade: funcionalidades) {
			listFuncionalidade.append(funcionalidade + ",");
		}
		listFuncionalidade.delete(listFuncionalidade.length() -1, listFuncionalidade.length());
		return listFuncionalidade.toString();
	}

	public Festa mudarStatusFesta(int idFesta, String statusFesta, int idUsuario) {
		String statusFestaMaiusculo = statusFesta.toUpperCase();
		if(!TipoStatusFesta.INICIADO.getValor().equals(statusFestaMaiusculo) && !TipoStatusFesta.FINALIZADO.getValor().equals(statusFestaMaiusculo) && !TipoStatusFesta.PREPARACAO.getValor().equals(statusFestaMaiusculo)) {
			throw new ValidacaoException("STATERRA"); //status errado
		}
		this.validarUsuarioFesta(idUsuario, idFesta);
		Festa festa = festaRepository.findByCodFesta(idFesta);
		if(statusFestaMaiusculo.equals(festa.getStatusFesta())) {
			throw new ValidacaoException("STANMUDA");// status não foi alterado
		}
		if(TipoStatusFesta.PREPARACAO.getValor().equals(festa.getStatusFesta()) && TipoStatusFesta.FINALIZADO.getValor().equals(statusFestaMaiusculo) ) {
			throw new ValidacaoException("FSTANINI");// festa precisa estar iniciada para fazer essa ação
		}
		festaRepository.updateStatusFesta(statusFestaMaiusculo, idFesta);
		festa.setStatusFesta(statusFestaMaiusculo);
		return festa;
	}
}
