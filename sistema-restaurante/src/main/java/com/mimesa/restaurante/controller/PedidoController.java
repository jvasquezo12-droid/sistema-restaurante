package com.mimesa.restaurante.controller;

import com.mimesa.restaurante.model.DetallePedido;
import com.mimesa.restaurante.model.EstadoPedido;
import com.mimesa.restaurante.model.Mesa;
import com.mimesa.restaurante.model.Pedido;
import com.mimesa.restaurante.model.ProductoMenu;
import com.mimesa.restaurante.model.Usuario;
import com.mimesa.restaurante.service.MesaService;
import com.mimesa.restaurante.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private MesaService mesaService;

    @GetMapping
    public String listar(@RequestParam(value = "estadoId", required = false) Integer estadoId,
                         Model model) {
        if (estadoId != null) {
            model.addAttribute("pedidos", pedidoService.buscarPorEstado(estadoId));
        } else {
            model.addAttribute("pedidos", pedidoService.listarTodos());
        }
        model.addAttribute("estados", pedidoService.listarEstados());
        model.addAttribute("estadoSeleccionado", estadoId);
        return "pedidos/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        Pedido pedido = new Pedido();
        pedido.setMesa(new Mesa());
        pedido.setUsuario(new Usuario());
        pedido.setEstado(new EstadoPedido());
        model.addAttribute("pedido", pedido);
        cargarListas(model);
        return "pedidos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Integer id, Model model,
                                   RedirectAttributes redirect) {
        Pedido pedido = pedidoService.buscarPorId(id);
        if (pedido == null) {
            redirect.addFlashAttribute("error", "El pedido no existe");
            return "redirect:/pedidos";
        }
        model.addAttribute("pedido", pedido);
        cargarListas(model);
        return "pedidos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("pedido") Pedido pedido,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirect) {

        if (pedido.getMesa() == null || pedido.getMesa().getIdMesa() == null) {
            result.rejectValue("mesa", "error.mesa", "Debe seleccionar una mesa");
        }
        if (pedido.getUsuario() == null || pedido.getUsuario().getIdUsuario() == null) {
            result.rejectValue("usuario", "error.usuario", "Debe seleccionar quién atiende el pedido");
        }
        if (pedido.getEstado() == null || pedido.getEstado().getIdEstadoPedido() == null) {
            result.rejectValue("estado", "error.estado", "Debe seleccionar el estado del pedido");
        }

        EstadoPedido estado = null;
        if (pedido.getEstado() != null && pedido.getEstado().getIdEstadoPedido() != null) {
            estado = pedidoService.buscarEstado(pedido.getEstado().getIdEstadoPedido());
        }

        // Regla del negocio: toda cancelación debe registrar su motivo
        if (estado != null && "Cancelado".equalsIgnoreCase(estado.getNombre())) {
            if (pedido.getMotivoCancelacion() == null || pedido.getMotivoCancelacion().isBlank()) {
                result.rejectValue("motivoCancelacion", "error.motivo",
                        "Debe indicar el motivo de la cancelación");
            }
        } else {
            pedido.setMotivoCancelacion(null);
        }

        // Regla del negocio: un pedido sin productos no se puede marcar como servido
        if (estado != null && "Servido".equalsIgnoreCase(estado.getNombre())
                && pedido.getIdPedido() != null) {
            if (pedidoService.contarDetalles(pedido.getIdPedido()) == 0) {
                result.rejectValue("estado", "error.estado",
                        "No se puede marcar como servido un pedido que no tiene productos");
            }
        }
        if (estado != null && "Servido".equalsIgnoreCase(estado.getNombre())
                && pedido.getIdPedido() == null) {
            result.rejectValue("estado", "error.estado",
                    "Un pedido nuevo no puede nacer servido. Primero agréguele los productos");
        }

        if (result.hasErrors()) {
            cargarListas(model);
            return "pedidos/formulario";
        }

        boolean esNuevo = (pedido.getIdPedido() == null);
        pedidoService.guardar(pedido);

        if (esNuevo) {
            redirect.addFlashAttribute("exito",
                    "Pedido creado. Ahora agréguele los productos");
            return "redirect:/pedidos/ver/" + pedido.getIdPedido();
        }

        redirect.addFlashAttribute("exito", "Pedido guardado correctamente");
        return "redirect:/pedidos";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Integer id, Model model, RedirectAttributes redirect) {
        Pedido pedido = pedidoService.buscarPorId(id);
        if (pedido == null) {
            redirect.addFlashAttribute("error", "El pedido no existe");
            return "redirect:/pedidos";
        }
        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setProducto(new ProductoMenu());
        model.addAttribute("detalle", detalle);
        cargarPedido(model, pedido);
        return "pedidos/detalle";
    }

    @PostMapping("/detalle/guardar")
    public String guardarDetalle(@Valid @ModelAttribute("detalle") DetallePedido detalle,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirect) {

        Integer idPedido = (detalle.getPedido() == null) ? null : detalle.getPedido().getIdPedido();
        if (idPedido == null) {
            redirect.addFlashAttribute("error", "No se pudo identificar el pedido");
            return "redirect:/pedidos";
        }

        Pedido pedido = pedidoService.buscarPorId(idPedido);
        if (pedido == null) {
            redirect.addFlashAttribute("error", "El pedido no existe");
            return "redirect:/pedidos";
        }
        detalle.setPedido(pedido);

        // Regla del negocio: un pedido servido o cancelado ya no se modifica
        String nombreEstado = (pedido.getEstado() == null) ? "" : pedido.getEstado().getNombre();
        if ("Servido".equalsIgnoreCase(nombreEstado) || "Cancelado".equalsIgnoreCase(nombreEstado)) {
            redirect.addFlashAttribute("error",
                    "No se pueden agregar productos a un pedido " + nombreEstado.toLowerCase());
            return "redirect:/pedidos/ver/" + idPedido;
        }

        ProductoMenu producto = null;
        if (detalle.getProducto() == null || detalle.getProducto().getIdProducto() == null) {
            result.rejectValue("producto", "error.producto", "Debe seleccionar un producto");
        } else {
            producto = pedidoService.buscarProducto(detalle.getProducto().getIdProducto());
            if (producto == null) {
                result.rejectValue("producto", "error.producto", "El producto no existe");
            } else if (Boolean.FALSE.equals(producto.getDisponible())) {
                result.rejectValue("producto", "error.producto",
                        "El producto " + producto.getNombre() + " no está disponible");
            }
        }

        if (result.hasErrors()) {
            cargarPedido(model, pedido);
            return "pedidos/detalle";
        }

        // El precio se toma del producto en este momento y el subtotal se calcula solo
        detalle.setProducto(producto);
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setSubtotal(producto.getPrecio()
                .multiply(BigDecimal.valueOf(detalle.getCantidad())));

        pedidoService.guardarDetalle(detalle);
        redirect.addFlashAttribute("exito", "Producto agregado al pedido");
        return "redirect:/pedidos/ver/" + idPedido;
    }

    @GetMapping("/detalle/eliminar/{idDetalle}")
    public String eliminarDetalle(@PathVariable Integer idDetalle, RedirectAttributes redirect) {
        DetallePedido detalle = pedidoService.buscarDetalle(idDetalle);
        if (detalle == null) {
            redirect.addFlashAttribute("error", "La línea del pedido no existe");
            return "redirect:/pedidos";
        }
        Integer idPedido = detalle.getPedido().getIdPedido();
        try {
            pedidoService.eliminarDetalle(idDetalle);
            redirect.addFlashAttribute("exito", "Producto quitado del pedido");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Ocurrió un error al quitar el producto");
        }
        return "redirect:/pedidos/ver/" + idPedido;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            pedidoService.eliminar(id);
            redirect.addFlashAttribute("exito", "Pedido eliminado correctamente");
        } catch (DataIntegrityViolationException e) {
            redirect.addFlashAttribute("error",
                    "No se puede eliminar este pedido porque tiene productos o una factura asociada");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Ocurrió un error al intentar eliminar el pedido");
        }
        return "redirect:/pedidos";
    }

    private void cargarListas(Model model) {
        model.addAttribute("mesas", mesaService.listarTodos());
        model.addAttribute("usuarios", pedidoService.listarUsuarios());
        model.addAttribute("estados", pedidoService.listarEstados());
    }

    private void cargarPedido(Model model, Pedido pedido) {
        model.addAttribute("pedido", pedido);
        model.addAttribute("detalles", pedidoService.listarDetalles(pedido.getIdPedido()));
        model.addAttribute("total", pedidoService.totalDePedido(pedido.getIdPedido()));
        model.addAttribute("productos", pedidoService.listarProductos());
    }
}