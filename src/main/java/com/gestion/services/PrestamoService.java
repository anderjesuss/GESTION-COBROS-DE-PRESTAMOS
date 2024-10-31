package com.gestion.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.dao.PrestamoRepository;
import com.gestion.entity.Prestamo;
import com.gestion.entity.Usuario;

@Service
public class PrestamoService {
	
	@Autowired
	private PrestamoRepository repo;
	
	public List<Prestamo> listaPrestamoPorPrestatario(int codPres) {
		return repo.listarPrestamosPorPrestatario(codPres);
	}
	
	public List<Prestamo> listarPrestamosPorGrupo(int codGru) {
		return repo.buscarPrestamosPorGrupo(codGru);
	}
	
	public Prestamo buscarPorId(Integer cod) {
		return repo.findById(cod).orElse(null);
	}
	
	public void registrar(Prestamo p) {
		repo.save(p);
	}
	
	public void actualizar(Prestamo p) {
		repo.save(p);
	}

	public long contarSolicitudesPorUsuarioYFecha(Integer codPres, LocalDate fecha) {
		
	    Usuario usuario = new Usuario();
	    usuario.setCodigo(codPres);
	    LocalDate fechaActual = LocalDate.now();
	        
	    // Obtener todos los préstamos del usuario
	    List<Prestamo> prestamosDelUsuario = repo.listarPrestamosPorPrestatario(codPres);
	        
	    // Contar las solicitudes del usuario en la fecha actual
	    long solicitudesDelDia = prestamosDelUsuario.stream()
	    		
	            .filter(p -> p.getFechaPrestamo().toLocalDate().isEqual(fechaActual))
	            .count();
	    
	      
	    return solicitudesDelDia;
	}
	
	public List<Prestamo> buscarPorFechasYGrupo(String fechaIni, String fechaFin, Integer codGru) {
		return repo.buscarPrestamoPorFechasYGrupo(fechaIni, fechaFin, codGru);
	}
	
	public int contarPrestamosGrupo(int codGrupo) {
		return repo.contarPrestamosPorGrupo(codGrupo);
	}
	
	public int prestamosPorUsuario(Integer codPres)  {
		return repo.countPrestamoPorUsuario(codPres);
	}
	public int obtenerCantidadPrestamos() {
		return repo.cantidadPrestamos();
	}
}
