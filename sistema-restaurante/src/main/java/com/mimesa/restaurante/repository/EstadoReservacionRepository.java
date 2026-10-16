package com.mimesa.restaurante.repository;

import com.mimesa.restaurante.model.EstadoReservacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoReservacionRepository extends JpaRepository<EstadoReservacion, Integer> {
}
