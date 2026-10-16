package com.mimesa.restaurante.repository.proyeccion;

import java.time.LocalDate;

public interface ReservacionPeriodoProyeccion {
    Integer getIdReservacion();
    String getNombreCliente();
    LocalDate getFecha();
    String getEstado();
}