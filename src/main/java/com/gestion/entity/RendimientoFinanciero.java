package com.gestion.entity;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RendimientoFinanciero {
	
	@Id
	private int cod_prestamista;
    private String nombre_prestamista;
    private BigDecimal monto_total_prestamos;
    private BigDecimal interes_total;
    private BigDecimal monto_pagado;
    private BigDecimal monto_pendiente;
    private BigDecimal mora_total;
    private BigDecimal rentabilidad_porcentaje;
    
	public int getCod_prestamista() {
		return cod_prestamista;
	}
	public void setCod_prestamista(int cod_prestamista) {
		this.cod_prestamista = cod_prestamista;
	}
	public String getNombre_prestamista() {
		return nombre_prestamista;
	}
	public void setNombre_prestamista(String nombre_prestamista) {
		this.nombre_prestamista = nombre_prestamista;
	}
	public BigDecimal getMonto_total_prestamos() {
		return monto_total_prestamos;
	}
	public void setMonto_total_prestamos(BigDecimal monto_total_prestamos) {
		this.monto_total_prestamos = monto_total_prestamos;
	}
	public BigDecimal getInteres_total() {
		return interes_total;
	}
	public void setInteres_total(BigDecimal interes_total) {
		this.interes_total = interes_total;
	}
	public BigDecimal getMonto_pagado() {
		return monto_pagado;
	}
	public void setMonto_pagado(BigDecimal monto_pagado) {
		this.monto_pagado = monto_pagado;
	}
	public BigDecimal getMonto_pendiente() {
		return monto_pendiente;
	}
	public void setMonto_pendiente(BigDecimal monto_pendiente) {
		this.monto_pendiente = monto_pendiente;
	}
	public BigDecimal getMora_total() {
		return mora_total;
	}
	public void setMora_total(BigDecimal mora_total) {
		this.mora_total = mora_total;
	}
	public BigDecimal getRentabilidad_porcentaje() {
		return rentabilidad_porcentaje;
	}
	public void setRentabilidad_porcentaje(BigDecimal rentabilidad_porcentaje) {
		this.rentabilidad_porcentaje = rentabilidad_porcentaje;
	}
    
    

}
