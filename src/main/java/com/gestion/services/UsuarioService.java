package com.gestion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.dao.UsuarioRepository;
import com.gestion.entity.Enlace;
import com.gestion.entity.Usuario;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repo;
	
	public Usuario loginUsuario(String vLogin) {
		return repo.iniciarSesion(vLogin);
	}
	
	public List<Enlace> enlacesDelUsuario(int rol) {
		return repo.traerEnlacesDElUsuario(rol);
	}
	
	public List<Usuario> listarTodosLosUsuarios() {
		return repo.listarTodosLosUsuarios();
	}
	
	public List<Usuario> obtenerPrestamistasPorRolYGrupo(int codigoRol, int codigoGrupo) {
	    return repo.findPrestamistasByRolAndGrupo(codigoRol, codigoGrupo);
	}

	public List<Usuario> listarPrestamistasPorGrupo(Integer codGru) {
		return repo.listarPrestamistasPorGrupo(codGru);
	}
	
	//Por el momento
	public List<Usuario> listarJefePrestamistas() {
		return repo.listarJefePrestamistas();
	}
	/*
	public List<Usuario> listarJefePrestamistasPorGrupo(Integer codGru) {
		return repo.listarJefePrestamistasPorGrupo(codGru);
	}
	*/
	public List<Usuario> obtenerJefePrestamistasPorRolYGrupo(int codigoRol, int codigoGrupo) {
	    return repo.findJefePrestamistasByRolAndGrupo(codigoRol, codigoGrupo);
	}

	
	public int obtenerCantidadUsuariosPorRolYGrupo(int rol, int grupo) {
        return repo.countUsuariosByRolAndGrupo(rol, grupo);
    }
	
	public int obtenerCantidadUsuariosPorDeRolPrestamistas() {
		return repo.countUsuariosByRol();
	}
  
	public int obtenerCantidadUsuariosPorDeRolPrestatarios() {
		return repo.countUsuariosByRolPrestatarios();
	}
  
  	public int obtenerCantidadUsuariosPorDeRolJefePrestamistas() {
		return repo.countUsuariosByRolJefePrestamista();
	}
	
	public int obtenerCantidadUsuariosEnSistema() {
		return repo.cantidadUsuarioSistema();
	}
	
	public boolean existeDni(String dni) {
		return repo.existsByDni(dni);
	}
	
	public boolean existeCorreo(String correo) {
		return repo.existsByCorreo(correo);
	}
	
	public boolean existeTelefono(String telefono) {
		return repo.existsByTelefono(telefono);
	}
	
	public void registrar(Usuario u) {
		repo.save(u);
	}
	
	public void actualizar(Usuario u) {
		repo.save(u);
	}
	
	public List<Usuario> listarTodos() {
		return repo.findAll();
	}
	
	public void eliminarPorID(Integer cod) {
		repo.deleteById(cod);
	}
	
	public Usuario buscarPorId(Integer cod) {
		return repo.findById(cod).orElse(null);
	}
	
	public List<Usuario> listarPrestatariosPorGrupo(Integer codGru) {
		return repo.listarPrestatariosPorGrupo(codGru);
	}
	
	//PC1-SANCHEZANAYAINICIO
	public boolean existeJefePrestamistaEnGrupo(int codigoRol, int codigoGrupo) {
        List<Usuario> prestamistas = repo.findPrestamistasByRolAndGrupo(codigoRol, codigoGrupo);
        return !prestamistas.isEmpty();
    }
	//PC1-SANCHEZANAYAFIN

	
	
}