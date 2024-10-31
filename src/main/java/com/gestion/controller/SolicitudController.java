package com.gestion.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.entity.CuentaBancaria;
import com.gestion.entity.Prestamo;
import com.gestion.entity.TipoPrestamo;
import com.gestion.entity.Usuario;
import com.gestion.services.CuentaBancariaService;
import com.gestion.services.PrestamoService;
import com.gestion.services.TipoPrestamoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/solicitud")
public class SolicitudController {
	
	@Autowired
	private PrestamoService servicePrestamo;
	
	@Autowired
	private CuentaBancariaService servicioCuenta;
	
	@Autowired
	private TipoPrestamoService servicioTipo;
	
	@GetMapping("/lista")
    public String index(Model model,
			            @SessionAttribute(name = "CODIGO_USUARIO", required = false) int codigo,
			            HttpSession session) {
		
		model.addAttribute("solicitud", servicePrestamo.listaPrestamoPorPrestatario(codigo));
		model.addAttribute("cuentas", servicioCuenta.listarCuentasBancariasPorUsuario(codigo));
		model.addAttribute("tipos", servicioTipo.listarTodos());
		model.addAttribute("cantidad", servicePrestamo.prestamosPorUsuario(codigo));
       
		return "solicitud";
    }
	
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("dias") int dias,
	                     @RequestParam("monto") BigDecimal monto,
	                     @RequestParam("cuenta") int cuenta,
	                     @RequestParam("tipo") int tipo,
	                     @RequestParam("interes") BigDecimal interes,
	                     @SessionAttribute("CODIGO_USUARIO") Integer usu,
	                     RedirectAttributes redirect) {

	    try {
	    	

	    	// Contar las solicitudes del usuario en la fecha actual
	        long solicitudesDelDia = servicePrestamo.contarSolicitudesPorUsuarioYFecha(usu, LocalDate.now());

	        // Definir el límite de solicitudes permitidas por día
	        int limiteSolicitudesPorDia = 1;

	        if (solicitudesDelDia < limiteSolicitudesPorDia) {
	            // El usuario puede hacer una nueva solicitud
	        	
	        Prestamo p = new Prestamo();
	        p.setDias(dias);
	        BigDecimal tasa = BigDecimal.valueOf(20.00);
	        p.setTasa(tasa);

	        // Esto obtiene la fecha actual con formato de java.sql
	        Date fechaActual = new Date(System.currentTimeMillis());
	        p.setFechaPrestamo(fechaActual);
	        p.setMonto(monto);
	        p.setInteres(interes);
	        CuentaBancaria cb = new CuentaBancaria();
	        cb.setCodigo(cuenta);
	        p.setCuenta(cb);
	        p.setEstado(0);
	        Usuario u = new Usuario();
	        u.setCodigo(usu);
	        p.setPrestatario(u);
	        TipoPrestamo tp = new TipoPrestamo();
	        tp.setCodigo(tipo);
	        p.setTipoPrestamo(tp);

	        servicePrestamo.registrar(p);
	        redirect.addFlashAttribute("MENSAJE", "Solicitud Registrada");
	        } else {
	            // El usuario ha alcanzado el límite de solicitudes para el día
	            redirect.addFlashAttribute("MENSAJE1", "Solo se puede realizar 1 solicitud por día.");
	        }

	    } catch (Exception e) {
	        redirect.addFlashAttribute("MENSAJE1", "Error en el registro");
	        e.printStackTrace();
	    }

	    return "redirect:/solicitud/lista";
	}

	

}
