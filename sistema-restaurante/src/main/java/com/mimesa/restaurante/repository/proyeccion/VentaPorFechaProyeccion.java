package com.mimesa.restaurante.repository.proyeccion;

import java.time.LocalDate;

public interface VentaPorFechaProyeccion {
    LocalDate getFecha();
    Integer getCantidadPedidos();
    Double getTotalVentas();
}