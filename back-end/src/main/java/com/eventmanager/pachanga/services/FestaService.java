package com.eventmanager.pachanga.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.utils.ObjectUtils;
import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.domains.CategoriasFesta;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.ConviteFestaTO;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.ItemEstoqueTO;
import com.eventmanager.pachanga.dtos.NotificacaoMudancaStatusTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.ConviteFestaFactory;
import com.eventmanager.pachanga.factory.FestaFactory;
import com.eventmanager.pachanga.factory.NotificacaoMudancaStatusFactory;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaFluxoRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaRepository;
import com.eventmanager.pachanga.repositories.CategoriaRepository;
import com.eventmanager.pachanga.repositories.CategoriasFestaRepository;
import com.eventmanager.pachanga.repositories.CupomRepository;
import com.eventmanager.pachanga.repositories.DadoBancarioRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.InfoIntegracaoRepository;
import com.eventmanager.pachanga.repositories.QuestionarioFormsRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoCategoria;
import com.eventmanager.pachanga.tipo.TipoGrupo;
import com.eventmanager.pachanga.tipo.TipoNotificacao;
import com.eventmanager.pachanga.tipo.TipoPermissao;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.utils.CloudinaryUtils;

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
	private CategoriasFestaRepository categoriasFestaRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private AreaSegurancaProblemaFluxoRepository areaProblemaFluxoRepository;
	
	@Autowired
	private AreaSegurancaProblemaService areaSegurancaProblemaService;
	
	@Autowired
	private AreaSegurancaRepository areaRepository;
	
	@Autowired
	private CupomRepository cupomRepository;
	
	@Autowired
	private DadoBancarioRepository dadoBancarioRepository;
	
	@Autowired
	private InfoIntegracaoRepository infoIntegracaoRepository;
	
	@Autowired
	private QuestionarioFormsRepository questionarioFormsRepository;

	@Autowired
	private NotificacaoMudancaStatusFactory notificacaoMudancaStatusFactory;

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private EstoqueService estoqueService;
	
	@Autowired
	private LoteService loteService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private FestaFactory festaFactory;

	@Autowired
	private ConviteFestaFactory conviteFestaFactory;

	@Autowired
	private NotificacaoService notificacaoService;
	
	@Autowired
	private Environment env;
	
	private static final String AMBIENTE = "spring.profiles.active";

	public List<Festa> procurarFestas() {
		return festaRepository.findAll();
	}

	public List<Festa> procurarFestasPorUsuario(int idUser) {
		this.validarUsuarioFesta(idUser, 0);
		Usuario usuario = usuarioRepository.findById(idUser);
		return festaRepository.findByUsuarios(usuario.getCodUsuario());
	}

	public List<Festa> procurarFestasComLotesCompraveis() {
		return festaRepository.findAllComLotesCompraveis();
	}

	public Festa addFesta(FestaTO festaTo, int idUser, MultipartFile imagem) throws IOException {
		this.validarUsuarioFesta(idUser, 0);
		Usuario usuario = usuarioRepository.findById(idUser);
		festaTo.setCodFesta(festaRepository.getNextValMySequence());
		festaTo.setStatusFesta(TipoStatusFesta.PREPARACAO.getValor());
		this.validarFesta(festaTo);
		this.validacaoCategorias(festaTo.getCodPrimaria(), festaTo.getCodSecundaria());

		Festa festa = festaFactory.getFesta(festaTo, imagem);
		this.adicionarImagemCloudnary(imagem, festa);
		festaRepository.save(festa);
		Grupo grupo = grupoService.addGrupo(festa.getCodFesta(), TipoGrupo.ORGANIZADOR.getValor(), true, null);
		grupoRepository.saveUsuarioGrupo(usuario.getCodUsuario(), grupo.getCodGrupo());
		estoqueService.addEstoque("Principal", festa.getCodFesta(), true, idUser);
		this.criacaoCategoriaFesta(festaTo);
		return festa;
	}

	private void criacaoCategoriaFesta(FestaTO festaTo) {
		this.validarCategoria(festaTo.getCodPrimaria());
		categoriasFestaRepository.addCategoriasFesta(festaTo.getCodFesta(), festaTo.getCodPrimaria(),
				TipoCategoria.PRIMARIO.getDescricao());
		if (festaTo.getCodSecundaria() != 0) {
			this.validarCategoria(festaTo.getCodSecundaria());
			categoriasFestaRepository.addCategoriasFesta(festaTo.getCodFesta(), festaTo.getCodSecundaria(),
					TipoCategoria.SECUNDARIO.getDescricao());
		}
	}

	public Usuario validarUsuarioFesta(int idUsuario, int idFesta) {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		if (usuario == null) {
			throw new ValidacaoException("USERNFOU");
		}
		if (idFesta != 0) {
			usuario = usuarioRepository.findBycodFestaAndUsuario(idFesta, idUsuario);
			if (usuario == null) {
				throw new ValidacaoException("USERNFES");// usuário não relacionado a festa
			}
		}
		return usuario;
	}

	public void deleteFesta(int idFesta, int idUser) throws IOException {
		validarPermissaoUsuario(idUser, idFesta, TipoPermissao.DELEFEST.getCodigo());
		Festa festa = this.validarFestaExistente(idFesta);

		areaSegurancaProblemaService.deleteByFesta(idFesta);
		areaRepository.deleteByCodFesta(idFesta);
		areaProblemaFluxoRepository.deleteByCodFesta(idFesta);

		grupoService.deleteCascade(idFesta, idUser);
		Set<CategoriasFesta> categorias = categoriasFestaRepository.findCategoriasFesta(idFesta);
		categoriasFestaRepository.deleteAll(categorias);

		estoqueService.deleteCascade(idFesta);
		loteService.deleteCascade(festa);

		questionarioFormsRepository.deleteByCodFesta(idFesta);
		infoIntegracaoRepository.deleteByCodFesta(idFesta);
		cupomRepository.deleteByCodFesta(idFesta);
		dadoBancarioRepository.deleteByCodFesta(idFesta);
		festaRepository.deleteById(idFesta);
		CloudinaryUtils.getCloudinaryCredentials().uploader().destroy(env.getProperty(AMBIENTE) + "/" + festa.getNomeFesta(), ObjectUtils.emptyMap());
	}

	public Festa updateFesta(FestaTO festaTo, int idUser, MultipartFile imagem) throws IOException {
		Festa festa = validarFestaExistente(festaTo.getCodFesta());

		this.adicionarImagemCloudnary(imagem, festa);

		this.validarPermissaoUsuario(idUser, festaTo.getCodFesta(), TipoPermissao.EDITDFES.getCodigo());
		this.validarFesta(festaTo);
		Festa festaMudanca = validarMudancas(festaTo, festa);
		festaRepository.save(festaMudanca);
		return festaMudanca;
	}

	private Festa validarMudancas(FestaTO festaTo, Festa festa) {
		mudarCategoriaFesta(festa, festaTo);
		if (!festa.getNomeFesta().equals(festaTo.getNomeFesta())) {
			festa.setNomeFesta(festaTo.getNomeFesta());
		}
		if (!festa.getDescricaoFesta().equals(festaTo.getDescricaoFesta())) {
			festa.setDescricaoFesta(festaTo.getDescricaoFesta());
		}
		if (!festa.getCodEnderecoFesta().equals(festaTo.getCodEnderecoFesta())) {
			festa.setCodEnderecoFesta(festaTo.getCodEnderecoFesta());
		}
		if (festa.getHorarioInicioFesta().compareTo(festaTo.getHorarioInicioFesta()) != 0) {
			festa.setHorarioInicioFesta(festaTo.getHorarioInicioFesta());
		}
		if (festa.getHorarioFimFesta().compareTo(festaTo.getHorarioFimFesta()) != 0) {
			festa.setHorarioFimFesta(festaTo.getHorarioFimFesta());
		}
		if (!festa.getOrganizador().equals(festaTo.getOrganizador())) {
			festa.setOrganizador(festaTo.getOrganizador());
		}
		if (!festa.getDescOrganizador().equals(festaTo.getDescOrganizador())) {
			festa.setDescOrganizador(festaTo.getDescOrganizador());
		}
		return festa;
	}

	private Festa mudarCategoriaFesta(Festa festa, FestaTO festaTo) {
		this.validacaoCategorias(festaTo.getCodPrimaria(), festaTo.getCodSecundaria());
		CategoriasFesta categoriasFesta = categoriasFestaRepository
				.findCategoriasFestaTipoCategoria(festa.getCodFesta(), TipoCategoria.PRIMARIO.getDescricao());
		if (TipoCategoria.PRIMARIO.getDescricao().equals(categoriasFesta.getTipCategoria())
				&& categoriasFesta.getCategoria().getCodCategoria() != festaTo.getCodPrimaria()) {
			Categoria categoria = validarCategoria(festaTo.getCodPrimaria());
			categoriasFestaRepository.delete(categoriasFesta);
			categoriasFesta.setCategoria(categoria);
			categoriasFestaRepository.addCategoriasFesta(categoriasFesta.getFesta().getCodFesta(),
					categoriasFesta.getCategoria().getCodCategoria(), TipoCategoria.PRIMARIO.getDescricao());
		}
		categoriasFesta = categoriasFestaRepository.findCategoriasFestaTipoCategoria(festa.getCodFesta(),
				TipoCategoria.SECUNDARIO.getDescricao());
		Categoria categoria = categoriaRepository.findByCodCategoria(festaTo.getCodSecundaria());
		if (categoriasFesta != null) {
			categoriasFestaRepository.delete(categoriasFesta);
			if (categoria != null) {
				categoriasFesta.setCategoria(categoria);
				categoriasFestaRepository.addCategoriasFesta(categoriasFesta.getFesta().getCodFesta(),
						categoriasFesta.getCategoria().getCodCategoria(), TipoCategoria.SECUNDARIO.getDescricao());
			}
		} else if (categoria != null) {
			categoriasFestaRepository.addCategoriasFesta(festa.getCodFesta(), festaTo.getCodSecundaria(),
					TipoCategoria.SECUNDARIO.getDescricao());
		}
		return festa;
	}

	private Categoria validarCategoria(int codCategoria) {
		Categoria categoria = categoriaRepository.findByCodCategoria(codCategoria);
		if (categoria == null) {
			throw new ValidacaoException("CATNFOUN");
		}
		return categoria;
	}

	private void validacaoCategorias(int codCategoriaPrincipal, int codCategoriaSecundario) {
		if (codCategoriaPrincipal == codCategoriaSecundario) {
			throw new ValidacaoException("FESTMCAT"); // categoria principal e secundaria identicas
		}
	}

	private void validarPermissaoUsuario(int idUser, int idFesta, int codPermissao) {
		List<Grupo> grupos = grupoRepository.findGrupoPermissaoUsuario(idFesta, idUser, codPermissao);
		if (grupos.isEmpty()) {
			throw new ValidacaoException("USERSPER");// Usuário sem permissão de fazer essa ação
		}
		Festa festa = festaRepository.findByCodFesta(idFesta);
		boolean festaFinalizadaDelete = TipoStatusFesta.FINALIZADO.getValor().equals(festa.getStatusFesta())
				&& TipoPermissao.DELEFEST.getCodigo() == codPermissao;
		if (!TipoStatusFesta.PREPARACAO.getValor().equals(festa.getStatusFesta()) && !festaFinalizadaDelete) {
			throw new ValidacaoException("FESTINIC");// Não pode ser feita essa operação com a festa iniciada
		}
	}

	private void validarFesta(FestaTO festaTo) {
		if (festaTo.getHorarioFimFesta().isBefore(festaTo.getHorarioInicioFesta())
				|| Duration.between(festaTo.getHorarioInicioFesta(), festaTo.getHorarioFimFesta()).isZero()
				|| !notificacaoService.getDataAtual().isBefore(festaTo.getHorarioInicioFesta())) {
			throw new ValidacaoException("DATEINFE");// data inicial ou final incorreta
		}
		Festa festa = festaRepository.findByNomeFesta(festaTo.getNomeFesta());
		if (festa != null && festa.getCodFesta() != festaTo.getCodFesta()
				&& festa.getNomeFesta().equals(festaTo.getNomeFesta())) {
			throw new ValidacaoException("FESTNOME");// outra festa está usando o msm nome
		}
		if (festaTo.getCodEnderecoFesta() == null) {
			throw new ValidacaoException("FESTNEND");// Festa sem código de endereço
		}
		if (festaTo.getCodPrimaria() == 0) {
			throw new ValidacaoException("FESTSCAT");// festa sem categoria primaria
		}
	}

	public Festa procurarFesta(int idFesta, int idUsuario) {
		this.validarUsuarioFesta(idUsuario, idFesta);
		return festaRepository.findByCodFesta(idFesta);
	}

	public Festa procurarDadosPublicosFesta(int idFesta) {
		return festaRepository.findByCodFesta(idFesta);
	}

	public String funcionalidadeFesta(int codFesta, int codUsuario) {
		List<String> funcionalidades = grupoRepository.findFuncionalidade(codFesta, codUsuario);
		StringBuilder listFuncionalidade = new StringBuilder();
		for (String funcionalidade : funcionalidades) {
			listFuncionalidade.append(funcionalidade + ",");
		}
		listFuncionalidade.delete(listFuncionalidade.length() - 1, listFuncionalidade.length());
		return listFuncionalidade.toString();
	}

	public Festa mudarStatusFesta(int idFesta, String statusFesta, int idUsuario) {
		String statusFestaMaiusculo = statusFesta.toUpperCase();
		if (!TipoStatusFesta.INICIADO.getValor().equals(statusFestaMaiusculo)
				&& !TipoStatusFesta.FINALIZADO.getValor().equals(statusFestaMaiusculo)
				&& !TipoStatusFesta.PREPARACAO.getValor().equals(statusFestaMaiusculo)) {
			throw new ValidacaoException("STATERRA"); // status errado
		}
		this.validarUsuarioFesta(idUsuario, idFesta);
		Festa festa = festaRepository.findByCodFesta(idFesta);
		if (statusFestaMaiusculo.equals(festa.getStatusFesta())) {
			throw new ValidacaoException("STANMUDA");// status não foi alterado
		}
		if (TipoStatusFesta.PREPARACAO.getValor().equals(festa.getStatusFesta())
				&& TipoStatusFesta.FINALIZADO.getValor().equals(statusFestaMaiusculo)) {
			throw new ValidacaoException("FSTANINI");// festa precisa estar iniciada para fazer essa ação
		}
		if(TipoStatusFesta.FINALIZADO.getValor().equals(statusFestaMaiusculo)) {
			festa.setHorarioFimFestaReal(notificacaoService.getDataAtual());
		}else {
			festa.setHorarioFimFestaReal(null);
		}
		festa.setStatusFesta(statusFestaMaiusculo);
		festaRepository.save(festa);
		List<Usuario> usuarios = usuarioRepository.findByIdFesta(idFesta);
		for (Usuario usuario : usuarios) {
			notificacaoService.inserirNotificacaoUsuario(usuario.getCodUsuario(), TipoNotificacao.STAALTER.getCodigo(),
					notificacaoService.criarMensagemAlteracaoStatusFesta(idFesta, statusFestaMaiusculo));
			if (TipoStatusFesta.FINALIZADO.getValor().equals(festa.getStatusFesta())) {
				notificacaoService.deleteNotificacao(usuario.getCodUsuario(),
						TipoNotificacao.ESTBAIXO.getValor() + "?" + idFesta);
				notificacaoService.deletarNotificacaoGrupo(TipoNotificacao.ESTBAIXO.getValor() + "?" + idFesta);
			}
		}
		return festa;
	}

	public ConviteFestaTO procurarFestaConvidado(Integer codGrupo, Integer codConvidado) {
		Festa festa = festaRepository.findFestaByCodConvidadoAndCodGrupo(codConvidado, codGrupo);
		if (festa == null) {
			throw new ValidacaoException("FESTNFOU");
		}
		Grupo grupo = this.validarGrupo(codGrupo);
		return conviteFestaFactory.getConviteFestaTO(festa, grupo);
	}

	public Grupo validarGrupo(int codGrupo) {
		Grupo grupo = grupoRepository.findById(codGrupo);
		if (grupo == null) {
			throw new ValidacaoException("GRUPNFOU");
		}
		return grupo;
	}

	public void validarFestaFinalizada(int codFesta) {
		Festa festa = festaRepository.findByCodFesta(codFesta);
		if (TipoStatusFesta.FINALIZADO.getValor().equals(festa.getStatusFesta())) {
			throw new ValidacaoException("FESTFINA"); // festa finalizada não pode alterar estoque/produto
		}
	}

	public void validarFestaInicializadaPrep(int codFesta) {
		Festa festa = festaRepository.findByCodFesta(codFesta);
		if (TipoStatusFesta.PREPARACAO.getValor().equals(festa.getStatusFesta())) {
			throw new ValidacaoException("FESTNINI"); // festa fora do estado inicializada, não pode fazer baixa
		}
	}

	public void validarFestaInicializadaFinal(int codFesta) {
		Festa festa = festaRepository.findByCodFesta(codFesta);
		if (TipoStatusFesta.FINALIZADO.getValor().equals(festa.getStatusFesta())) {
			throw new ValidacaoException("FESTNINI");
		}
	}

	public void validarProdEstoqueIniciada(ItemEstoqueTO itemEstoqueTO, int codFesta) {
		int codProduto = itemEstoqueTO.getCodProduto();
		int codEstoque = itemEstoqueTO.getCodEstoque();
		ItemEstoque itemEstoque = produtoService.validarProdutoEstoque(codEstoque, codProduto);
		Festa festa = festaRepository.findByCodFesta(codFesta);
		if (TipoStatusFesta.INICIADO.getValor().equals(festa.getStatusFesta())
				&& itemEstoqueTO.getQuantidadeAtual() != itemEstoque.getQuantidadeAtual()) {
			throw new ValidacaoException("QNTATLDF"); // quantidade atual diferente tentando ser editada com festa
														// inicializada
		}
	}

	public Festa validarFestaExistente(int codFesta) {
		Festa festa = festaRepository.findByCodFesta(codFesta);
		if (festa == null) {
			throw new ValidacaoException("FESTNFOU");
		}
		return festa;
	}

	public NotificacaoMudancaStatusTO getNotificacaoMudancaStatus(int codFesta, String StatusFestaNotificacao) {
		Festa festa = validarFestaExistente(codFesta);
		return notificacaoMudancaStatusFactory.getNotificacaoMudancaStatus(festa, StatusFestaNotificacao);
	}

	private void adicionarImagemCloudnary(MultipartFile imagem, Festa festa) throws IOException {
		if (imagem == null) {
			CloudinaryUtils.getCloudinaryCredentials().uploader().destroy(env.getProperty(AMBIENTE) + "/" + festa.getNomeFesta(), ObjectUtils.emptyMap());
			festa.setImagem(null);
			festa.setUrlImagem(null);
		} else {
			File imagemUpload = new File(festa.getNomeFesta());
			imagemUpload.createNewFile();
			FileOutputStream fos = new FileOutputStream(imagemUpload);
			fos.write(imagem.getBytes());
			fos.close();

			Map<?,?> uploadImagem = CloudinaryUtils.getCloudinaryCredentials().uploader().upload(imagemUpload,
					ObjectUtils.asMap("public_id", env.getProperty(AMBIENTE) + "/" + festa.getNomeFesta()));// quando for feito os testes em desenvolvimento trocar para dev o prod
			
			festa.setImagem(imagem.getBytes());
			festa.setUrlImagem(uploadImagem.get("secure_url").toString());
		}
	}

}
