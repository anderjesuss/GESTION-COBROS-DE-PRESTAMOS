package com.gestion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.dao.RendimientoFinancieroRepository;
import com.gestion.entity.RendimientoFinanciero;

@Service
public class RendimientoFinancieroService {
	
	@Autowired
	private RendimientoFinancieroRepository repo;
	
	public List<RendimientoFinanciero> filtrarRendimientoFinanciero(int codGrupo) {
		return repo.filtrarRendimientoFinanciero(codGrupo);
	}
}
