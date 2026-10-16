package com.mimesa.restaurante.repository.proyeccion;

public interface ProductoMasVendidoProyeccion {
    String getNombreProducto();
    Long getCantidadTotal();
    Double getTotalVendido();
}