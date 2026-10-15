package com.mimesa.restaurante.controller;

import com.mimesa.restaurante.model.EstadoMesa;
import com.mimesa.restaurante.model.Mesa;
import com.mimesa.restaurante.service.EstadoMesaService;
import com.mimesa.restaurante.service.MesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @Autowired
    private EstadoMesaService estadoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("mesas", mesaService.listarTodos());
        return "mesas/lista";
    }

    @GetMapping("/nueva")
    public String formularioNueva(Model model) {
        Mesa mesa = new Mesa();
        mesa.setEstado(new EstadoMesa());
        model.addAttribute("mesa", mesa);
        model.addAttribute("estados", estadoService.listarTodos());
        return "mesas/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Integer id, Model model,
                                   RedirectAttributes redirect) {
        Mesa mesa = mesaService.buscarPorId(id);
        if (mesa == null) {
            redirect.addFlashAttribute("error", "La mesa no existe");
            return "redirect:/mesas";
        }
        model.addAttribute("mesa", mesa);
        model.addAttribute("estados", estadoService.listarTodos());
        return "mesas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("mesa") Mesa mesa,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirect) {
        if (mesa.getEstado() == null || mesa.getEstado().getIdEstadoMesa() == null) {
            result.rejectValue("estado", "estado.requerido", "Seleccione un estado");
        }
        if (mesa.getNumero() != null && !result.hasFieldErrors("numero")
                && mesaService.numeroEnUso(mesa.getNumero(), mesa.getIdMesa())) {
            result.rejectValue("numero", "numero.duplicado", "Ya existe una mesa con ese número");
        }
        if (result.hasErrors()) {
            // Se vuelve a cargar la lista de estados para que el desplegable no aparezca vacío
            model.addAttribute("estados", estadoService.listarTodos());
            return "mesas/formulario";
        }
        mesaService.guardar(mesa);
        redirect.addFlashAttribute("exito", "Mesa guardada correctamente");
        return "redirect:/mesas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            mesaService.eliminar(id);
            redirect.addFlashAttribute("exito", "Mesa eliminada correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error",
                    "No se puede eliminar esta mesa porque tiene pedidos o reservaciones asociados");
        }
        return "redirect:/mesas";
    }
}
