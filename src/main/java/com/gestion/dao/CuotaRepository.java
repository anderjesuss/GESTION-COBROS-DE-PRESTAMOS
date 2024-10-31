package com.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gestion.entity.Cuota;
import com.gestion.entity.Grupo;
import com.gestion.entity.Prestamo;

public interface CuotaRepository extends JpaRepository<Cuota, Integer> {
	
	@Query(value = "SELECT c.cod_cuota, p.cod_pres, u.nom_usu, c.num_cuota, c.monto_cuota, c.fecha_pago, c.esta_cuota, c.fec_reg, c.monto_cuota_fija\r\n"
			+ "FROM tb_cuota c\r\n"
			+ "INNER JOIN tb_prestamo p ON c.cod_pres = p.cod_pres\r\n"
			+ "INNER JOIN tb_usuario u ON p.cod_prestatario = u.cod_usu\r\n"
			+ "WHERE p.cod_prestatario = ?1", nativeQuery = true)
	public List<Cuota> listaCuotasPorPrestatario(Integer codPres);
	
	@Query("select c from Cuota c where c.prestamo.prestatario.codigo = ?1")
	public List<Cuota> ListarEstadoCuotasPorPrestatario(Integer codPrestatario);
	
	@Query(value = "SELECT c.*\r\n"
			+ "FROM tb_cuota AS c\r\n"
			+ "INNER JOIN tb_prestamo AS p ON c.cod_pres = p.cod_pres\r\n"
			+ "INNER JOIN tb_usuario AS u ON p.cod_prestatario = u.cod_usu\r\n"
			+ "WHERE c.fecha_pago BETWEEN ?1 AND ?2\r\n"
			+ "AND u.cod_usu = ?3", nativeQuery = true)	
	public List<Cuota> ListarEstadoCuotasPorRangoFechas(String fechaIni, String FechaFin, Integer codPrestatario);
	
	@Query("select count(c) from Cuota c where c.prestamo.prestatario.grupo.codigo = ?1")
	int cantidadCuotasPorGrupo(int codGrupo);
	
	@Query("select count(c) from Cuota c where c.prestamo.prestatario.codigo = ?1")
	int cantidadCuotasPorPrestatario(int codPrestatario);
	
}
