package com.eventmanager.pachanga.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.ProblemaBuilder;
import com.eventmanager.pachanga.builder.ProblemaTOBuilder;
import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.dtos.ProblemaTO;

@Component(value = "problemaFactory")
public class ProblemaFactory {
	 ProblemaFactory() {
	 }
	 
	public ProblemaTO getProblemaTO(Problema problema) {		
		return ProblemaTOBuilder.getInstance()
							  .codProblema(problema.getCodProblema())
							  .descProblema(problema.getDescProblema())
							  .build();
	}	 
	 
	public Problema getProblema(ProblemaTO problema) {	
		return ProblemaBuilder.getInstance()
							  .codProblema(problema.getCodProblema())
							  .descProblema(problema.getDescProblema())
							  .build();
	}	
	
	public List<ProblemaTO> getProblemasTO(List<Problema> problemas) {		
		List<ProblemaTO> problemasTO = new ArrayList<>();
		for(Problema problema : problemas) {
			problemasTO.add(getProblemaTO(problema));
		}
		return problemasTO;
	}
	

}
