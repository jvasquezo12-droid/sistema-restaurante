package com.mimesa.restaurante.controller;

import com.mimesa.restaurante.service.IndicadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private IndicadorService indicadorService;

    @GetMapping({"/", "/home"})
    public String inicio(Model model) {
        model.addAttribute("indicadores", indicadorService.obtenerIndicadores());
        return "inicio";
    }
}