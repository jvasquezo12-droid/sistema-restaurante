package com.mimesa.restaurante.controller;

import com.mimesa.restaurante.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteRepository reporteRepository;

    @GetMapping
    public String index() {
        return "reportes/index";
    }

    @GetMapping("/productos-mas-vendidos")
    public String productosMasVendidos(Model model) {
        model.addAttribute("reporte", reporteRepository.obtenerProductosMasVendidos());
        model.addAttribute("titulo", "Reporte de Productos Más Vendidos");
        return "reportes/productos_mas_vendidos";
    }

    @GetMapping("/ventas-por-fecha")
    public String ventasPorFecha(
            @RequestParam(value = "fechaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(value = "fechaFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            Model model) {

        if (fechaInicio == null) fechaInicio = LocalDate.now().minusDays(30);
        if (fechaFin == null) fechaFin = LocalDate.now();

        model.addAttribute("ventas", reporteRepository.obtenerVentasPorFecha(fechaInicio, fechaFin));
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);
        model.addAttribute("titulo", "Reporte de Ventas por Fecha");
        return "reportes/ventas_por_fecha";
    }

    @GetMapping("/reservaciones-periodo")
    public String reservacionesPorPeriodo(
            @RequestParam(value = "fechaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(value = "fechaFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            Model model) {

        if (fechaInicio == null) fechaInicio = LocalDate.now().minusDays(30);
        if (fechaFin == null) fechaFin = LocalDate.now();

        model.addAttribute("reservaciones", reporteRepository.obtenerReservacionesPorPeriodo(fechaInicio, fechaFin));
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);
        model.addAttribute("titulo", "Reporte de Reservaciones por Periodo");
        return "reportes/reservaciones_periodo";
    }
}