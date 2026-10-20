package com.mimesa.restaurante.repository;

import com.mimesa.restaurante.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT p FROM Pedido p ORDER BY p.fCreation DESC")
    List<Pedido> listarOrdenados();

    @Query("SELECT p FROM Pedido p WHERE p.estado.idEstadoPedido = :idEstado ORDER BY p.fCreation DESC")
    List<Pedido> buscarPorEstado(@Param("idEstado") Integer idEstado);
}