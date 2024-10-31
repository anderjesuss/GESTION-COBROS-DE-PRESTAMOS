package com.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gestion.entity.RendimientoFinanciero;

public interface RendimientoFinancieroRepository extends JpaRepository<RendimientoFinanciero, Integer> {
	
	@Query(value = "SELECT \r\n"
			+ "    u.cod_usu AS cod_prestamista,\r\n"
			+ "    CONCAT(u.nom_usu, ' ', u.ape_usu) AS nombre_prestamista,\r\n"
			+ "    COALESCE(monto_total_prestamos, 0) AS monto_total_prestamos,\r\n"
			+ "    COALESCE(interes_total, 0) AS interes_total,\r\n"
			+ "    COALESCE(monto_pagado, 0) AS monto_pagado,\r\n"
			+ "    COALESCE(monto_pendiente, 0) AS monto_pendiente,\r\n"
			+ "    COALESCE(mora_total, 0) AS mora_total,\r\n"
			+ "    COALESCE(FORMAT((interes_total + mora_total) / NULLIF(monto_total_prestamos, 0) * 100, 1), '0') AS rentabilidad_porcentaje\r\n"
			+ "FROM tb_usuario u\r\n"
			+ "LEFT JOIN (\r\n"
			+ "    SELECT \r\n"
			+ "        p.cod_prestamista,\r\n"
			+ "        SUM(p.monto_pres) AS monto_total_prestamos\r\n"
			+ "    FROM tb_prestamo p\r\n"
			+ "    WHERE p.estado_pres = 1\r\n"
			+ "    GROUP BY p.cod_prestamista\r\n"
			+ ") prestamos ON u.cod_usu = prestamos.cod_prestamista\r\n"
			+ "\r\n"
			+ "LEFT JOIN (\r\n"
			+ "    SELECT \r\n"
			+ "        p.cod_prestamista,\r\n"
			+ "        SUM(p.inte_pres) AS interes_total\r\n"
			+ "    FROM tb_prestamo p\r\n"
			+ "    WHERE p.estado_pres = 1\r\n"
			+ "    GROUP BY p.cod_prestamista\r\n"
			+ ") intereses ON u.cod_usu = intereses.cod_prestamista\r\n"
			+ "\r\n"
			+ "LEFT JOIN (\r\n"
			+ "    SELECT \r\n"
			+ "        u.cod_usu AS cod_prestamista,\r\n"
			+ "        SUM(pago.mon_pag_total) AS monto_pagado,\r\n"
			+ "        SUM(CASE WHEN cu.esta_cuota = 0 THEN cu.monto_cuota ELSE 0 END) AS monto_pendiente,\r\n"
			+ "        SUM(pago.mora_pago) AS mora_total\r\n"
			+ "    FROM tb_usuario u\r\n"
			+ "    JOIN tb_prestamo p ON u.cod_usu = p.cod_prestamista\r\n"
			+ "    LEFT JOIN tb_cuota cu ON p.cod_pres = cu.cod_pres\r\n"
			+ "    LEFT JOIN tb_pago pago ON cu.cod_cuota = pago.cod_cuota\r\n"
			+ "    WHERE u.cod_gru = ?1 -- Reemplaza 8 con el código de grupo deseado\r\n"
			+ "    AND u.idrol = 3\r\n"
			+ "    GROUP BY u.cod_usu\r\n"
			+ ") mora ON u.cod_usu = mora.cod_prestamista\r\n"
			+ "\r\n"
			+ "WHERE u.cod_gru = ?1 -- Reemplaza 8 con el código de grupo deseado\r\n"
			+ "    AND u.idrol = 3", nativeQuery = true)
	public List<RendimientoFinanciero> filtrarRendimientoFinanciero(int codGrupo);

}
