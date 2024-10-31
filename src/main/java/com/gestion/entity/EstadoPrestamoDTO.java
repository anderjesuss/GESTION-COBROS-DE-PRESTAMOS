package com.gestion.entity;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EstadoPrestamoDTO {
	
	@Id
	private int codigoPrestamo;
    private String nombrePrestatario;
    private BigDecimal montoPrestamo;
    private int numeroCuotas;
    private int cuotasPagadas;
    private int cuotasPendientes;
    private BigDecimal montoPagado;
    private BigDecimal montoPendiente;
	public int getCodigoPrestamo() {
		return codigoPrestamo;
	}
	public void setCodigoPrestamo(int codigoPrestamo) {
		this.codigoPrestamo = codigoPrestamo;
	}
	public String getNombrePrestatario() {
		return nombrePrestatario;
	}
	public void setNombrePrestatario(String nombrePrestatario) {
		this.nombrePrestatario = nombrePrestatario;
	}
	public BigDecimal getMontoPrestamo() {
		return montoPrestamo;
	}
	public void setMontoPrestamo(BigDecimal montoPrestamo) {
		this.montoPrestamo = montoPrestamo;
	}
	public Integer getNumeroCuotas() {
		return numeroCuotas;
	}
	public void setNumeroCuotas(Integer numeroCuotas) {
		this.numeroCuotas = numeroCuotas;
	}
	public int getCuotasPagadas() {
		return cuotasPagadas;
	}
	public void setCuotasPagadas(int cuotasPagadas) {
		this.cuotasPagadas = cuotasPagadas;
	}
	public int getCuotasPendientes() {
		return cuotasPendientes;
	}
	public void setCuotasPendientes(int cuotasPendientes) {
		this.cuotasPendientes = cuotasPendientes;
	}
	public BigDecimal getMontoPagado() {
		return montoPagado;
	}
	public void setMontoPagado(BigDecimal montoPagado) {
		this.montoPagado = montoPagado;
	}
	public BigDecimal getMontoPendiente() {
		return montoPendiente;
	}
	public void setMontoPendiente(BigDecimal montoPendiente) {
		this.montoPendiente = montoPendiente;
	}
    
    

      
}
