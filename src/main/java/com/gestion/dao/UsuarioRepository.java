package com.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gestion.entity.Enlace;
import com.gestion.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	//select *from tb_usuario where login='anita' and password='123'
	@Query("select u from Usuario u where u.correo=?1")
	public Usuario iniciarSesion(String vLogin);
	
	//select e.idenlace,e.descripcion,e.ruta from tb_rol_enlace re join tb_enlace e
	@Query("select e from RolEnlace re join re.enlace e where re.rol.codigo=?1")
	public List<Enlace> traerEnlacesDElUsuario(int codigoRol);
	
	@Query("select u from Usuario u where u.rol.codigo <> 1 order by u.codigo asc")
	public List<Usuario> listarTodosLosUsuarios();
	
	@Query("SELECT u FROM Usuario u WHERE u.rol.codigo = ?1 AND u.grupo.codigo = ?2")
	List<Usuario> findPrestamistasByRolAndGrupo(int codigoRol, int codigoGrupo);
	
	@Query("SELECT u FROM Usuario u WHERE u.rol.codigo = ?1 AND u.grupo.codigo = ?2")
	List<Usuario> findJefePrestamistasByRolAndGrupo(int codigoRol, int codigoGrupo);
	
	
	@Query("SELECT u FROM Usuario u WHERE u.rol.codigo = 4 AND u.grupo.codigo = ?1")
	public List<Usuario> listarPrestatariosPorGrupo(Integer codGru);
	
	
	@Query("SELECT u FROM Usuario u WHERE u.rol.codigo = 3 AND u.grupo.codigo = ?1")
	public List<Usuario> listarPrestamistasPorGrupo(Integer codGru);
	
	//por el momento
	@Query("SELECT u FROM Usuario u WHERE u.rol.codigo = 2 ")
	public List<Usuario> listarJefePrestamistas();
	
/*
	@Query("SELECT u FROM Usuario u WHERE u.rol.codigo = 2 AND u.grupo.codigo = ?1")
	public List<Usuario> listarJefePrestamistasPorGrupo(Integer codGru);
*/	
	
	@Query("select count(u) from Usuario u where u.rol.codigo = ?1 and u.grupo.codigo = ?2")
	int countUsuariosByRolAndGrupo(int rol, int grupo);
	
	@Query("select count(u) from Usuario u where u.rol.codigo = 3")
	int countUsuariosByRol();

	@Query("select count(u) from Usuario u where u.rol.codigo = 4")
	int countUsuariosByRolPrestatarios();
  
	@Query("select count(u) from Usuario u where u.rol.codigo = 2")
	int countUsuariosByRolJefePrestamista();
  
	@Query("select count(u) from Usuario u where u.codigo != 1")
	int cantidadUsuarioSistema();
	
	boolean existsByDni(String dni);
	
	boolean existsByCorreo(String correo);
	
	boolean existsByTelefono(String telefono);
	
	
}
