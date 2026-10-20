package com.mimesa.restaurante.repository;

import com.mimesa.restaurante.model.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Integer> {
}