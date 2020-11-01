package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.QuestionarioForms;

public interface QuestionarioFormsRepository extends JpaRepository<QuestionarioForms, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_quest_forms');", nativeQuery = true)
	public int getNextValMySequence();

	@Query(value = "SELECT qf FROM QuestionarioForms qf JOIN qf.festa f WHERE f.codFesta = :codFesta")
	List<QuestionarioForms> listaQuestionarioFormsFesta(int codFesta);

	@Query(value = "SELECT qf FROM QuestionarioForms qf JOIN qf.festa f WHERE f.codFesta = :codFesta AND qf.nomeQuestionario = :nomeQuestionario")
	public QuestionarioForms findByNome(String nomeQuestionario, int codFesta);
	
	@Query(value = "SELECT qf FROM QuestionarioForms qf JOIN qf.festa f WHERE f.codFesta = :codFesta AND qf.urlQuestionario = :urlQuestionario")
	public QuestionarioForms findByUrl(String urlQuestionario, int codFesta);

	@Query(value = "SELECT qf FROM QuestionarioForms qf JOIN qf.festa f WHERE qf.codQuestionario = :codQuestionario")
	public QuestionarioForms findByCodQuestionario(int codQuestionario);

}
