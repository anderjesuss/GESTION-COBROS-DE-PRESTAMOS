package com.gestion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.dao.CuentaBancariaRepository;
import com.gestion.entity.CuentaBancaria;

@Service
public class CuentaBancariaService {
	
	@Autowired
	private CuentaBancariaRepository repo;
	
	public List<CuentaBancaria> listarCuentasBancariasPorUsuario(int codUsu) {
		return repo.listarCuentasBancariasPorUsuario(codUsu);
	}
	
	public CuentaBancaria buscarPorID(Integer cod) {
		return repo.findById(cod).orElse(null);
	}
	
	public boolean existeNumeroDeCuenta(String numeroCuenta)  {
		return repo.existsByNumeroCuenta(numeroCuenta);
	}
	
	public void registrar(CuentaBancaria cuenta) {
		repo.save(cuenta);
	}
	
	public int cuentasPorUsuario(Integer codUsu)  {
		return repo.countCuentaBancariaPorUsuario(codUsu);
	}
	
}
