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
import com.gestion.services.CuotaService;
import com.gestion.services.EstadoPrestamoService;
import com.gestion.services.PrestamoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reporte")
public class EstadoPrestamoPrestatarioController {
	
	@Autowired
	private CuotaService servicioCuota;

	@GetMapping("/prestamo")
    public String index(Model model,
			            @SessionAttribute(name = "CODIGO_USUARIO", required = false) int codigo,
			            HttpSession session) {
		
		model.addAttribute("estadoCuotas", servicioCuota.listarEstadoCuotasPorPrestatario(codigo));		
		model.addAttribute("cuotasxPrestatario", servicioCuota.obtenerCantidadCuotasPorPrestatario(codigo));
		
		return "estadoPrestamo";
    }
	
	@GetMapping("/filtrarPorFecha")
	@ResponseBody
	public List<Cuota> filtrarPorFecha(@RequestParam("fechaInicio") String fechaInicio, 
	                                   @RequestParam("fechaFin") String fechaFin,
	                                   @SessionAttribute("CODIGO_USUARIO") Integer codUsu) {
	    // Lógica para filtrar los préstamos por fecha y código de grupo
	    List<Cuota> estadoCuotasFiltradas = servicioCuota.listarEstadoCuotasPorRangoFechas(fechaInicio, fechaFin, codUsu);
	    return estadoCuotasFiltradas;
	}
}
