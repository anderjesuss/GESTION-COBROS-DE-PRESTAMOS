package com.gestion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.dao.TipoPrestamoRepository;
import com.gestion.entity.TipoPrestamo;

@Service
public class TipoPrestamoService {
	
	@Autowired
	private TipoPrestamoRepository repo;
	
	public List<TipoPrestamo> listarTodos() {
		return repo.findAll();
	}
}
