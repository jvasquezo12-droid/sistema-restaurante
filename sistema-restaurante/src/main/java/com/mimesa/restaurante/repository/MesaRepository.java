package com.mimesa.restaurante.repository;

import com.mimesa.restaurante.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MesaRepository extends JpaRepository<Mesa, Integer> {

    Optional<Mesa> findByNumero(Integer numero);
}
