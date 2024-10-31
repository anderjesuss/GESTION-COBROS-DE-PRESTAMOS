package com.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gestion.entity.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
	
	@Query("select g from Grupo g where codigo <> -1")
	public List<Grupo> listarTodosLosGrupos();

}
