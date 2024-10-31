package com.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gestion.entity.CuentaBancaria;

public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, Integer> {
	
	@Query("select cb from CuentaBancaria cb where cb.usuario.codigo = ?1")
	public List<CuentaBancaria> listarCuentasBancariasPorUsuario(int codUsu);
	
	boolean existsByNumeroCuenta(String numeroCuenta);
	
	@Query("select count(u) from CuentaBancaria u where u.usuario.codigo = ?1")	
	int countCuentaBancariaPorUsuario(Integer codUsu);

}
