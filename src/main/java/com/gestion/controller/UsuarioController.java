package com.gestion.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.entity.Grupo;
import com.gestion.entity.Rol;
import com.gestion.entity.Usuario;
import com.gestion.services.GrupoService;
import com.gestion.services.RolService;
import com.gestion.services.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
    private UsuarioService servicioUsuario;
	@Autowired
	private RolService servicioRol;
	@Autowired
	private GrupoService servicioGrupo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@RequestMapping("/lista")
	public String lista(Model model) {
		model.addAttribute("roles", servicioRol.listarTodosLosRoles());
		model.addAttribute("grupos", servicioGrupo.listarTodos());
		model.addAttribute("usuarios", servicioUsuario.listarTodosLosUsuarios());
		model.addAttribute("cantidad", servicioUsuario.obtenerCantidadUsuariosEnSistema());
		return "usuario";
	}
	
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("codigo") Integer cod,
	                     @RequestParam("nombre") String nom,
	                     @RequestParam("apellido") String ape,
	                     @RequestParam("dni") String dni,
	                     @RequestParam("telefono") String tel,
	                     @RequestParam("fechaNacimiento") String fec,
	                     @RequestParam("correo") String correo,
	                     @RequestParam("contraseña") String pass,
	                     @RequestParam("estado") Boolean esta,
	                     @RequestParam("rol") Integer rol,
	                     @RequestParam("grupo") Integer grupo,
	                     RedirectAttributes redirect) {

	    try {
	    	
	        if (cod == 0) {
	            if (servicioUsuario.existeDni(dni)) {
	                redirect.addFlashAttribute("MENSAJE1", "DNI ya existente");
	            } else if (servicioUsuario.existeTelefono(tel)) {
	                redirect.addFlashAttribute("MENSAJE1", "Teléfono ya existente");
	            } else if (servicioUsuario.existeCorreo(correo)) {
	                redirect.addFlashAttribute("MENSAJE1", "Correo ya existente");
	            //PC1-SANCHEZANAYAINICIO
	            } else if (servicioUsuario.existeJefePrestamistaEnGrupo(rol, grupo)) {
	            	redirect.addFlashAttribute("MENSAJE1", "Un jefe prestamista ya está asignado a este grupo");
			    //PC1-SANCHEZANAYAFIN
	            } else {
	                // Si las validaciones pasa, se registrara el usuario
	                Usuario u = new Usuario();
	                u.setNombre(nom);
	                u.setApellido(ape);
	                u.setDni(dni);
	                u.setTelefono(tel);
	                u.setFechaNacimiento(LocalDate.parse(fec));
	                u.setCorreo(correo);
	                u.setClave(encoder.encode(pass));
	                u.setEstado(esta);
	                Rol r = new Rol();
	                r.setCodigo(rol);
	                u.setRol(r);
	                Grupo g = new Grupo();
	                g.setCodigo(grupo);
	                u.setGrupo(g);
	                
	                servicioUsuario.registrar(u);
	                redirect.addFlashAttribute("MENSAJE", "Usuario registrado");
	            }
	        } else {
	            // En caso el codigo sea diferente de 0, se actualizara el usuario
	            Usuario u = servicioUsuario.buscarPorId(cod);
	            if (u != null) {
	            	// Validamos si el correo que se va ingresar ya existe en la bd, en caso exista manda un mensaje de correo ya existente
	            	// Caso contrario se actualiza al usuario
	                if (!correo.equals(u.getCorreo()) && servicioUsuario.existeCorreo(correo)) {
	                    redirect.addFlashAttribute("MENSAJE1", "Correo ya existente");
	                } else if (!dni.equals(u.getDni()) && servicioUsuario.existeDni(dni)) {
	                	redirect.addFlashAttribute("MENSAJE1", "DNI ya existente");
	                } else if (!tel.equals(u.getTelefono()) && servicioUsuario.existeTelefono(tel)) {
	                	redirect.addFlashAttribute("MENSAJE1", "Teléfono ya existente");
	                //PC1-SUAÑA |Validar si el grupo ya está asignado (solo para Jefe de Prestamista)|
	                } else if (!grupo.equals(u.getGrupo().getCodigo()) && servicioUsuario.existeJefePrestamistaEnGrupo(rol, grupo) && rol == 2) {
	                	redirect.addFlashAttribute("MENSAJE1", "Grupo ya asignado");
	                //PC1-RUBINASANCHEZINICIO
	                } else if (!rol.equals(u.getRol().getCodigo())) {
	                	redirect.addFlashAttribute("MENSAJE1", "No se puede actualizar el rol");
	                //PC1-RUBINASANCHEZFIN
	                } else {
	                    u.setNombre(nom);
	                    u.setApellido(ape);
	                    u.setDni(dni);
	                    u.setTelefono(tel);
	                    u.setFechaNacimiento(LocalDate.parse(fec));
	                    u.setCorreo(correo);
	                    u.setEstado(esta);
	                    Rol r = new Rol();
	                    r.setCodigo(rol);
	                    u.setRol(r);
	                    Grupo g = new Grupo();
	                    g.setCodigo(grupo);
	                    u.setGrupo(g);

	                    servicioUsuario.actualizar(u);
	                    redirect.addFlashAttribute("MENSAJE", "Usuario actualizado");
	                }
	                
	            } else {
	                redirect.addFlashAttribute("MENSAJE1", "Usuario no encontrado");
	            }
	        }
	    } catch (Exception e) {
	        redirect.addFlashAttribute("MENSAJE1", "Error en el registro");
	        e.printStackTrace();
	    }
	    return "redirect:/usuario/lista";
	}
	
	@RequestMapping("/buscar")
	@ResponseBody
	public Usuario buscarPorCodigo(@RequestParam("codigo") Integer cod) {
		return servicioUsuario.buscarPorId(cod);
	}
	//PC1-SANCHEZANAYAINICIO
	@RequestMapping("/eliminar")
	public String eliminarPorCodigo(@RequestParam("codigoEliminar") Integer cod,
	                                RedirectAttributes redirect) {
	    try {
	        Usuario usuario = servicioUsuario.buscarPorId(cod);

	        if (usuario != null) {
	            
	            usuario.setEstado(false);
	            servicioUsuario.actualizar(usuario);
	            redirect.addFlashAttribute("MENSAJE", "Usuario marcado como inactivo");
	        } else {
	            redirect.addFlashAttribute("MENSAJE1", "Usuario no encontrado");
	        }
	    } catch (Exception e) {
	        redirect.addFlashAttribute("MENSAJE1", "Error al marcar el usuario como inactivo");
	        e.printStackTrace();
	    }

	    return "redirect:/usuario/lista";
	}
	//PC1-SANCHEZANAYAFIN
}
