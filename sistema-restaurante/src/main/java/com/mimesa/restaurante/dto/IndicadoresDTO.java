package com.mimesa.restaurante.dto;

public class IndicadoresDTO {

    private long totalProductos;
    private long productosDisponibles;
    private long totalCategorias;
    private long totalMesas;
    private long mesasDisponibles;
    private long totalUsuarios;

    public long getTotalProductos() { return totalProductos; }
    public void setTotalProductos(long totalProductos) { this.totalProductos = totalProductos; }

    public long getProductosDisponibles() { return productosDisponibles; }
    public void setProductosDisponibles(long productosDisponibles) { this.productosDisponibles = productosDisponibles; }

    public long getTotalCategorias() { return totalCategorias; }
    public void setTotalCategorias(long totalCategorias) { this.totalCategorias = totalCategorias; }

    public long getTotalMesas() { return totalMesas; }
    public void setTotalMesas(long totalMesas) { this.totalMesas = totalMesas; }

    public long getMesasDisponibles() { return mesasDisponibles; }
    public void setMesasDisponibles(long mesasDisponibles) { this.mesasDisponibles = mesasDisponibles; }

    public long getTotalUsuarios() { return totalUsuarios; }
    public void setTotalUsuarios(long totalUsuarios) { this.totalUsuarios = totalUsuarios; }
}
