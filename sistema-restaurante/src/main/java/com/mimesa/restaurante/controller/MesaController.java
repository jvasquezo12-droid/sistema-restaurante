package com.mimesa.restaurante.controller;

import com.mimesa.restaurante.model.EstadoMesa;
import com.mimesa.restaurante.model.Mesa;
import com.mimesa.restaurante.repository.EstadoMesaRepository;
import com.mimesa.restaurante.repository.MesaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

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

    @GetMapping("/nueva")
    public String formularioNueva(Model model) {
        Mesa mesa = new Mesa();
        mesa.setEstado(new EstadoMesa());
        model.addAttribute("mesa", mesa);
        model.addAttribute("estados", estadoMesaRepository.findAll());
        return "mesas/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Integer id, Model model,
                                   RedirectAttributes redirect) {
        Mesa mesa = mesaRepository.findById(id).orElse(null);
        if (mesa == null) {
            redirect.addFlashAttribute("error", "La mesa no existe");
            return "redirect:/mesas";
        }
        model.addAttribute("mesa", mesa);
        model.addAttribute("estados", estadoMesaRepository.findAll());
        return "mesas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("mesa") Mesa mesa,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirect) {

        if (mesa.getEstado() == null || mesa.getEstado().getIdEstadoMesa() == null) {
            result.rejectValue("estado", "error.estado", "Debe seleccionar el estado de la mesa");
        }

        // Regla del negocio: el número de mesa no se puede repetir
        if (mesa.getNumero() != null) {
            Optional<Mesa> repetida = mesaRepository.findByNumero(mesa.getNumero());
            if (repetida.isPresent() && !repetida.get().getIdMesa().equals(mesa.getIdMesa())) {
                result.rejectValue("numero", "error.numero",
                        "Ya existe una mesa con el número " + mesa.getNumero());
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("estados", estadoMesaRepository.findAll());
            return "mesas/formulario";
        }

        mesaRepository.save(mesa);
        redirect.addFlashAttribute("exito", "Mesa guardada correctamente");
        return "redirect:/mesas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            mesaRepository.deleteById(id);
            redirect.addFlashAttribute("exito", "Mesa eliminada correctamente");
        } catch (DataIntegrityViolationException e) {
            redirect.addFlashAttribute("error",
                    "No se puede eliminar esta mesa porque tiene reservaciones o pedidos asociados");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Ocurrió un error al intentar eliminar la mesa");
        }
        return "redirect:/mesas";
    }
}