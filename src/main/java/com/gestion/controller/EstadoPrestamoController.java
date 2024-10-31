package com.gestion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.gestion.entity.Cuota;
import com.gestion.entity.EstadoPrestamoDTO;
import com.gestion.services.EstadoPrestamoService;
import com.gestion.services.PrestamoService;
import com.gestion.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/estado")
public class EstadoPrestamoController {
	
	@Autowired
	private EstadoPrestamoService servicioEstado;

	@Autowired
	private UsuarioService servicioUsuario;
	
	@Autowired
	private PrestamoService servicioPrestamo;
	
	
	@RequestMapping("/prestamos")
	public String lista(Model model,
						@SessionAttribute(name = "grupo", required = false) Integer grupo,
						HttpSession session) {
		
		model.addAttribute("prestamistas", servicioUsuario.listarPrestamistasPorGrupo(grupo));
		model.addAttribute("totalprestamoss", servicioPrestamo.contarPrestamosGrupo(grupo));
		return "estado";
	}
	
	@GetMapping("/filtrarPorPrestamista")
	@ResponseBody
	public List<EstadoPrestamoDTO> filtrarPorPrestamista(@RequestParam("prestamista") Integer codPres) {
	    // Lógica para filtrar los préstamos por fecha y código de grupo
	    List<EstadoPrestamoDTO> prestamosFiltrados = servicioEstado.obtenerInformacionPrestamosPorPrestamista(codPres);
	    return prestamosFiltrados;
	}

}
