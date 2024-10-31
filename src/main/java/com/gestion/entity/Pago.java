package com.gestion.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_pago")
public class Pago {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_pago")
	private Integer codigo;
	@Column(name = "monto_pago")
	private BigDecimal montoPago;
	@Column(name = "mora_pago")
	private BigDecimal moraPago;
	@Column(name = "mon_pag_total")
	private BigDecimal montoPagoTotal;
	
	@ManyToOne
	@JoinColumn(name = "cod_cuota")
	private Cuota cuota;
	
	@ManyToOne
	@JoinColumn(name = "cod_prestamista")
	private Usuario usuario;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getMontoPago() {
		return montoPago;
	}

	public void setMontoPago(BigDecimal montoPago) {
		this.montoPago = montoPago;
	}

	public BigDecimal getMoraPago() {
		return moraPago;
	}

	public void setMoraPago(BigDecimal moraPago) {
		this.moraPago = moraPago;
	}

	public Cuota getCuota() {
		return cuota;
	}

	public void setCuota(Cuota cuota) {
		this.cuota = cuota;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getMontoPagoTotal() {
		return montoPagoTotal;
	}

	public void setMontoPagoTotal(BigDecimal montoPagoTotal) {
		this.montoPagoTotal = montoPagoTotal;
	}
	
}
