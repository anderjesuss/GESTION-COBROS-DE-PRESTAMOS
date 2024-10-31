package com.gestion.controller;

import java.math.BigDecimal;
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

import com.gestion.entity.CuentaBancaria;
import com.gestion.entity.Cuota;
import com.gestion.entity.Pago;
import com.gestion.entity.TipoCuentaBancaria;
import com.gestion.entity.Usuario;
import com.gestion.services.CuotaService;
import com.gestion.services.PagoService;
import com.gestion.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pago")
public class CuotaController {
	
	@Autowired
	private CuotaService servicioCuota;
	
	@Autowired
	private UsuarioService servicioUsuario;
	
	@Autowired 
	private PagoService servicioPago;
	
	@RequestMapping("/lista")
	public String lista(Model model,
						@SessionAttribute(name = "grupo", required = false) Integer grupo,
						HttpSession session) {
		model.addAttribute("prestatarios", servicioUsuario.listarPrestatariosPorGrupo(grupo));
		model.addAttribute("cantidadCuotas", servicioCuota.obtenerCantidadCuotasPorGrupo(grupo));
		return "cuota";
	}
	
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("cuota") Integer codCuota,
	                     @SessionAttribute(name = "CODIGO_USUARIO", required = false) int codigo,
	                     @RequestParam("monto") BigDecimal monto,
	                     @RequestParam("montoCuota") BigDecimal montoFijo,
	                     @RequestParam("mora") BigDecimal mora,
	                     @RequestParam("pagar") BigDecimal pago,
	                     RedirectAttributes redirect) {

	    try {
	        // Obtener la cuota
	        Cuota cuota = servicioCuota.filtrarCuotaPorID(codCuota);

	        // Validar si la cuota ya está pagada
	        if (cuota != null && cuota.getEstado() == 1) {
	            redirect.addFlashAttribute("MENSAJE1", "La cuota ya ha sido pagada anteriormente.");
	            return "redirect:/pago/lista";
	        }

	        // Calcular monto total (monto de la cuota + mora)
	        BigDecimal montoCuota = monto;
	        BigDecimal moraCuota = mora;

	        if (montoCuota == null) {
	            montoCuota = BigDecimal.ZERO;
	        }

	        if (moraCuota == null) {
	            moraCuota = BigDecimal.ZERO;
	        }
	        
	        BigDecimal montoTotal = montoCuota.add(moraCuota);

	        // Verificar si ya existe un pago para esta cuota
	        Pago pagoExistente = servicioPago.obtenerPagoPorCuota(codCuota);

	        // Registrar o actualizar el pago
	        Pago p = new Pago();
	        Cuota c = new Cuota();
	        c.setCodigo(codCuota);
	        p.setCuota(c);
	        Usuario u = new Usuario();
	        u.setCodigo(codigo);
	        p.setUsuario(u);
	        
	     // Verificar si las variables p.getMontoPagoTotal() y p.getMoraPago() son nulas y asignarles un valor por defecto si es necesario
	        if (p.getMontoPagoTotal() == null) {
	            p.setMontoPagoTotal(BigDecimal.ZERO);
	        }

	        if (p.getMoraPago() == null) {
	            p.setMoraPago(BigDecimal.ZERO);
	        }

	        // Lógica de pago
	        if (pago.compareTo(montoTotal) >= 0) {
	            // Pago completo
	            p.setMontoPago(montoFijo);
	            cuota.setEstado(1);
	            cuota.setMonto(BigDecimal.ZERO);
	            cuota.setMora(BigDecimal.ZERO);
	            p.setMontoPagoTotal(pago);
	            p.setMoraPago(mora);
	            
	        } else if (pago.compareTo(moraCuota) >= 0) {
	            // Pago que cubre la mora y parte de la cuota
	            p.setMoraPago(moraCuota);
	            BigDecimal montoRestante = pago.subtract(moraCuota);
	            p.setMontoPago(montoFijo);
	            cuota.setEstado(2);
	            cuota.setMonto(montoCuota.subtract(montoRestante));
	            cuota.setMora(moraCuota.subtract(montoRestante));
	            p.setMontoPagoTotal(pago);
	        }

	        // Actualizar la cuota
	        cuota.setFechaRegPago(new java.sql.Date(System.currentTimeMillis()));
	        servicioCuota.actualizar(cuota);

	        // Verificar y registrar o actualizar el pago
	        if (pagoExistente != null) {
	            // Ya hay un pago existente, actualizarlo
	            p.setCodigo(pagoExistente.getCodigo());

	            // Actualizar montoPagoTotal y moraPago
	            BigDecimal montoPagadoExistente = pagoExistente.getMontoPagoTotal();
	            BigDecimal moraPagadaExistente = pagoExistente.getMoraPago();

	            if (montoPagadoExistente == null) {
	                montoPagadoExistente = BigDecimal.ZERO;
	            }

	            if (moraPagadaExistente == null) {
	                moraPagadaExistente = BigDecimal.ZERO;
	            }

	            BigDecimal nuevoMontoPagado = montoPagadoExistente.add(p.getMontoPagoTotal());
	            BigDecimal nuevaMoraPagada = moraPagadaExistente.add(p.getMoraPago());

	            p.setMontoPagoTotal(nuevoMontoPagado);
	            p.setMoraPago(nuevaMoraPagada);

	            servicioPago.actualizar(p);
	        } else {
	            // No hay un pago existente, registrar uno nuevo
	            servicioPago.registrar(p);
	        }

	        redirect.addFlashAttribute("MENSAJE", "Pago registrado");

	    } catch (Exception e) {
	        redirect.addFlashAttribute("MENSAJE1", "Error en el pago");
	        e.printStackTrace();
	    }
	    return "redirect:/pago/lista";
	}
	
	@GetMapping("/filtrarPorPrestatario")
	@ResponseBody
	public List<Cuota> filtrarPorPrestatario(@RequestParam("prestatario") Integer codPres) {
	    // Lógica para filtrar los préstamos por fecha y código de grupo
	    List<Cuota> cuotasFiltradas = servicioCuota.listarCuotasPorPrestatarios(codPres);
	    return cuotasFiltradas;
	}
	
	@RequestMapping("/buscar")
	@ResponseBody
	public Cuota buscarPorCodigo(@RequestParam("codigo") Integer cod) {
		return servicioCuota.buscarCuotaPorId(cod);
		
	}
}
