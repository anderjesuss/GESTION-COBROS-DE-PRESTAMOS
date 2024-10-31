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
import com.gestion.entity.RendimientoFinanciero;
import com.gestion.services.RendimientoFinancieroService;
import com.gestion.services.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/rendimiento")
public class RendimientoFinancieroController {
	
	@Autowired
	private UsuarioService servicioUsuario;
		
	@Autowired
	private RendimientoFinancieroService servicioFinanciero;
		
		
	@RequestMapping("/prestamos")
	public String lista(Model model) {
		model.addAttribute("jefeprestamistas", servicioUsuario.listarJefePrestamistas());
		return "rendimiento";
	}
	
	@GetMapping("/filtrarRendimiento")
	@ResponseBody
	public List<RendimientoFinanciero> filtrarPorGrupo(@RequestParam("grupo") int grupo) {
	    // Lógica para filtrar los préstamos por fecha y código de grupo
	    List<RendimientoFinanciero> rendimientoFiltrado = servicioFinanciero.filtrarRendimientoFinanciero(grupo);
	    return rendimientoFiltrado;
	}	
	
}
