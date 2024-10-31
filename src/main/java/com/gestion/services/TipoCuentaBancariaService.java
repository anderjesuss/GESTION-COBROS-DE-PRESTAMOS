package com.gestion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.dao.TipoCuentaBancariaRepository;
import com.gestion.entity.TipoCuentaBancaria;

@Service
public class TipoCuentaBancariaService {
	
	@Autowired
	private TipoCuentaBancariaRepository repo;
	
	public List<TipoCuentaBancaria> listarTodos() {
		return repo.findAll();
	}
}
