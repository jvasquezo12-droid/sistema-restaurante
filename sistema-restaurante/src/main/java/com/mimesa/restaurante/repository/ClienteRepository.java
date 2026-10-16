package com.mimesa.restaurante.repository;

import com.mimesa.restaurante.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
