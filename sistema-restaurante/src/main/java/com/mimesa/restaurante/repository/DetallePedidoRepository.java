package com.mimesa.restaurante.repository;

import com.mimesa.restaurante.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

    @Query("SELECT d FROM DetallePedido d WHERE d.pedido.idPedido = :idPedido ORDER BY d.idDetalle")
    List<DetallePedido> buscarPorPedido(@Param("idPedido") Integer idPedido);

    @Query("SELECT COALESCE(SUM(d.subtotal), 0) FROM DetallePedido d WHERE d.pedido.idPedido = :idPedido")
    BigDecimal totalDePedido(@Param("idPedido") Integer idPedido);

    @Query("SELECT COUNT(d) FROM DetallePedido d WHERE d.pedido.idPedido = :idPedido")
    long contarPorPedido(@Param("idPedido") Integer idPedido);
}