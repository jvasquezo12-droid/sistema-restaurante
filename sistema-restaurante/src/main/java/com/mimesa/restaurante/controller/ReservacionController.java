package com.mimesa.restaurante.controller;

import com.mimesa.restaurante.model.Cliente;
import com.mimesa.restaurante.model.EstadoReservacion;
import com.mimesa.restaurante.model.Mesa;
import com.mimesa.restaurante.model.Reservacion;
import com.mimesa.restaurante.service.ClienteService;
import com.mimesa.restaurante.service.MesaService;
import com.mimesa.restaurante.service.ReservacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reservaciones")
public class ReservacionController {

    @Autowired
    private ReservacionService reservacionService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MesaService mesaService;

    @GetMapping
    public String listar(@RequestParam(value = "estadoId", required = false) Integer estadoId,
                         Model model) {
        if (estadoId != null) {
            model.addAttribute("reservaciones", reservacionService.buscarPorEstado(estadoId));
        } else {
            model.addAttribute("reservaciones", reservacionService.listarTodas());
        }
        model.addAttribute("estados", reservacionService.listarEstados());
        model.addAttribute("estadoSeleccionado", estadoId);
        return "reservaciones/lista";
    }

    @GetMapping("/nueva")
    public String formularioNuevo(Model model) {
        Reservacion reservacion = new Reservacion();
        reservacion.setCliente(new Cliente());
        reservacion.setMesa(new Mesa());
        reservacion.setEstado(new EstadoReservacion());
        model.addAttribute("reservacion", reservacion);
        cargarListas(model);
        return "reservaciones/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Integer id, Model model,
                                   RedirectAttributes redirect) {
        Reservacion reservacion = reservacionService.buscarPorId(id);
        if (reservacion == null) {
            redirect.addFlashAttribute("error", "La reservación no existe");
            return "redirect:/reservaciones";
        }
        model.addAttribute("reservacion", reservacion);
        cargarListas(model);
        return "reservaciones/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("reservacion") Reservacion reservacion,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirect) {

        if (reservacion.getCliente() == null || reservacion.getCliente().getIdCliente() == null) {
            result.rejectValue("cliente", "error.cliente", "Debe seleccionar un cliente");
        }
        if (reservacion.getMesa() == null || reservacion.getMesa().getIdMesa() == null) {
            result.rejectValue("mesa", "error.mesa", "Debe seleccionar una mesa");
        }
        if (reservacion.getEstado() == null || reservacion.getEstado().getIdEstadoReservacion() == null) {
            result.rejectValue("estado", "error.estado", "Debe seleccionar el estado de la reservación");
        }

        // Regla del negocio: no se puede exceder la capacidad de la mesa
        if (reservacion.getMesa() != null && reservacion.getMesa().getIdMesa() != null
                && reservacion.getPersonas() != null) {
            Mesa mesa = reservacionService.buscarMesa(reservacion.getMesa().getIdMesa());
            if (mesa != null && reservacion.getPersonas() > mesa.getCapacidad()) {
                result.rejectValue("personas", "error.personas",
                        "La mesa " + mesa.getNumero() + " admite como máximo "
                                + mesa.getCapacidad() + " personas");
            }
        }

        // Regla del negocio: una mesa no puede reservarse dos veces en el mismo horario
        if (reservacion.getMesa() != null && reservacion.getMesa().getIdMesa() != null
                && reservacion.getFecha() != null && reservacion.getHora() != null) {
            boolean ocupado = reservacionService.horarioOcupado(
                    reservacion.getMesa().getIdMesa(),
                    reservacion.getFecha(),
                    reservacion.getHora(),
                    reservacion.getIdReservacion());
            if (ocupado) {
                result.rejectValue("hora", "error.hora",
                        "Esa mesa ya tiene una reservación para esa fecha y hora");
            }
        }

        // Regla del negocio: toda cancelación debe registrar su motivo
        if (reservacion.getEstado() != null && reservacion.getEstado().getIdEstadoReservacion() != null) {
            EstadoReservacion estado = reservacionService.listarEstados().stream()
                    .filter(e -> e.getIdEstadoReservacion()
                            .equals(reservacion.getEstado().getIdEstadoReservacion()))
                    .findFirst().orElse(null);
            boolean cancelada = estado != null && "Cancelada".equalsIgnoreCase(estado.getNombre());
            boolean sinMotivo = reservacion.getMotivoCancelacion() == null
                    || reservacion.getMotivoCancelacion().isBlank();
            if (cancelada && sinMotivo) {
                result.rejectValue("motivoCancelacion", "error.motivo",
                        "Debe indicar el motivo de la cancelación");
            }
            if (!cancelada) {
                reservacion.setMotivoCancelacion(null);
            }
        }

        if (result.hasErrors()) {
            cargarListas(model);
            return "reservaciones/formulario";
        }

        reservacionService.guardar(reservacion);
        redirect.addFlashAttribute("exito", "Reservación guardada correctamente");
        return "redirect:/reservaciones";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            reservacionService.eliminar(id);
            redirect.addFlashAttribute("exito", "Reservación eliminada correctamente");
        } catch (DataIntegrityViolationException e) {
            redirect.addFlashAttribute("error",
                    "No se puede eliminar esta reservación porque está siendo utilizada");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Ocurrió un error al intentar eliminar la reservación");
        }
        return "redirect:/reservaciones";
    }

    private void cargarListas(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("mesas", mesaService.listarTodos());
        model.addAttribute("estados", reservacionService.listarEstados());
    }
}