package com.eventmanager.pachanga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.ItemEstoqueFluxo;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.ItemEstoqueTO;
import com.eventmanager.pachanga.dtos.NotificacaoEstoqueTO;
import com.eventmanager.pachanga.dtos.ProdutoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.ItemEstoqueFactory;
import com.eventmanager.pachanga.factory.NotificacaoEstoqueTOFactory;
import com.eventmanager.pachanga.factory.ProdutoFactory;
import com.eventmanager.pachanga.repositories.EstoqueRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.ItemEstoqueFluxoRepository;
import com.eventmanager.pachanga.repositories.ItemEstoqueRepository;
import com.eventmanager.pachanga.repositories.ProdutoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoNotificacao;
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
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ProdutoFactory produtoFactory;

	@Autowired
	private ItemEstoqueFactory itemEstoqueFactory;

	@Autowired
	private ItemEstoqueRepository itemEstoqueRepository;

	@Autowired
	private NotificacaoService notificacaoService;

	@Autowired
	private NotificacaoEstoqueTOFactory notificacaoEstoqueTOFactory;

	@Autowired
	private FestaService festaService;

	@Autowired
	private ItemEstoqueFluxoRepository itemEstoqueFluxoRepository;

	// add_____________________________________________________________________________________________________
	public Produto addProduto(ProdutoTO produtoTO, Integer codFesta, Integer idUsuarioPermissao) {
		festaService.validarFestaFinalizada(codFesta);
		this.validarUsuarioPorFesta(codFesta, idUsuarioPermissao, TipoPermissao.CADAESTO.getCodigo());
		this.validarProduto(produtoTO.getMarca(), produtoTO.getCodFesta(), 0);
		this.validarQuantidadeDoseProduto(produtoTO);
		Produto produto = produtoFactory.getProduto(produtoTO);
		produto.setCodProduto(produtoRepository.getNextValMySequence());
		produto.setCodFesta(codFesta);
		this.validarFesta(codFesta);
		produtoRepository.save(produto);
		return produto;
	}

	public ItemEstoque addProdutoEstoque(ItemEstoqueTO itemEstoqueTO, Integer codEstoque, Integer idUsuarioPermissao) {
		festaService.validarFestaFinalizada(itemEstoqueTO.getCodFesta());
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax();
		int quantidadeAtual = itemEstoqueTO.getQuantidadeAtual();
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();

		this.validarUsuarioPorEstoque(idUsuarioPermissao, codEstoque, TipoPermissao.CADAESTO.getCodigo());

		ItemEstoque produtoEstoqueExistentes = this.validarEstoqueProduto(codEstoque, itemEstoqueTO.getCodProduto());

		this.validarFesta(itemEstoqueTO.getCodFesta());

		this.verificarProdutoEstoque(codEstoque, itemEstoqueTO.getCodProduto());

		this.validaQuantAndPorcent(quantidadeMax, quantidadeAtual, porcentagemMin);

		ItemEstoque itemEstoque = itemEstoqueFactory.getItemEstoque(itemEstoqueTO,
				produtoEstoqueExistentes.getProduto(), produtoEstoqueExistentes.getEstoque());

		itemEstoqueRepository.save(itemEstoque);

		return itemEstoque;
	}

	// remover_____________________________________________________________________________________________________
	public void removerProduto(Integer idUsuarioPermissao, Integer codFesta, Integer codProduto) {
		festaService.validarFestaFinalizada(codFesta);
		Produto produto = this.validarProduto(codProduto);

		this.validarUsuarioPorFesta(codFesta, idUsuarioPermissao, TipoPermissao.DELMESTO.getCodigo());

		List<Produto> produtos = produtoRepository.findEstoquesComProduto(codProduto);
		if (!produtos.isEmpty()) {
			throw new ValidacaoException("PRODEUSO"); // PRODUTO EM USO
		}

		produtoRepository.delete(produto);
	}

	public void removerProdutoEstoque(Integer codProduto, Integer codEstoque, Integer idUsuarioPermissao) {
		Estoque estoque = this.validarUsuarioPorEstoque(idUsuarioPermissao, codEstoque,
				TipoPermissao.DELMESTO.getCodigo());
		this.validarProdutoEstoque(codEstoque, codProduto);
		List<Grupo> grupos = grupoRepository.findGruposFesta(estoque.getFesta().getCodFesta());
		this.deleteNotificacoesItemEstoque(grupos, itemEstoqueRepository.findItemEstoque(codEstoque, codProduto));
		produtoRepository.deleteProdutoEstoque(codProduto, codEstoque);
	}

	// editar_____________________________________________________________________________________________________
	public Produto editarProduto(ProdutoTO produtoTO, Integer idUsuarioPermissao) {
		festaService.validarFestaFinalizada(produtoTO.getCodFesta());
		this.validarUsuarioPorFesta(produtoTO.getCodFesta(), idUsuarioPermissao, TipoPermissao.EDIMESTO.getCodigo());
		Produto produto = this.validarProduto(produtoTO.getCodProduto());
		produto.setPrecoMedio(produtoTO.getPrecoMedio());
		int quantidadeDoses = 1;
		boolean mudancaDose = false;
		if (produto.getDose().booleanValue()) {
			if (produtoTO.isDose()) {
				produto.setQuantDoses(produtoTO.getQuantDoses());
				this.validarQuantidadeDoseProduto(produtoTO);
			} else {
				produto.setDose(produtoTO.isDose());
				quantidadeDoses = produto.getQuantDoses();
				produto.setQuantDoses(0);
				mudancaDose = true;
			}
		} else if (!produto.getDose().booleanValue() && produto.getDose().booleanValue() != produtoTO.isDose()) {
			produto.setDose(produtoTO.isDose());
			produto.setQuantDoses(produtoTO.getQuantDoses());
			quantidadeDoses = produto.getQuantDoses();
			mudancaDose = true;
			this.validarQuantidadeDoseProduto(produtoTO);
		}

		List<ItemEstoque> itensEstoque = itemEstoqueRepository.findItensEstoqueByCodProduto(produto.getCodProduto());

		if (!itensEstoque.isEmpty()) {
			for (ItemEstoque itemEstoque : itensEstoque) {
				if (produto.getDose().booleanValue() && mudancaDose) {
					itemEstoque
					.setQuantidadeAtual(Math.multiplyExact(quantidadeDoses, itemEstoque.getQuantidadeAtual()));
					itemEstoque.setQuantidadeMax(Math.multiplyExact(quantidadeDoses, itemEstoque.getQuantidadeMax()));
					itemEstoque.setQuantPerda(Math.multiplyExact(quantidadeDoses, itemEstoque.getQuantPerda()));
				} else if (!produto.getDose().booleanValue() && mudancaDose) {
					itemEstoque.setQuantidadeAtual((int) Math.ceil(itemEstoque.getQuantidadeAtual() / quantidadeDoses));
					itemEstoque.setQuantidadeMax((int) Math.ceil(itemEstoque.getQuantidadeMax() / quantidadeDoses));
					itemEstoque.setQuantPerda((int) Math.ceil(itemEstoque.getQuantPerda() / quantidadeDoses));
				}
			}
		}

		this.validarProduto(produtoTO.getMarca(), produtoTO.getCodFesta(), produtoTO.getCodProduto());
		produto.setMarca(produtoTO.getMarca());
		produtoRepository.save(produto);
		return produto;
	}

	public ItemEstoque editarProdutoEstoque(ItemEstoqueTO itemEstoqueTO, Integer idUsuarioPermissao) {
		festaService.validarFestaFinalizada(itemEstoqueTO.getCodFesta());
		festaService.validarProdEstoqueIniciada(itemEstoqueTO, itemEstoqueTO.getCodFesta());
		this.validarUsuarioPorEstoque(idUsuarioPermissao, itemEstoqueTO.getCodEstoque(),
				TipoPermissao.EDIMESTO.getCodigo());
		int codProduto = itemEstoqueTO.getCodProduto();
		int codEstoque = itemEstoqueTO.getCodEstoque();
		ItemEstoque retorno = this.validarProdutoEstoque(codEstoque, codProduto);

		retorno.setQuantidadeMax(itemEstoqueTO.getQuantidadeMax());
		retorno.setPorcentagemMin(itemEstoqueTO.getPorcentagemMin());
		retorno.setQuantidadeAtual(itemEstoqueTO.getQuantidadeAtual());

		this.validaQuantAndPorcent(retorno.getQuantidadeMax(), retorno.getQuantidadeAtual(),
				retorno.getPorcentagemMin());

		itemEstoqueRepository.save(retorno);

		return retorno;
	}

	// baixa/recarga_____________________________________________________________________________________________________

	public ItemEstoque baixaProduto(int codProduto, int codEstoque, int quantidade, int idUsuarioPermissao,
			boolean quebra) {

		Estoque estoque = this.validarEstoque(codEstoque);

		Festa festa = estoque.getFesta();

		festaService.validarFestaInicializadaPrep(festa.getCodFesta());

		festaService.validarFestaInicializadaFinal(festa.getCodFesta());

		this.validarUsuarioPorEstoque(idUsuarioPermissao, codEstoque, TipoPermissao.EDIMESTO.getCodigo());

		this.validarQuantInformada(quantidade);

		ItemEstoque itemEstoque = this.validarProdutoEstoque(codEstoque, codProduto);

		int quantidadeAtual = itemEstoque.getQuantidadeAtual() - quantidade;

		if (quebra) {
			itemEstoque.setQuantPerda(quantidadeAtual);
		}

		itemEstoque.setQuantidadeAtual(quantidadeAtual);

		this.validaQuantAndPorcent(itemEstoque.getQuantidadeMax(), quantidadeAtual, itemEstoque.getPorcentagemMin());

		itemEstoqueRepository.save(itemEstoque);

		this.inserirItemEstoqueFluxo(itemEstoque);

		this.disparaNotificacaoCasoEstoqueEscasso(itemEstoque);

		return itemEstoque;
	}

	public ItemEstoque recargaProduto(int codProduto, int codEstoque, int quantidade, int idUsuarioPermissao) {

		Estoque estoque = this.validarEstoque(codEstoque);

		Festa festa = estoque.getFesta();

		festaService.validarFestaInicializadaPrep(festa.getCodFesta());

		festaService.validarFestaInicializadaFinal(festa.getCodFesta());

		this.validarQuantInformada(quantidade);
		this.validarUsuarioPorEstoque(idUsuarioPermissao, codEstoque, TipoPermissao.EDIMESTO.getCodigo());

		ItemEstoque itemEstoque = this.validarProdutoEstoque(codEstoque, codProduto);
		int quantidadeAtual = itemEstoque.getQuantidadeAtual() + quantidade;
		if (quantidadeAtual > itemEstoque.getQuantidadeMax())
			throw new ValidacaoException("QATMMAXI"); // quantidade total fará que o estoque fique com uma quantidade
		// maior que a máxima

		itemEstoque.setQuantidadeAtual(quantidadeAtual);

		itemEstoqueRepository.save(itemEstoque);

		if (!itemEstoque.quantidadeAtualAbaixoMin()) {
			List<Grupo> grupos = grupoRepository.findGruposPermissaoEstoque(itemEstoque.getCodFesta());
			this.deleteNotificacoesItemEstoque(grupos, itemEstoque);
		}

		this.inserirItemEstoqueFluxo(itemEstoque);

		return itemEstoque;
	}

	private void inserirItemEstoqueFluxo(ItemEstoque itemEstoque) {
		ItemEstoqueFluxo itemEstoqueFluxo = new ItemEstoqueFluxo(itemEstoque, notificacaoService.getDataAtual(),
				itemEstoqueFluxoRepository.getNextValMySequence(), itemEstoque.getProduto().getDose());
		itemEstoqueFluxoRepository.save(itemEstoqueFluxo);
	}

	// gets_____________________________________________________________________________________________________

	public List<Produto> listaProduto(Integer codFesta, Integer codUsuario) {
		this.validarUsuarioPorFesta(codFesta, codUsuario, TipoPermissao.VISUESTO.getCodigo());
		return produtoRepository.findProdutoByCodFesta(codFesta);
	}

	private void deleteNotificacoesItemEstoque(List<Grupo> grupos, ItemEstoque itemEstoque) {
		for (Grupo grupo : grupos) {
			String mensagem = notificacaoService.criarMensagemEstoqueBaixo(itemEstoque.getCodFesta(),
					itemEstoque.getEstoque().getCodEstoque(), itemEstoque.getProduto().getCodProduto());
			if (!notificacaoService.verificarNotificacaoGrupo(grupo.getCodGrupo(), mensagem)) {
				notificacaoService.deletarNotificacaoGrupo(grupo.getCodGrupo(), mensagem);
				List<Usuario> usuarios = usuarioRepository.findUsuariosPorGrupo(grupo.getCodGrupo());
				for (Usuario usuario : usuarios) {
					notificacaoService.deleteNotificacao(usuario.getCodUsuario(), mensagem);
				}
			}
		}
	}

	public Produto getProduto(Integer codFesta, Integer codUsuario, Integer codProduto) {
		this.validarUsuarioPorFesta(codFesta, codUsuario, TipoPermissao.VISUESTO.getCodigo());
		return this.validarProduto(codProduto);
	}

	public NotificacaoEstoqueTO getInfoEstoqueProduto(int codFesta, int codEstoque, int codProduto) {
		Festa festa = validarFesta(codFesta);
		ItemEstoque itemEstoque = this.validarProdutoEstoque(codEstoque, codProduto);
		return notificacaoEstoqueTOFactory.getNotificacaoEstoqueTO(itemEstoque, festa);
	}

	// validadores________________________________________________________________________________________________
	private Festa validarFesta(int codFesta) {
		Festa festa = festaRepository.findById(codFesta);
		if (festa == null)
			throw new ValidacaoException("FSTANFOU");
		return festa;
	}

	private void validarQuantidadeDoseProduto(ProdutoTO produtoTO) {
		if (produtoTO.isDose() && produtoTO.getQuantDoses() <= 0) {
			throw new ValidacaoException("PRODDMEZ"); // Quantidade de doses informada precisa ser maior que zero
		}
	}

	private Produto validarProduto(int codProduto) {
		Produto produto = produtoRepository.findById(codProduto);
		if (produto == null)
			throw new ValidacaoException("PRODNFOU");
		return produto;
	}

	private Estoque validarEstoque(int codEstoque) {
		Estoque estoque = estoqueRepository.findByEstoqueCodEstoque(codEstoque);
		if (estoque == null) {
			throw new ValidacaoException("ESTONFOU");// estoque não encontrado
		}
		return estoque;
	}

	public ItemEstoque validarProdutoEstoque(int codEstoque, int codProduto) {
		this.validarEstoque(codEstoque);
		this.validarProduto(codProduto);
		ItemEstoque itemEstoque = itemEstoqueRepository.findItemEstoque(codEstoque, codProduto);
		if (itemEstoque == null) {
			throw new ValidacaoException("ESTNPROD");// estoque não possui o produto
		}
		return itemEstoque;
	}

	private void verificarProdutoEstoque(int codEstoque, int codProduto) {
		if (itemEstoqueRepository.findItemEstoque(codEstoque, codProduto) != null) {
			throw new ValidacaoException("PRODCADA");// Produto já cadastrado
		}

	}

	private void validarUsuarioPorFesta(int codFesta, int codUsuario, int tipoPermissao) { // TipoPermissao.CADAESTO.getCodigo()
		List<Grupo> grupos = grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, tipoPermissao);
		if (grupos.isEmpty()) {
			throw new ValidacaoException("USESPERM");// usuário sem permissão
		}
	}

	private Estoque validarUsuarioPorEstoque(int codUsuario, int codEstoque, int tipoPermissao) {
		Estoque estoque = this.validarEstoque(codEstoque);
		this.validarUsuarioPorFesta(estoque.getFesta().getCodFesta(), codUsuario, tipoPermissao);
		return estoque;
	}

	private ItemEstoque validarEstoqueProduto(int codEstoque, int codProduto) {
		Estoque estoque = this.validarEstoque(codEstoque);
		Produto produto = this.validarProduto(codProduto);

		if (estoque.getFesta().getCodFesta() != produto.getCodFesta())
			throw new ValidacaoException("PRODNEST");// Produto não pertence a mesma festa do estoque

		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setEstoque(estoque);
		itemEstoque.setProduto(produto);
		return itemEstoque;
	}

	private void validarQuantInformada(int quantidade) {
		if (quantidade <= 0) {
			throw new ValidacaoException("OPERAINV"); // quantidade inválida
		}
	}

	private void validaQuantAndPorcent(int quantidadeMax, int quantidadeAtual, int porcentagemMin) {

		if (quantidadeMax <= 0 || quantidadeMax < quantidadeAtual)
			throw new ValidacaoException("QMAXINV"); // quantidade máxima inválida

		if (porcentagemMin < 0)
			throw new ValidacaoException("PCMININV"); // porcentagemMin inválida

		if (quantidadeAtual < 0)
			throw new ValidacaoException("QATUAINV"); // quantidadeAtual inválida
	}

	private void validarProduto(String marca, int codFesta, int codProduto) {
		Produto produtoMarcaIgual = produtoRepository.findByMarca(marca, codFesta);
		if (produtoMarcaIgual != null && produtoMarcaIgual.getCodProduto() != codProduto) {
			throw new ValidacaoException("PROMIGUA");// produto com a mesma marca cadastrado na festa
		}
	}
	



	private void disparaNotificacaoCasoEstoqueEscasso(ItemEstoque itemEstoque) {
		if (itemEstoque.quantidadeAtualAbaixoMin()) {
			int codFesta = itemEstoque.getEstoque().getFesta().getCodFesta(); // pega o código da festa
			List<Grupo> grupos = grupoRepository.findGruposPermissaoEstoque(codFesta);

			for (Grupo grupo : grupos) {
				String mensagem = notificacaoService.criarMensagemEstoqueBaixo(itemEstoque.getCodFesta(),
						itemEstoque.getEstoque().getCodEstoque(), itemEstoque.getProduto().getCodProduto());
				if (notificacaoService.verificarNotificacaoGrupo(grupo.getCodGrupo(), mensagem)) {
					notificacaoService.inserirNotificacaoGrupo(grupo.getCodGrupo(),
							TipoNotificacao.ESTBAIXO.getCodigo(), mensagem);
					List<Usuario> usuarios = usuarioRepository.findUsuariosPorGrupo(grupo.getCodGrupo());
					for (Usuario usuario : usuarios) {
						notificacaoService.inserirNotificacaoUsuario(usuario.getCodUsuario(),
								TipoNotificacao.ESTBAIXO.getCodigo(), mensagem);
					}
				}
			}
		}
	}

	

}
