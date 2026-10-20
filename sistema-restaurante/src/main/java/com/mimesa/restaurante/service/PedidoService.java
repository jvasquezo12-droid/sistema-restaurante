package com.mimesa.restaurante.service;

import com.mimesa.restaurante.model.DetallePedido;
import com.mimesa.restaurante.model.EstadoPedido;
import com.mimesa.restaurante.model.Pedido;
import com.mimesa.restaurante.model.ProductoMenu;
import com.mimesa.restaurante.model.Usuario;
import com.mimesa.restaurante.repository.DetallePedidoRepository;
import com.mimesa.restaurante.repository.EstadoPedidoRepository;
import com.mimesa.restaurante.repository.PedidoRepository;
import com.mimesa.restaurante.repository.ProductoMenuRepository;
import com.mimesa.restaurante.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoMenuRepository productoMenuRepository;

    public List<Pedido> listarTodos() {
        return pedidoRepository.listarOrdenados();
    }

    public List<Pedido> buscarPorEstado(Integer idEstado) {
        return pedidoRepository.buscarPorEstado(idEstado);
    }

    public Pedido buscarPorId(Integer id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public List<EstadoPedido> listarEstados() {
        return estadoPedidoRepository.findAll();
    }

    public EstadoPedido buscarEstado(Integer id) {
        return estadoPedidoRepository.findById(id).orElse(null);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<ProductoMenu> listarProductos() {
        return productoMenuRepository.findAll();
    }

    public ProductoMenu buscarProducto(Integer id) {
        if (id == null) {
            return null;
        }
        return productoMenuRepository.findById(Long.valueOf(id)).orElse(null);
    }

    public void guardar(Pedido pedido) {
        if (pedido.getIdPedido() == null) {
            pedido.setFCreation(LocalDateTime.now());
        } else {
            Pedido actual = pedidoRepository.findById(pedido.getIdPedido()).orElse(null);
            if (actual != null) {
                pedido.setFCreation(actual.getFCreation());
            } else {
                pedido.setFCreation(LocalDateTime.now());
            }
            pedido.setFUpdate(LocalDateTime.now());
        }
        pedidoRepository.save(pedido);
    }

    public void eliminar(Integer id) {
        pedidoRepository.deleteById(id);
    }

    public List<DetallePedido> listarDetalles(Integer idPedido) {
        return detallePedidoRepository.buscarPorPedido(idPedido);
    }

    public BigDecimal totalDePedido(Integer idPedido) {
        BigDecimal total = detallePedidoRepository.totalDePedido(idPedido);
        return (total == null) ? BigDecimal.ZERO : total;
    }

    public long contarDetalles(Integer idPedido) {
        return detallePedidoRepository.contarPorPedido(idPedido);
    }

    public DetallePedido buscarDetalle(Integer idDetalle) {
        return detallePedidoRepository.findById(idDetalle).orElse(null);
    }

    public void guardarDetalle(DetallePedido detalle) {
        detallePedidoRepository.save(detalle);
    }

    public void eliminarDetalle(Integer idDetalle) {
        detallePedidoRepository.deleteById(idDetalle);
    }
}