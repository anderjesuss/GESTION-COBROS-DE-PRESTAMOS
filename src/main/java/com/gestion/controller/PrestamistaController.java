package com.gestion.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.entity.Grupo;
import com.gestion.entity.Rol;
import com.gestion.entity.Usuario;
import com.gestion.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/prestamista")
public class PrestamistaController {
	
	@Autowired
	private UsuarioService serUsu;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@RequestMapping("/lista")
	public String lista(Model model,
	                    @SessionAttribute(name = "grupo", required = false) Integer grupo,
	                    @SessionAttribute(name = "rol", required = false) Integer rol,
	                    HttpSession session) {

	    // Verificar si 'grupo' es null en lugar de solo verificar 'rol'
	    if (rol != null && grupo != null) {
	        if (rol == 1) {
	            // Validamos que el administrador tendrá acceso a toda la lista de los prestamistas, prestatarios y jefes de prestamistas
	            model.addAttribute("prestamistas", serUsu.listarTodosLosUsuarios());	           
	        } else if (rol == 2) {
	            // Si el rol es 2, mostrar la lista específica para ese rol
	            model.addAttribute("prestamistas", serUsu.obtenerPrestamistasPorRolYGrupo(3, grupo));
	            model.addAttribute("cantidad", serUsu.obtenerCantidadUsuariosPorRolYGrupo(3, grupo));
	        }
	        return "prestamista";
	    } else {
	        return "redirect:/validar/usuario";
	    }
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
			             @SessionAttribute("grupo") Integer grupo,
			             @SessionAttribute("rol") Integer rol,
			             RedirectAttributes redirect) {

	    try {
	        if (cod == 0) {
	            if (serUsu.existeDni(dni)) {
	                redirect.addFlashAttribute("MENSAJE1", "DNI ya existente");
	            } else if (serUsu.existeTelefono(tel)) {
	                redirect.addFlashAttribute("MENSAJE1", "Teléfono ya existente");
	            } else if (serUsu.existeCorreo(correo)) {
	                redirect.addFlashAttribute("MENSAJE1", "Correo ya existente");
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
    	            r.setCodigo(3);
    	            u.setRol(r);
    	            Grupo g = new Grupo();
    	            g.setCodigo(grupo);
    	            u.setGrupo(g);

	                serUsu.registrar(u);
	                redirect.addFlashAttribute("MENSAJE", "Prestamista registrado");
	            }
	        } else {
	            // En caso el codigo sea diferente de 0, se actualizara el usuario
	            Usuario u = serUsu.buscarPorId(cod);
	            if (u != null) {
	            	// Validamos si el correo que se va ingresar ya existe en la bd, en caso exista manda un mensaje de correo ya existente
	            	// Caso contrario se actualiza al usuario
	                if (!correo.equals(u.getCorreo()) && serUsu.existeCorreo(correo)) {
	                    redirect.addFlashAttribute("MENSAJE1", "Correo ya existente");
	                } else if (!dni.equals(u.getDni()) && serUsu.existeDni(dni)) {
	                	redirect.addFlashAttribute("MENSAJE1", "DNI ya existe");
	                } else if (!tel.equals(u.getTelefono()) && serUsu.existeTelefono(tel)) {
	                	redirect.addFlashAttribute("MENSAJE1", "Teléfono ya existente");
	                } else {
	    	            u.setNombre(nom);
	    	            u.setApellido(ape);
	    	            u.setDni(dni);
	    	            u.setTelefono(tel);
	    	            u.setFechaNacimiento(LocalDate.parse(fec));
	    	            u.setCorreo(correo);
	    	            u.setEstado(esta);
	    	            Rol r = new Rol();
	    	            r.setCodigo(3);
	    	            u.setRol(r);
	    	            Grupo g = new Grupo();
	    	            g.setCodigo(grupo);
	    	            u.setGrupo(g);

	    	            serUsu.actualizar(u);
	                    redirect.addFlashAttribute("MENSAJE", "Prestamista actualizado");
	                }
	            } else {
	                redirect.addFlashAttribute("MENSAJE1", "Prestamista no encontrado");
	            }
	        }
	    } catch (Exception e) {
	        redirect.addFlashAttribute("MENSAJE1", "Error en el registro");
	        e.printStackTrace();
	    }
	    return "redirect:/prestamista/lista";
	}

	@RequestMapping("/buscar")
	@ResponseBody
	public Usuario buscarPorCodigo(@RequestParam("codigo") Integer cod) {
		return serUsu.buscarPorId(cod);
	}
	
	//PC1-SANCHEZANAYAINICIO
	@RequestMapping("/eliminar")
	public String eliminarPorCodigo(@RequestParam("codigoEliminar") Integer cod,
	                                RedirectAttributes redirect) {
	    try {
	        Usuario usuario = serUsu.buscarPorId(cod);

	        if (usuario != null) {
	           
	            usuario.setEstado(false);
	            serUsu.actualizar(usuario);
	            redirect.addFlashAttribute("MENSAJE", "Prestamista marcado como inactivo");
	        } else {
	            redirect.addFlashAttribute("MENSAJE1", "Prestamista no encontrado");
	        }
	    } catch (Exception e) {
	        redirect.addFlashAttribute("MENSAJE1", "Error al marcar al prestamista como inactivo");
	        e.printStackTrace();
	    }

	    return "redirect:/prestamista/lista";
	}
	//PC1-SANCHEZANAYAFIN
	
}
