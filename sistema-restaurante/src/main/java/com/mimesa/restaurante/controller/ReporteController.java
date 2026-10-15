package com.mimesa.restaurante.controller;

import com.mimesa.restaurante.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteRepository reporteRepository;

    @GetMapping
    public String menuReportes() {
        return "reportes/index";
    }

    @GetMapping("/productos-mas-vendidos")
    public String productosMasVendidos(Model model) {
        model.addAttribute("reporte", reporteRepository.obtenerProductosMasVendidos());
        return "reportes/productos-mas-vendidos";
    }

    @GetMapping("/ventas-por-fecha")
    public String ventasPorFecha(@RequestParam(value = "inicio", required = false, defaultValue = "2026-01-01") String inicio,
                                 @RequestParam(value = "fin", required = false, defaultValue = "2026-12-31") String fin,
                                 Model model) {
        model.addAttribute("reporte", reporteRepository.obtenerVentasPorFecha(inicio, fin));
        model.addAttribute("inicio", inicio);
        model.addAttribute("fin", fin);
        return "reportes/ventas-por-fecha";
    }
}