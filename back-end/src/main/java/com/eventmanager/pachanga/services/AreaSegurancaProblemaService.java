package com.eventmanager.pachanga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.AreaSegurancaProblemaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.AreaSegurancaProblemaFactory;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.ProblemaRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@Service
@Transactional
public class AreaSegurancaProblemaService {
	
	@Autowired
	private UsuarioRepository userRepository;
	
	@Autowired
	private ProblemaRepository problemaRepository;
	
	@Autowired
	private AreaSegurancaRepository areaSegurancaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private FestaRepository festaRepository;
	
	@Autowired
	NotificacaoService notificacaoService;
	
	@Autowired
	GrupoService grupoService;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private AreaSegurancaProblemaRepository areaSegurancaProblemaRepository;
	
	public AreaSegurancaProblema addProblemaSeguranca(AreaSegurancaProblemaTO problemaSegurancaTO, int idUsuario) {
		this.validarUsuarioFesta(idUsuario, problemaSegurancaTO.getCodFesta());
		//this.validarPermissaoUsuario(problemaSegurancaTO.getCodFesta(), idUsuario, TipoPermissao.ADDPSEGU.getCodigo());
		if(this.getAreaSegurancaProblema(problemaSegurancaTO.getCodAreaSeguranca(), problemaSegurancaTO.getCodProblema()) != null) {
			throw new ValidacaoException("PRSEEXST"); //ProblemaSeguranca já existe
		}
		
		Usuario usuarioEmissor = this.validarUsuarioFesta(problemaSegurancaTO.getCodUsuarioEmissor(), problemaSegurancaTO.getCodFesta());
		Usuario usuarioResolv = this.validarUsuarioFesta(problemaSegurancaTO.getCodUsuarioResolv(), problemaSegurancaTO.getCodFesta());
		Problema problema = this.validarProblema(problemaSegurancaTO.getCodProblema());
		AreaSeguranca areaSeguranca = areaSegurancaRepository.findAreaByCodFestaAndCodArea(problemaSegurancaTO.getCodFesta(), problemaSegurancaTO.getCodAreaSeguranca());								 
		Festa festa = this.validarFesta(problemaSegurancaTO.getCodFesta());
		
		AreaSegurancaProblema problemaSeguranca = AreaSegurancaProblemaFactory.getProblemaSeguranca(problemaSegurancaTO, festa, areaSeguranca, problema, usuarioEmissor, usuarioResolv);
		this.validarAreaSegurancaProblema(problemaSeguranca);
		areaSegurancaProblemaRepository.save(problemaSeguranca);
		
		String mensagem = "Foi reportado um problema de " + problema.getDescProblema() + " na área " 
		                  + areaSeguranca.getNomeArea() + ": " + problemaSeguranca.getDescProblema();
		this.notificarGrupos(festa.getCodFesta(), mensagem);
		
		return problemaSeguranca;
	}
	
	public AreaSegurancaProblema updateProblemaSeguranca(AreaSegurancaProblemaTO problemaSegurancaTO, int idUsuario) {
		this.validarUsuarioFesta(idUsuario, problemaSegurancaTO.getCodFesta());
		//this.validarPermissaoUsuario(problemaSegurancaTO.getCodFesta(), idUsuario, TipoPermissao.EDITPSEG.getCodigo());
		AreaSegurancaProblema problemaSeguranca = getAreaSegurancaProblema(problemaSegurancaTO.getCodAreaSeguranca(), problemaSegurancaTO.getCodProblema());
		if(problemaSeguranca == null) {
			throw new ValidacaoException("PRSENFOU"); //ProblemaSeguranca nao existe
		}
		
		Usuario usuarioEmissor = userRepository.findById(problemaSegurancaTO.getCodUsuarioEmissor());
		Usuario usuarioResolv = userRepository.findById(problemaSegurancaTO.getCodUsuarioResolv());								 
		Festa festa = this.validarFesta(problemaSegurancaTO.getCodFesta());
		
		problemaSeguranca.setDescProblema(problemaSegurancaTO.getDescProblema());
		problemaSeguranca.setHorarioFim(problemaSegurancaTO.getHorarioFim());
		problemaSeguranca.setHorarioInicio(problemaSegurancaTO.getHorarioInicio());
		problemaSeguranca.setStatusProblema(problemaSegurancaTO.getStatusProblema());
		problemaSeguranca.setCodUsuarioEmissor(usuarioEmissor);
		problemaSeguranca.setCodUsuarioResolv(usuarioResolv);
		problemaSeguranca.setFesta(festa);
		
		this.validarAreaSegurancaProblema(problemaSeguranca);
		areaSegurancaProblemaRepository.save(problemaSeguranca);
		
		String mensagem = "O problema de " + problemaSeguranca.getProblema().getDescProblema() + " na área " 
                		  + problemaSeguranca.getArea().getNomeArea() + " foi atualizado: " + problemaSeguranca.getDescProblema();
		this.notificarGrupos(festa.getCodFesta(), mensagem);
		
		return problemaSeguranca;
	}
	
	public AreaSegurancaProblema deleteProblemaSeguranca(int codAreaSeguranca, int codProblema, int codFesta, int idUsuario) {
		this.validarUsuarioFesta(idUsuario, codFesta);
		//this.validarPermissaoUsuario(codFesta, idUsuario, TipoPermissao.DELEPSEG.getCodigo());
		AreaSegurancaProblema problemaSeguranca = getAreaSegurancaProblema(codAreaSeguranca, codProblema);
		if(problemaSeguranca == null) {
			throw new ValidacaoException("PRSENFOU"); //ProblemaSeguranca nao existe
		}							 
	
		areaSegurancaProblemaRepository.delete(problemaSeguranca);
		
		String mensagem = "O problema de " + problemaSeguranca.getProblema().getDescProblema() + " na área " 
      		  + problemaSeguranca.getArea().getNomeArea() + " foi encerrado: " + problemaSeguranca.getDescProblema();
		this.notificarGrupos(problemaSeguranca.getFesta().getCodFesta(), mensagem);
		
		return problemaSeguranca;
	}
	
	public AreaSegurancaProblema findProblemaSeguranca(int codAreaSeguranca, int codProblema, int codFesta, int idUsuario) {
		this.validarUsuarioFesta(idUsuario, codFesta);
		//this.validarPermissaoUsuario(codFesta, idUsuario, TipoPermissao.VISUPSEG.getCodigo());
		AreaSegurancaProblema problemaSeguranca = areaSegurancaProblemaRepository.findAreaSegurancaProblema(codAreaSeguranca, codProblema);;
		return problemaSeguranca;
	}
	
	public List<AreaSegurancaProblema> findAllProblemasSegurancaArea(int codAreaProblema, int codFesta, int idUsuario) {
		this.validarUsuarioFesta(idUsuario, codFesta);
		//this.validarPermissaoUsuario(codFesta, idUsuario, TipoPermissao.VISUPSEG.getCodigo());
		List<AreaSegurancaProblema> problemasSeguranca = areaSegurancaProblemaRepository.findAllAreaSegurancaProblemaArea(codAreaProblema, codFesta);
		return problemasSeguranca;
	}
	
	public List<AreaSegurancaProblema> findAllProblemasSegurancaFesta(int codFesta, int idUsuario) {
		this.validarUsuarioFesta(idUsuario, codFesta);
		//this.validarPermissaoUsuario(codFesta, idUsuario, TipoPermissao.VISUPSEG.getCodigo());
		List<AreaSegurancaProblema> problemasSeguranca = areaSegurancaProblemaRepository.findAllAreaSegurancaProblemaFesta(codFesta);
		return problemasSeguranca;
	}
	
//validadores______________________________________________________________________________________________________________

	private Usuario validarUsuarioFesta(int idUsuario, int idFesta) {
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
	
	private AreaSegurancaProblema getAreaSegurancaProblema(int codAreaSeguranca, int codProblema) {
		AreaSegurancaProblema problemaSeguranca = areaSegurancaProblemaRepository.findAreaSegurancaProblema(codAreaSeguranca, codProblema);
		return problemaSeguranca;
	}
	
	private void validarAreaSegurancaProblema(AreaSegurancaProblema problemaSeguranca) {
		if(problemaSeguranca.getHorarioFim().isBefore(problemaSeguranca.getHorarioInicio())) {
			throw new ValidacaoException("DATEINFE");
		}
	}
	
	private Problema validarProblema(int codProblema) {
		Problema problema = problemaRepository.findProblemaByCodProblema(codProblema);
		if(problema == null) {
			throw new ValidacaoException("PROBNFOU");
		}
		return problema;
	}
	
	public Festa validarFesta(int codFesta) {
		Festa festa = festaRepository.findByCodFesta(codFesta);
		if (festa == null) {
			throw new ValidacaoException("FESTNFOU");
		}
		return festa;
	}
	
	private void notificarGrupos(int codFesta, String mensagem) {
		List<Grupo> grupos =grupoRepository.findGruposFesta(codFesta);
		for(Grupo grupo : grupos) {
			notificacaoService.inserirNotificacaoGrupo(grupo.getCodGrupo(), 5, mensagem);	
		}
	}
	
	private void validarPermissaoUsuario(int codFesta, int idUsuario, int codPermissao) {
		List<Grupo> grupos = grupoRepository.findGrupoPermissaoUsuario(codFesta, idUsuario, codPermissao);
		if (grupos.isEmpty()) {
			throw new ValidacaoException("USESPERM");// usuário sem permissão
		} 
	}

}
