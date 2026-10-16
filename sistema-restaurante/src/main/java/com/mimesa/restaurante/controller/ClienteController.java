package com.mimesa.restaurante.controller;

import com.mimesa.restaurante.dto.ClienteForm;
import com.mimesa.restaurante.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "clientes/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("cliente", new ClienteForm());
        return "clientes/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Integer id, Model model,
                                   RedirectAttributes redirect) {
        ClienteForm form = clienteService.obtenerFormulario(id);
        if (form == null) {
            redirect.addFlashAttribute("error", "El cliente no existe");
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", form);
        return "clientes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("cliente") ClienteForm cliente,
                          BindingResult result,
                          RedirectAttributes redirect) {
        if (cliente.getEmail() != null && !result.hasFieldErrors("email")
                && clienteService.correoEnUso(cliente.getEmail(), cliente.getIdUsuario())) {
            result.rejectValue("email", "email.duplicado", "Ya existe un usuario con ese correo");
        }
        if (result.hasErrors()) {
            return "clientes/formulario";
        }
        clienteService.guardar(cliente);
        redirect.addFlashAttribute("exito", "Cliente guardado correctamente");
        return "redirect:/clientes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            clienteService.eliminar(id);
            redirect.addFlashAttribute("exito", "Cliente eliminado correctamente");
        } catch (DataIntegrityViolationException e) {
            redirect.addFlashAttribute("error",
                    "No se puede eliminar este cliente porque tiene reservaciones asociadas");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Ocurrió un error al intentar eliminar el cliente");
        }
        return "redirect:/clientes";
    }
}
