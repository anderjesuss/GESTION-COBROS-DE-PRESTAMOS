package com.gestion.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gestion.entity.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
	
	@Query("select p from Prestamo p where p.prestatario.codigo = ?1")
	public List<Prestamo> listarPrestamosPorPrestatario(int codPres);
	
	@Query("SELECT p FROM Prestamo p INNER JOIN p.prestatario u WHERE u.grupo.codigo = ?1")
    public List<Prestamo> buscarPrestamosPorGrupo(int codigoGrupo);
	
	@Query(value = "SELECT p.* FROM tb_prestamo p\r\n"
			+ "INNER JOIN tb_usuario u ON p.cod_prestatario = u.cod_usu\r\n"
			+ "WHERE p.fecha_pres BETWEEN ?1 AND ?2\r\n"
			+ "AND u.cod_gru = ?3", nativeQuery = true)
	public List<Prestamo> buscarPrestamoPorFechasYGrupo(String fechaInicio, String fechaFin, Integer codGrupo);

	@Query("SELECT COUNT(p) FROM Prestamo p " +
		       "INNER JOIN Usuario u ON p.prestatario.codigo = u.codigo " +
		       "WHERE u.grupo.codigo = ?1")
		int contarPrestamosPorGrupo(int codGrupo);

	@Query("select count(u) from Prestamo u where u.prestatario.codigo = ?1")	
	int countPrestamoPorUsuario(Integer codPres);
	
	@Query("select count(p) from Prestamo p")
	int cantidadPrestamos();
	

}
