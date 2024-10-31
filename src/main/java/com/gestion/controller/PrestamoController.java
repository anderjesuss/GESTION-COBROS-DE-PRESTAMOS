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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.dao.PrestamoRepository;
import com.gestion.entity.Prestamo;
import com.gestion.entity.Usuario;
import com.gestion.services.CuotaService;
import com.gestion.services.PrestamoService;
import com.gestion.services.TipoPrestamoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/prestamo")
public class PrestamoController {
	
	@Autowired
	private PrestamoService servicePrestamo;
	
	@Autowired
	private TipoPrestamoService servicioTipo;
	
	@Autowired
	private CuotaService servicioCuota;
	
	@Autowired
	private PrestamoRepository repoPrestamo;
	
	@GetMapping("/lista")
    public String index(Model model,
			            @SessionAttribute(name = "grupo", required = false) int codGrupo,
			            HttpSession session) {
		
		model.addAttribute("prestamos", servicePrestamo.listarPrestamosPorGrupo(codGrupo));
		model.addAttribute("tipos", servicioTipo.listarTodos());
		model.addAttribute("prestamoGrupo", servicePrestamo.contarPrestamosGrupo(codGrupo));
		model.addAttribute("totalprestamos", servicePrestamo.obtenerCantidadPrestamos());
		
        return "prestamo";
    }
	
	@RequestMapping("/aprobar")
	public String grabar(@RequestParam("codigoPres") Integer codPres,
						 @SessionAttribute("CODIGO_USUARIO") Integer prestamista,
	                     RedirectAttributes redirect) {
	    try {
	        Prestamo prestamo = servicePrestamo.buscarPorId(codPres);
	        if (prestamo != null) {
	            if (prestamo.getEstado() == 1) {
	                redirect.addFlashAttribute("MENSAJE1", "El préstamo ya está aprobado");
	            } else if (prestamo.getEstado() == 2) {
	                redirect.addFlashAttribute("MENSAJE1", "El préstamo ya está cancelado");
	            } else {
	                servicioCuota.registrarCuota(codPres);
	                
	                // Actualizar la tabla Prestamo con el código del prestamista
	                Usuario u = new Usuario();
	                u.setCodigo(prestamista);
	                prestamo.setPrestamista(u);
	                servicePrestamo.actualizar(prestamo);
	                
	                redirect.addFlashAttribute("MENSAJE", "Prestamo aprobado correctamente");
	            }
	        } else {
	            redirect.addFlashAttribute("MENSAJE1", "No se encontró el préstamo");
	        }
	    } catch (Exception e) {
	        redirect.addFlashAttribute("MENSAJE1", "Error en el registro");
	        e.printStackTrace();
	    }
	    return "redirect:/prestamo/lista";
	}


	
	@RequestMapping("/buscar")
	@ResponseBody
	public Prestamo buscarPorCodigo(@RequestParam("codigo") Integer cod) {
		return servicePrestamo.buscarPorId(cod);
	}
	
	@RequestMapping("/cancelar")
	public String cancelarPrestamo(@RequestParam("codigoEliminar") Integer codPres,
	                       RedirectAttributes redirect) {
	    try {
	        Prestamo prestamo = servicePrestamo.buscarPorId(codPres);
	        
	        if (prestamo != null) {
	            if (prestamo.getEstado() == 1) {
	                redirect.addFlashAttribute("MENSAJE1", "El préstamo ya está activo y no se puede cancelar");
	            } else {
	                prestamo.setEstado(2);
	                repoPrestamo.save(prestamo);
	                redirect.addFlashAttribute("MENSAJE", "Prestamo cancelado correctamente");
	            }
	        } else {
	            redirect.addFlashAttribute("MENSAJE1", "No se encontró el préstamo");
	        }
	    } catch (Exception e) {
	        redirect.addFlashAttribute("MENSAJE1", "Error al cancelar el préstamo");
	        e.printStackTrace();
	    }
	    return "redirect:/prestamo/lista";
	}
	
	@GetMapping("/filtrarPorFecha")
	@ResponseBody
	public List<Prestamo> filtrarPorFecha(@RequestParam("fechaInicio") String fechaInicio, 
	                                      @RequestParam("fechaFin") String fechaFin,
	                                      @RequestParam("codGrupo") Integer codGrupo) {
	    // Lógica para filtrar los préstamos por fecha y código de grupo
	    List<Prestamo> prestamosFiltrados = servicePrestamo.buscarPorFechasYGrupo(fechaInicio, fechaFin, codGrupo);
	    return prestamosFiltrados;
	}





}
