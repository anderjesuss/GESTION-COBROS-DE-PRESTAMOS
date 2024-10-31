package com.gestion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.dao.PagoRepository;
import com.gestion.entity.Pago;

@Service
public class PagoService {
	
	@Autowired
	private PagoRepository repo;
	
	public void registrar(Pago p) {
		repo.save(p);
	}
	
	public void actualizar(Pago p) {
		repo.save(p);
	}
	
	public Pago obtenerPagoPorCuota(Integer codigoCuota) {
        return repo.findByCuotaCodigo(codigoCuota);
    }
}
