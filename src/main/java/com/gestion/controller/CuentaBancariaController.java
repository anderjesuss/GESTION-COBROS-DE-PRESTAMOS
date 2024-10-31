package com.gestion.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.entity.CuentaBancaria;
import com.gestion.entity.Grupo;
import com.gestion.entity.Rol;
import com.gestion.entity.TipoCuentaBancaria;
import com.gestion.entity.Usuario;
import com.gestion.services.CuentaBancariaService;
import com.gestion.services.TipoCuentaBancariaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cuenta")
public class CuentaBancariaController {	
	
	@Autowired
	private CuentaBancariaService servicioCuenta;
	
	@Autowired
	private TipoCuentaBancariaService servicioTipo;
	
	@GetMapping("/lista")
    public String index(Model model,
			            @SessionAttribute(name = "CODIGO_USUARIO", required = false) int codigo,
			            HttpSession session) {
			
			model.addAttribute("tipos", servicioTipo.listarTodos());
			model.addAttribute("cuentas", servicioCuenta.listarCuentasBancariasPorUsuario(codigo));
			model.addAttribute("cuentaUsu", servicioCuenta.cuentasPorUsuario(codigo));
			
			
        return "cuenta";
    }
	
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("numero") String numCuenta,
						 @SessionAttribute(name = "CODIGO_USUARIO", required = false) int codigo,
	                     @RequestParam("tipo") int tipo,
	                     RedirectAttributes redirect) {

	    try {
	    	
	    	if (servicioCuenta.existeNumeroDeCuenta(numCuenta)) {
	    		redirect.addFlashAttribute("MENSAJE1", "Numero de cuenta ya existente");
	    	} else {
	    		CuentaBancaria cb = new CuentaBancaria();
	    		cb.setNumeroCuenta(numCuenta);
	    		BigDecimal saldo = BigDecimal.valueOf(0.00);
	    		cb.setSaldo(saldo);
	    		Usuario u = new Usuario();
	    		u.setCodigo(codigo);
	    		cb.setUsuario(u);
	    		TipoCuentaBancaria tcb = new TipoCuentaBancaria();
	    		tcb.setCodigo(tipo);
	    		cb.setBanco(tcb);
	    		
	    		servicioCuenta.registrar(cb);
	    		redirect.addFlashAttribute("MENSAJE", "Cuenta bancaria registrada");
	    	}
	    	
	    } catch (Exception e) {
	        redirect.addFlashAttribute("MENSAJE1", "Error en el registro");
	        e.printStackTrace();
	    }
	    return "redirect:/cuenta/lista";
	}
	
	@RequestMapping("/buscar")
	@ResponseBody
	public CuentaBancaria buscarPorCodigo(@RequestParam("codigo") Integer cod) {
		return servicioCuenta.buscarPorID(cod);
	}

}
