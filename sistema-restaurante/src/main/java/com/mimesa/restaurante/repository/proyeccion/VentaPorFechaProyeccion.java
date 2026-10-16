package com.mimesa.restaurante.repository.proyeccion;

import java.time.LocalDate;

public interface VentaPorFechaProyeccion {
    LocalDate getFecha();
    Long getCantidadPedidos();
    Double getTotalVentas();
}