package com.gestion.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="tb_enlace")

public class Enlace {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idenlace")
	private int codigo;
	private String descripcion;
	private String ruta;
	private String icon;

	
	@OneToMany(mappedBy = "enlace")
	private List<RolEnlace> listaRolEnlace;


	public int getCodigo() {
		return codigo;
	}


	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getRuta() {
		return ruta;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public void setRuta(String ruta) {
		this.ruta = ruta;
	}


	public List<RolEnlace> getListaRolEnlace() {
		return listaRolEnlace;
	}


	public void setListaRolEnlace(List<RolEnlace> listaRolEnlace) {
		this.listaRolEnlace = listaRolEnlace;
	}


	
}
