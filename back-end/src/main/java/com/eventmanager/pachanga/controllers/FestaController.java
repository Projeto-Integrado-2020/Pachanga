package com.eventmanager.pachanga.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.CategoriaTO;
import com.eventmanager.pachanga.dtos.ConvidadoTO;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.CategoriaFactory;
import com.eventmanager.pachanga.factory.ConvidadoFactory;
import com.eventmanager.pachanga.factory.FestaFactory;
import com.eventmanager.pachanga.factory.UsuarioFactory;
import com.eventmanager.pachanga.services.CategoriaService;
import com.eventmanager.pachanga.services.ConvidadoService;
import com.eventmanager.pachanga.services.FestaService;
import com.eventmanager.pachanga.services.UsuarioService;
import com.eventmanager.pachanga.tipo.TipoCategoria;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

@Controller
@RequestMapping("/festa")
@CrossOrigin
public class FestaController {

	@Autowired
	private FestaService festaService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ConvidadoService convidadoService;

	@Autowired
	private ConvidadoFactory convidadoFactory;

	@Autowired
	private FestaFactory festaFactory;


	@ResponseBody
	@GetMapping(path = "/lista")
	public ResponseEntity<Object> listaFesta(@RequestParam(required = true) int idUser){
		List<Festa> festas = new ArrayList<>();
		try {
			if(idUser == 0 ) {
				festas =  festaService.procurarFestas();
			}else {
				festas =  festaService.procurarFestasPorUsuario(idUser);
			}
			List<FestaTO> festasTo = festas.stream().map(f -> {
				List<UsuarioTO> usuarios = listUsuarioTO(f);
				List<ConvidadoTO> convidados = convidadoFactory.getConvidadosTO(convidadoService.pegarConvidadosFesta(f.getCodFesta()));
				CategoriaTO categoriaPrimaria = categoriaFesta(f.getCodFesta(), TipoCategoria.PRIMARIO.getDescricao());
				CategoriaTO categoriaSecundario = categoriaFesta(f.getCodFesta(), TipoCategoria.SECUNDARIO.getDescricao());
				FestaTO festaTo = festaFactory.getFestaTO(f, usuarios, true, categoriaPrimaria, categoriaSecundario, convidados, idUser);
				if(idUser != 0) {
					festaTo.setFuncionalidade(festaService.funcionalidadeFesta(festaTo.getCodFesta(), idUser));
				}
				return festaTo;
			}).collect(Collectors.toList());
			return ResponseEntity.ok(festasTo);
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping(path = "/listaFestasDisponiveis")
	public ResponseEntity<Object> listarFestaDisponiveis(){
		try {
			List<Festa> festas =  festaService.procurarFestasComLotesCompraveis();
			List<FestaTO> festasTO = new ArrayList<>();
			for(Festa festa : festas) {
				List<UsuarioTO> usuarios = listUsuarioTO(festa);
				CategoriaTO categoriaPrimaria = categoriaFesta(festa.getCodFesta(), TipoCategoria.PRIMARIO.getDescricao());
				CategoriaTO categoriaSecundaria = categoriaFesta(festa.getCodFesta(), TipoCategoria.SECUNDARIO.getDescricao());
				List<ConvidadoTO> convidados = convidadoFactory.getConvidadosTO(convidadoService.pegarConvidadosFesta(festa.getCodFesta()));
				festasTO.add(festaFactory.getFestaTO(festa, usuarios, true, categoriaPrimaria, categoriaSecundaria, convidados));		
			}
			return ResponseEntity.ok(festasTO);
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping(path = "/adicionar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	public ResponseEntity<Object> addFesta(@RequestParam String json, @RequestParam(required = false) MultipartFile imagem, @RequestParam(required = true) int idUser) throws IOException{
		try {
			FestaTO festaTo = criarFestaTOByString(json);
			Festa festa = festaService.addFesta(festaTo, idUser, imagem);
			CategoriaTO categoriaPrimaria = categoriaFesta(festa.getCodFesta(), TipoCategoria.PRIMARIO.getDescricao());
			CategoriaTO categoriaSecundario = categoriaFesta(festa.getCodFesta(), TipoCategoria.SECUNDARIO.getDescricao());
			return ResponseEntity.ok(festaFactory.getFestaTO(festa, null, false, categoriaPrimaria, categoriaSecundario, null));
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@DeleteMapping(path = "/delete")
	public ResponseEntity<Object> deleteFesta(@RequestParam(required = true) int idFesta, @RequestParam(required = true) int idUser){
		try {
			festaService.deleteFesta(idFesta, idUser);
			return ResponseEntity.ok("FESTDELE");//ver se precisa colocar alguma coisa aqui ou pode mandar somente um ok
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PutMapping(path = "/atualizar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	public ResponseEntity<Object> atualizaFesta(@RequestParam String json, @RequestParam(required = false) MultipartFile imagem, @RequestParam(required = true) int idUser)throws IOException{
		try {
			FestaTO festaTo = criarFestaTOByString(json);
			Festa festa = festaService.updateFesta(festaTo, idUser, imagem);
			CategoriaTO categoriaPrimaria = categoriaFesta(festa.getCodFesta(), TipoCategoria.PRIMARIO.getDescricao());
			CategoriaTO categoriaSecundario = categoriaFesta(festa.getCodFesta(), TipoCategoria.SECUNDARIO.getDescricao());
			return ResponseEntity.ok(festaFactory.getFestaTO(festa, null, false, categoriaPrimaria, categoriaSecundario, null));
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@GetMapping(path = "/festaUnica")
	public ResponseEntity<Object> getFesta(@RequestParam(required = true)int idFesta, @RequestParam(required = true)int idUsuario){
		try {			
			FestaTO festaTo = null;
			Festa festa = festaService.procurarFesta(idFesta, idUsuario);
			if(festa != null) {
				List<UsuarioTO> usuarios = listUsuarioTO(festa);
				CategoriaTO categoriaPrimaria = categoriaFesta(festa.getCodFesta(), TipoCategoria.PRIMARIO.getDescricao());
				CategoriaTO categoriaSecundario = categoriaFesta(festa.getCodFesta(), TipoCategoria.SECUNDARIO.getDescricao());
				List<ConvidadoTO> convidados = convidadoFactory.getConvidadosTO(convidadoService.pegarConvidadosFesta(festa.getCodFesta()));
				return ResponseEntity.ok(festaFactory.getFestaTO(festa, usuarios, false, categoriaPrimaria, categoriaSecundario, convidados, idUsuario));
			}
			return ResponseEntity.status(200).body(festaTo);
		}catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping(path = "/festaUnicaDadosPublicos")
	public ResponseEntity<Object> getFestaSemUserId(@RequestParam(required = true)int idFesta){
		try {			
			FestaTO festaTo = null;
			Festa festa = festaService.procurarDadosPublicosFesta(idFesta);
			if(festa != null) {
				CategoriaTO categoriaPrimaria = categoriaFesta(festa.getCodFesta(), TipoCategoria.PRIMARIO.getDescricao());
				CategoriaTO categoriaSecundaria = categoriaFesta(festa.getCodFesta(), TipoCategoria.SECUNDARIO.getDescricao());
				List<ConvidadoTO> convidados = convidadoFactory.getConvidadosTO(convidadoService.pegarConvidadosFesta(festa.getCodFesta()));
				return ResponseEntity.ok(festaFactory.getFestaTO(festa, null, false, categoriaPrimaria, categoriaSecundaria, convidados));
			}
			return ResponseEntity.status(200).body(festaTo);
		}catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PutMapping(path = "/festaMudancaStatus")
	public ResponseEntity<Object> alterarStatusFesta(@RequestParam(required = true)int idFesta, @RequestParam(required = true)String statusFesta, @RequestParam(required = true)int idUsuario) {
		try {
			Festa festa = festaService.mudarStatusFesta(idFesta, statusFesta, idUsuario);
			CategoriaTO categoriaPrimaria = categoriaFesta(festa.getCodFesta(), TipoCategoria.PRIMARIO.getDescricao());
			CategoriaTO categoriaSecundario = categoriaFesta(festa.getCodFesta(), TipoCategoria.SECUNDARIO.getDescricao());
			return ResponseEntity.ok(festaFactory.getFestaTO(festa, null, false, categoriaPrimaria, categoriaSecundario, null));//festa mudada com sucesso
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	private List<UsuarioTO> listUsuarioTO(Festa festa){
		return usuarioService.getUsuariosFesta(festa.getCodFesta()).stream().map(u -> {
			String funcionalidade = usuarioService.funcionalidadeUsuarioFesta(festa.getCodFesta(), u.getCodUsuario());
			return UsuarioFactory.getUsuarioTO(u, funcionalidade);
		}).collect(Collectors.toList());
	}

	public CategoriaTO categoriaFesta(int codFesta, String tipoCategoria) {
		Categoria categoria = categoriaService.procurarCategoriaFesta(codFesta, tipoCategoria);
		if(categoria != null) {
			return CategoriaFactory.getCategoriaTO(categoria);
		}else {
			return null;
		}
	}
	
	private FestaTO criarFestaTOByString(String json) throws IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objectMapper.readValue(json, FestaTO.class);
	}
}
