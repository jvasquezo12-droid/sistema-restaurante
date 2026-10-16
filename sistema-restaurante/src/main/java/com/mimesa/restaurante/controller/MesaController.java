package com.mimesa.restaurante.controller;

import com.mimesa.restaurante.repository.EstadoMesaRepository;
import com.mimesa.restaurante.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mesas")
public class MesaController {

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private EstadoMesaRepository estadoMesaRepository;

    @GetMapping
    public String listarMesas(@RequestParam(value = "estadoId", required = false) Integer estadoId, Model model) {
        if (estadoId != null) {
            model.addAttribute("mesas", mesaRepository.findByEstado_IdEstadoMesa(estadoId));
        } else {
            model.addAttribute("mesas", mesaRepository.findAll());
        }
        model.addAttribute("estados", estadoMesaRepository.findAll());
        return "mesas/lista";
    }
}