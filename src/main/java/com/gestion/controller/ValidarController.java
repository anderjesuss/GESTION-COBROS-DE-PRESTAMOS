package com.gestion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gestion.entity.Enlace;
import com.gestion.entity.Usuario;
import com.gestion.services.UsuarioService;

@SessionAttributes({"datosUsuario", "enlaces", "CODIGO_USUARIO","tipoUsuario","grupo","rol","datosPrestatario"})
@Controller
@RequestMapping("/validar")
public class ValidarController {

    @Autowired
    private UsuarioService servicio;

    @GetMapping("/usuario")
    public String index() {
        return "login";
    }

    @RequestMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
    	
    	model.addAttribute("totalPrestamistas", servicio.obtenerCantidadUsuariosPorDeRolPrestamistas());
    	model.addAttribute("totalJefePrestamistas", servicio.obtenerCantidadUsuariosPorDeRolJefePrestamistas());
    	model.addAttribute("totalPrestatarios", servicio.obtenerCantidadUsuariosPorDeRolPrestatarios());
    	
        // Verificar si el objeto Authentication es nulo o si el usuario no está autenticado
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/validar/usuario";
        }

        String nomUsuario = auth.getName();
        Usuario bean = servicio.loginUsuario(nomUsuario);
        List<Enlace> lista = servicio.enlacesDelUsuario(bean.getRol().getCodigo());
        model.addAttribute("datosUsuario", bean.getNombre());
        model.addAttribute("datosPrestatario", bean.getApellido() + " " + bean.getNombre());
        model.addAttribute("tipoUsuario", bean.getRol().getDescripcion());
        model.addAttribute("enlaces", lista);
        model.addAttribute("CODIGO_USUARIO", bean.getCodigo());
        model.addAttribute("grupo", bean.getGrupo().getCodigo());
        model.addAttribute("rol", bean.getRol().getCodigo());

        return "dashboard";
    }
    
    @RequestMapping("/register")
    public String register() {
    	return "register";
    }
}
