package com.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gestion.entity.EstadoPrestamoDTO;

public interface EstadoPrestamoRepository extends JpaRepository<EstadoPrestamoDTO, Integer> {
	
	@Query(
	        value = "SELECT\r\n"
	        		+ "    p.cod_pres AS codigo_prestamo,\r\n"
	        		+ "    CONCAT(u.nom_usu, ' ', u.ape_usu) AS nombre_prestatario,\r\n"
	        		+ "    p.monto_pres AS monto_prestamo,\r\n"
	        		+ "    p.dias_pres AS numero_cuotas, \r\n"
	        		+ "    SUM(cu.esta_cuota = 1) AS cuotas_pagadas,\r\n"
	        		+ "    SUM(cu.esta_cuota = 0) AS cuotas_pendientes,\r\n"
	        		+ "    COALESCE(SUM(CASE WHEN cu.esta_cuota = 1 THEN pg.mon_pag_total ELSE 0 END), 0) AS monto_pagado,\r\n"
	        		+ "    SUM(CASE WHEN cu.esta_cuota = 0 THEN cu.monto_cuota ELSE 0 END) AS monto_pendiente\r\n"
	        		+ "FROM\r\n"
	        		+ "    tb_prestamo p\r\n"
	        		+ "INNER JOIN tb_usuario u ON p.cod_prestatario = u.cod_usu\r\n"
	        		+ "LEFT JOIN tb_cuota cu ON p.cod_pres = cu.cod_pres\r\n"
	        		+ "LEFT JOIN tb_pago pg ON cu.cod_cuota = pg.cod_cuota\r\n"
	        		+ "WHERE\r\n"
	        		+ "    p.cod_prestamista = ?1\r\n"
	        		+ "GROUP BY\r\n"
	        		+ "    p.cod_pres, u.nom_usu, p.monto_pres, p.dias_pres;",
	        nativeQuery = true
	    )
	    public List<EstadoPrestamoDTO> obtenerInformacionPrestamos(Integer codPrestamista);

}
