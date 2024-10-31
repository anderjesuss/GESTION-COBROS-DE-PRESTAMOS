package com.gestion.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_tipo_banco")
public class TipoCuentaBancaria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_tipo_banco")
	private Integer codigo;
	@Column(name = "nombre_tipo_banco")
	private String nombre;
	
	@JsonIgnore
	@OneToMany(mappedBy = "banco")
	private List<CuentaBancaria> listaCuentaBancaria;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<CuentaBancaria> getListaCuentaBancaria() {
		return listaCuentaBancaria;
	}

	public void setListaCuentaBancaria(List<CuentaBancaria> listaCuentaBancaria) {
		this.listaCuentaBancaria = listaCuentaBancaria;
	}
	
}
