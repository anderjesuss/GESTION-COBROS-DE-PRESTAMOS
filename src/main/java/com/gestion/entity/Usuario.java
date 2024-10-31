package com.gestion.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name="tb_usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_usu")
	private Integer codigo;
	@Column(name = "correo_usu")
	private String correo;
	@Column(name = "pass_usu")
	private String clave;
	@Column(name = "nom_usu")
	private String nombre;
	@Column(name = "ape_usu")
	private String apellido;
	@Column(name = "dni_usu")
	private String dni;
	@Column(name = "tel_usu")
	private String telefono;
	@Column(name = "fec_nac_usu")
	private LocalDate fechaNacimiento;
	@Column(name = "fec_reg_usu", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
	@Column(name = "esta_usu")
	private Boolean estado;
	
	// Para el Rol
	@ManyToOne
	@JoinColumn(name = "idrol")
	private Rol rol;
	
	// Para grupo
	@ManyToOne
	@JoinColumn(name = "cod_gru")
	private Grupo grupo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "usuario")
	private List<CuentaBancaria> listaCuentaBancaria;
	
	@JsonIgnore
	@OneToMany(mappedBy = "prestatario")
	private List<Prestamo> listaPrestamos;
	
	@JsonIgnore
	@OneToMany(mappedBy = "prestamista")
	private List<Prestamo> listaPrestamoss;
	
	@JsonIgnore
	@OneToMany(mappedBy = "usuario")
	private List<Pago> listaPagos;
	
	public List<Prestamo> getListaPrestamoss() {
		return listaPrestamoss;
	}

	public void setListaPrestamoss(List<Prestamo> listaPrestamoss) {
		this.listaPrestamoss = listaPrestamoss;
	}

	public Usuario() {
		this.fechaRegistro = new Date();
	}

	// GET AND SET
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public List<CuentaBancaria> getListaCuentaBancaria() {
		return listaCuentaBancaria;
	}

	public void setListaCuentaBancaria(List<CuentaBancaria> listaCuentaBancaria) {
		this.listaCuentaBancaria = listaCuentaBancaria;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public List<Prestamo> getListaPrestamos() {
		return listaPrestamos;
	}

	public void setListaPrestamos(List<Prestamo> listaPrestamos) {
		this.listaPrestamos = listaPrestamos;
	}

	public List<Pago> getListaPagos() {
		return listaPagos;
	}

	public void setListaPagos(List<Pago> listaPagos) {
		this.listaPagos = listaPagos;
	}
	
	
	
	
	
	//public List<Boleta> getListaBoleta() {
		//return listaBoleta;
	//}
	//public void setListaBoleta(List<Boleta> listaBoleta) {
		//this.listaBoleta = listaBoleta;
	//}
}
