package com.gestion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.dao.GrupoRepository;
import com.gestion.entity.Grupo;

@Service
public class GrupoService {
	
	@Autowired
	private GrupoRepository repo;
	
	public List<Grupo> listarTodos() {
		return repo.listarTodosLosGrupos();
	}

}
