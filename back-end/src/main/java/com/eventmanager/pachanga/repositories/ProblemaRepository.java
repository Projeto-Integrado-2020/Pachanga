package com.eventmanager.pachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventmanager.pachanga.domains.Problema;

public interface ProblemaRepository extends JpaRepository<Problema, Integer> {

}
