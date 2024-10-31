package com.gestion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.dao.RolRepository;
import com.gestion.entity.Rol;

@Service
public class RolService {
	
	@Autowired
	private RolRepository repo;
	
	public List<Rol> listarTodosLosRoles() {
		return repo.findAll();
	}
}
