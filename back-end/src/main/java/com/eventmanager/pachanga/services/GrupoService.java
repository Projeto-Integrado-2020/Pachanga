package com.eventmanager.pachanga.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoGrupo;
import com.eventmanager.pachanga.utils.EmailMensagem;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private FestaRepository festaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EmailMensagem emailMensagem;


	public StringBuilder addUsuariosFesta(List<String> emails, int codFesta) {
		StringBuilder mensagemRetorno = new StringBuilder();
		Grupo grupo = grupoRepository.findGrupoConvidadoFesta(codFesta);
		if(grupo == null) {
			grupo = new Grupo(grupoRepository.getNextValMySequence(), festaRepository.findByCodFesta(codFesta), TipoGrupo.CONVIDADO.getValor(), 100);
			grupoRepository.save(grupo);
		}
		for(String email : emails) {
			Usuario usuario = usuarioRepository.findByEmail(email);
			if(usuario != null) {
				grupoRepository.saveUsuarioGrupo(usuario.getCodUsuario(), grupo.getCodGrupo());
			}else {
				emailMensagem.enviarEmail(email);				
				mensagemRetorno.append(email);
				mensagemRetorno.append(" ");
			}
		}
		return mensagemRetorno;

	}

}
