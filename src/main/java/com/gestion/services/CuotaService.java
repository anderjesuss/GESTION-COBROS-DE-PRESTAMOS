package com.gestion.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.dao.CuentaBancariaRepository;
import com.gestion.dao.CuotaRepository;
import com.gestion.dao.PrestamoRepository;
import com.gestion.entity.CuentaBancaria;
import com.gestion.entity.Cuota;
import com.gestion.entity.Prestamo;
import com.gestion.entity.Usuario;

import jakarta.transaction.Transactional;


@Service
public class CuotaService {
	
	@Autowired
	private CuotaRepository repoCuota;
	
	@Autowired
	private PrestamoRepository repoPrestamo;
	
	@Autowired
	private CuentaBancariaRepository repoCuenta;
	
	@Transactional
	public void registrarCuota(Integer codPres) {

	    Optional<Prestamo> optPrestamo = repoPrestamo.findById(codPres);
	    if (optPrestamo.isPresent()) {

	        Prestamo objprestamo = optPrestamo.get();
	        if (objprestamo.getEstado() == 1) {
	            return;
	        } else {

	            int dias = optPrestamo.get().getDias();
	            BigDecimal tasa = optPrestamo.get().getTasa();
	            Date fechaPrestamo = optPrestamo.get().getFechaPrestamo();
	            BigDecimal montoSolicitado = optPrestamo.get().getMonto();

	            // Generar las cuotas
	            BigDecimal tasaDiaria = BigDecimal.valueOf(Math.pow((tasa.divide(new BigDecimal(100)).add(BigDecimal.valueOf(1)).doubleValue()), (1.0 / 30.0)) - 1);
	            BigDecimal interes = montoSolicitado.multiply(tasaDiaria).multiply(BigDecimal.valueOf(dias));
	            BigDecimal montoTotal = montoSolicitado.add(interes);
	            BigDecimal cuota = montoTotal.divide(BigDecimal.valueOf(dias), RoundingMode.HALF_UP);

	            Calendar cal = Calendar.getInstance();
	            cal.setTime(new Date());  // Inicializar el calendario con la fecha actual

	            Cuota objCuota = null;

	            for (int i = 1; i <= dias; i++) {
	                objCuota = new Cuota();
	                objCuota.setNumero(i);
	                objCuota.setMonto(cuota);
	                objCuota.setMontoFijo(cuota);

	                java.sql.Date fechaSql = new java.sql.Date(cal.getTime().getTime());
	                objCuota.setFechaPago(fechaSql);
	                objCuota.setFechaRegPago(fechaSql);

	                objCuota.setPrestamo(optPrestamo.get());
	                repoCuota.save(objCuota);

	                cal.add(Calendar.DAY_OF_YEAR, 1);  // Agregar un día a la fecha para la próxima cuota
	            }

	            Prestamo prestamo = optPrestamo.get();
	            prestamo.setEstado(1);
	            repoPrestamo.save(prestamo);

	            // Para la actualización de la tabla cuenta bancaria
	            CuentaBancaria cuenta = objprestamo.getCuenta();
	            BigDecimal saldoActual = cuenta.getSaldo();
	            BigDecimal nuevoSaldo = saldoActual.add(montoSolicitado);
	            cuenta.setSaldo(nuevoSaldo);
	            repoCuenta.save(cuenta);
	        }
	    }
	}

	
	public List<Cuota> listarCuotasPorPrestatarios(Integer codPres) {
		return repoCuota.listaCuotasPorPrestatario(codPres);
	}
	
	public Cuota buscarCuotaPorId(Integer cod) {
	    Optional<Cuota> cuotaOptional = repoCuota.findById(cod);

	    if (cuotaOptional.isPresent()) {
	        Cuota cuota = cuotaOptional.get();

	        // Verificar el estado de la cuota
	        int estadoCuota = cuota.getEstado();

	        if (estadoCuota == 0 || estadoCuota == 2) { // Si el estado es pendiente o parcial
	            // Calcular la mora directamente en el servicio
	            BigDecimal tasaMensual = BigDecimal.valueOf(0.20);
	            BigDecimal tasaDiaria = BigDecimal.valueOf(Math.pow(1 + tasaMensual.doubleValue(), 1.0 / 30.0) - 1);

	            // Obtener la fecha de pago y la fecha de registro de pago
	            LocalDate fechaPagoLocal = cuota.getFechaPago().toLocalDate();
	            LocalDate fechaRegistroPagoLocal = cuota.getFechaRegPago().toLocalDate();

	            // Calcular los días transcurridos
	            long diasTranscurridos = ChronoUnit.DAYS.between(fechaPagoLocal, LocalDate.now());

	            // Asegurarse de que la mora no sea negativa si no han pasado días desde la fecha de pago
	            BigDecimal mora = diasTranscurridos > 0 ?
	                    cuota.getMonto().multiply(BigDecimal.valueOf(Math.pow(1 + tasaDiaria.doubleValue(), diasTranscurridos) - 1)) :
	                    BigDecimal.ZERO;

	            // Si la fecha de pago es diferente a la fecha de registro de pago, recalcula la mora
	            if (!fechaPagoLocal.equals(fechaRegistroPagoLocal)) {
	                diasTranscurridos = ChronoUnit.DAYS.between(fechaRegistroPagoLocal, LocalDate.now());
	                mora = diasTranscurridos > 0 ?
	                        cuota.getMonto().multiply(BigDecimal.valueOf(Math.pow(1 + tasaDiaria.doubleValue(), diasTranscurridos) - 1)) :
	                        BigDecimal.ZERO;
	            }

	            cuota.setMora(mora.setScale(2, RoundingMode.HALF_UP));
	        } else if (estadoCuota == 1) { // Si el estado es pagado
	            cuota.setMora(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
	        }

	        return cuota;
	    } else {
	        return null; // O manejar de otra manera si no se encuentra la cuota
	    }
	}

	public Cuota filtrarCuotaPorID(Integer cod) {
		return repoCuota.findById(cod).orElse(null);
	}

	public void actualizar(Cuota c) {
		repoCuota.save(c);
	}
	
	public List<Cuota> listarEstadoCuotasPorPrestatario(Integer codPrestatario) {
		return repoCuota.ListarEstadoCuotasPorPrestatario(codPrestatario);
	}
	
	public List<Cuota> listarEstadoCuotasPorRangoFechas(String fechaIni, String fechaFin, Integer codPrestatario) {
		return repoCuota.ListarEstadoCuotasPorRangoFechas(fechaIni, fechaFin, codPrestatario);
	}

	public int obtenerCantidadCuotasPorGrupo(int codGrupo) {
		return repoCuota.cantidadCuotasPorGrupo(codGrupo);
	}
	
	public int obtenerCantidadCuotasPorPrestatario(int codPrestatario) {
		return repoCuota.cantidadCuotasPorPrestatario(codPrestatario);
	}

}
