package com.gestion.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "tb_prestamo")
public class Prestamo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_pres")
	private Integer codigo;
	@Column(name = "dias_pres")
	private int dias;
	@Column(name = "tasa_pres")
	private BigDecimal tasa;
	@Temporal(TemporalType.DATE)
    @Column(name = "fecha_pres")
    private Date fechaPrestamo;
	@Column(name = "monto_pres")
	private BigDecimal monto;
	@Column(name = "estado_pres")
	private int estado;
	@Column(name = "inte_pres")
	private BigDecimal interes;
	
	@ManyToOne
	@JoinColumn(name = "cod_cuenta")
	private CuentaBancaria cuenta;
	
	@ManyToOne
	@JoinColumn(name = "cod_prestatario")
	private Usuario prestatario;
	
	@ManyToOne
	@JoinColumn(name = "tipo_prestamos")
	private TipoPrestamo tipoPrestamo;
	
	@ManyToOne
	@JoinColumn(name = "cod_prestamista")
	private Usuario prestamista;
	
	@JsonIgnore
	@OneToMany(mappedBy = "prestamo")
	private List<Cuota> listaCuotas;

	public Integer getCodigo() {
		return codigo;
	}

	public Usuario getPrestamista() {
		return prestamista;
	}

	public void setPrestamista(Usuario prestamista) {
		this.prestamista = prestamista;
	}

	public List<Cuota> getListaCuotas() {
		return listaCuotas;
	}

	public void setListaCuotas(List<Cuota> listaCuotas) {
		this.listaCuotas = listaCuotas;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public BigDecimal getTasa() {
		return tasa;
	}

	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}

	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public CuentaBancaria getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaBancaria cuenta) {
		this.cuenta = cuenta;
	}

	public Usuario getPrestatario() {
		return prestatario;
	}

	public void setPrestatario(Usuario prestatario) {
		this.prestatario = prestatario;
	}

	public TipoPrestamo getTipoPrestamo() {
		return tipoPrestamo;
	}

	public void setTipoPrestamo(TipoPrestamo tipoPrestamo) {
		this.tipoPrestamo = tipoPrestamo;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}
		
	

}
