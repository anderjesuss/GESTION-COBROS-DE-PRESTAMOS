package com.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.entity.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
	
	Pago findByCuotaCodigo(Integer codigoCuota);

}
