package com.gestion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.dao.EstadoPrestamoRepository;
import com.gestion.entity.EstadoPrestamoDTO;

@Service
public class EstadoPrestamoService {
	
	@Autowired
	private EstadoPrestamoRepository repo;
	
	public List<EstadoPrestamoDTO> obtenerInformacionPrestamosPorPrestamista(Integer codPrestamista) {
		return repo.obtenerInformacionPrestamos(codPrestamista);
	}

}

