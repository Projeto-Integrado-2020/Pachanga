package com.eventmanager.pachanga.controllers;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.CategoriaTO;
import com.eventmanager.pachanga.dtos.IngressoTO;
import com.eventmanager.pachanga.dtos.InsercaoIngresso;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.FestaFactory;
import com.eventmanager.pachanga.factory.IngressoFactory;
import com.eventmanager.pachanga.services.IngressoService;
import com.eventmanager.pachanga.tipo.TipoCategoria;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.utils.EnvioEmMassaDeConvite;
import com.eventmanager.pachanga.utils.PdfConviteManager;

@Controller
@RequestMapping("/ingresso")
@CrossOrigin
public class IngressoController {

	@Autowired
	private IngressoService ingressoService;

	@Autowired
	private IngressoFactory ingressoFactory;

	@Autowired
	private FestaController festaController;

	@Autowired
	private FestaFactory festaFactory;

	@ResponseBody
	@GetMapping(path = "/listaUser")
	public ResponseEntity<Object> getIngressosUser(@RequestParam(required = true) int idUser) {
		try {
			List<IngressoTO> ingressosTO = new ArrayList<>();
			for (Ingresso ingresso : ingressoService.getIngressosUser(idUser)) {
				IngressoTO ingressoTO = ingressoFactory.getIngressoTO(ingresso, null);

				CategoriaTO categoriaPrimaria = festaController.categoriaFesta(ingresso.getFesta().getCodFesta(),
						TipoCategoria.PRIMARIO.getDescricao());
				CategoriaTO categoriaSecundario = festaController.categoriaFesta(ingresso.getFesta().getCodFesta(),
						TipoCategoria.SECUNDARIO.getDescricao());

				ingressoTO.setFesta(festaFactory.getFestaTO(ingresso.getFesta(), null, false, categoriaPrimaria,
						categoriaSecundario, null));

				ingressosTO.add(ingressoTO);
			}
			return ResponseEntity.ok(ingressosTO);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@GetMapping(path = "/listaFesta")
	public ResponseEntity<Object> getIngressosFesta(@RequestParam(required = true) int idFesta) {
		try {
			List<Ingresso> ingressos = ingressoService.getIngressosFesta(idFesta);
			List<IngressoTO> ingressosTO = new ArrayList<>();
			for (Ingresso ingresso : ingressos) {
				IngressoTO ingressoTO = ingressoFactory.getIngressoTO(ingresso, null);
				ingressosTO.add(ingressoTO);
			}
			return ResponseEntity.ok(ingressosTO);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping(path = "/addIngresso")
	public ResponseEntity<Object> addIngresso(@RequestBody IngressoTO ingressoTO) {
		try {
			return ResponseEntity.ok(ingressoService.addIngresso(ingressoTO, null));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping(path = "/addIngressoLista")
	public ResponseEntity<Object> addIngressoLista(@RequestBody InsercaoIngresso insercaoIngresso) {
		try {
			return ResponseEntity.ok(ingressoService.addListaIngresso(insercaoIngresso));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PutMapping(path = "/updateCheckin")
	public ResponseEntity<Object> updateChekin(@RequestParam(required = true) String codIngresso,
			@RequestParam(required = true) int codFesta, @RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity
					.ok(ingressoFactory.getIngressoTO(ingressoService.updateCheckin(codIngresso, codUsuario, codFesta), null));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping(path = "/updateStatusCompra")
	public ResponseEntity<Object> updateStatusCompra(@RequestParam(required = true) String codBoleto,
			@RequestParam(required = true) String notificationType,
			@RequestParam(required = true) String notificationCode) {
		try {
			ingressoService.updateStatusCompra(codBoleto, notificationCode);
			return ResponseEntity.ok().build();
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	/* dps vou deletar
	@ResponseBody
	@GetMapping(path = "/test")
	public ResponseEntity<Object> test() throws Exception{
			//PdfConviteManager.GerarPDF(listarIng());
			EnvioEmMassaDeConvite envioEmMassaDeConvite = new EnvioEmMassaDeConvite();
			envioEmMassaDeConvite.upsertAll(listarIng());
			envioEmMassaDeConvite.enviarTudo();
			return ResponseEntity.ok().build();
		
	}
	
	public List<Ingresso> listarIng() throws Exception{
		Ingresso ingresso = new Ingresso();
		ingresso.setUsuario(usuarioTest());
		ingresso.setEmailTitular("teste@email.com");
		ingresso.setNomeTitular("titularz");
		ingresso.setFesta(festaTest());
		ingresso.setLote(loteTest());
		ingresso.setDataCompra(LocalDateTime.of(2015, Month.JUNE, 22, 19, 10));
		ingresso.setCodIngresso("1234");
		ingresso.setPreco((float) 52.99);
		List<Ingresso> retorno = new ArrayList<>(); 
		retorno.add(ingresso);
		retorno.add(ingresso);
		
		
		ingresso = new Ingresso();
		ingresso.setUsuario(usuarioTest());
		ingresso.setEmailTitular("testex@email.com");
		ingresso.setNomeTitular("titularx");
		ingresso.setFesta(festaTestt());
		ingresso.setLote(loteTest());
		ingresso.setDataCompra(LocalDateTime.of(2015, Month.JUNE, 22, 19, 10));
		ingresso.setCodIngresso("123456");
		ingresso.setPreco((float) 52.99);
		
		retorno.add(ingresso);
		retorno.add(ingresso);
		
		return retorno;
	}
	
	@SuppressWarnings("deprecation")
	public static Usuario usuarioTest() throws Exception{
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("opedrofreitas@gmail.com");
		usuarioTest.setSenha("fc68b677646b5f018d1762e9a19bf65180d9aab2794794340ade50e0d78a239affd43a613e7136a61b5d63b09f072c0c039dea4281873abe826d6e6285d9cefef0a0d868d3b0b0d4582ec787b473b4e0");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}
	
	
	
	private Festa festaTest() throws Exception{
		Festa festaTest = new Festa();
		festaTest.setCodFesta(2);
		festaTest.setCodEnderecoFesta("https//:minhacasa.org");
		festaTest.setDescOrganizador("sou demente");
		festaTest.setHorarioInicioFesta(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		festaTest.setHorarioFimFesta(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		festaTest.setNomeFesta("festao");
		festaTest.setOrganizador("Joao Neves");
		festaTest.setStatusFesta(TipoStatusFesta.PREPARACAO.getValor());
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));

		return festaTest;
	}
	
	private Festa festaTestt() throws Exception{
		Festa festaTest = new Festa();
		festaTest.setCodFesta(6);
		festaTest.setCodEnderecoFesta("https//:minhacasa.org");
		festaTest.setDescOrganizador("sou dementee");
		festaTest.setHorarioInicioFesta(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		festaTest.setHorarioFimFesta(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		festaTest.setNomeFesta("festao");
		festaTest.setOrganizador("Joao Neves");
		festaTest.setStatusFesta(TipoStatusFesta.PREPARACAO.getValor());
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));

		return festaTest;
	}
	
	private Lote loteTest() throws Exception {
		Lote lote = new Lote();
		lote.setFesta(festaTest());
		lote.setCodLote(1);
		lote.setDescLote("lote vip krl");
		lote.setNomeLote("Teste");
		lote.setHorarioInicio(LocalDateTime.of(2020, Month.SEPTEMBER, 23, 19, 10));
		lote.setHorarioFim(LocalDateTime.of(2020, Month.SEPTEMBER, 24, 19, 10));
		lote.setNumeroLote(1);
		lote.setPreco(17.2f);
		lote.setQuantidade(100);
		return lote;
	}
	*/
}
