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
import jakarta.persistence.Transient;

@Entity
@Table(name = "tb_cuota")
public class Cuota {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_cuota")
	private Integer codigo;
	@Column(name = "num_cuota")
	private int numero;
	@Column(name = "monto_cuota")
	private BigDecimal monto;
	@Column(name = "fecha_pago")
	private Date fechaPago;
	@Column(name = "fec_reg")
	private Date fechaRegPago;
	@Column(name = "esta_cuota")
	private int estado;
	@Column(name = "monto_cuota_fija")
	private BigDecimal montoFijo;
	@Transient
    private BigDecimal mora;
	@Transient
    private BigDecimal deuda;
	
	@ManyToOne
	@JoinColumn(name = "cod_pres")
	private Prestamo prestamo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "cuota")
	private List<Pago> listaPagos;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public BigDecimal getMora() {
		return mora;
	}

	public void setMora(BigDecimal mora) {
		this.mora = mora;
	}

	public Date getFechaRegPago() {
		return fechaRegPago;
	}

	public void setFechaRegPago(Date fechaRegPago) {
		this.fechaRegPago = fechaRegPago;
	}

	public List<Pago> getListaPagos() {
		return listaPagos;
	}

	public void setListaPagos(List<Pago> listaPagos) {
		this.listaPagos = listaPagos;
	}

	public BigDecimal getMontoFijo() {
		return montoFijo;
	}

	public void setMontoFijo(BigDecimal montoFijo) {
		this.montoFijo = montoFijo;
	}

	public BigDecimal getDeuda() {
		return deuda;
	}

	public void setDeuda(BigDecimal deuda) {
		this.deuda = deuda;
	}
	
	

}
