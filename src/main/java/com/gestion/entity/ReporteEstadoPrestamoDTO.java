package com.gestion.entity;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ReporteEstadoPrestamoDTO {
	@Id
	private int codigoPrestamo;
    private int numeroCuota;
    private BigDecimal montoCuota;
    private Date fechaPago;
    private int estadoCuota;
    private BigDecimal deuda;
    
	public int getCodigoPrestamo() {
		return codigoPrestamo;
	}
	public void setCodigoPrestamo(int codigoPrestamo) {
		this.codigoPrestamo = codigoPrestamo;
	}
	public int getNumeroCuota() {
		return numeroCuota;
	}
	public void setNumeroCuota(int numeroCuota) {
		this.numeroCuota = numeroCuota;
	}
	public BigDecimal getMontoCuota() {
		return montoCuota;
	}
	public void setMontoCuota(BigDecimal montoCuota) {
		this.montoCuota = montoCuota;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	public int getEstadoCuota() {
		return estadoCuota;
	}
	public void setEstadoCuota(int estadoCuota) {
		this.estadoCuota = estadoCuota;
	}
	public BigDecimal getDeuda() {
		return deuda;
	}
	public void setDeuda(BigDecimal deuda) {
		this.deuda = deuda;
	}
	
	
    
    
}
