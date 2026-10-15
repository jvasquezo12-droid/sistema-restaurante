package com.mimesa.restaurante.repository.proyeccion;

public interface VentaPorFechaProyeccion {
    String getFecha();
    Integer getCantidadPedidos();
    Double getTotalMonto();
}