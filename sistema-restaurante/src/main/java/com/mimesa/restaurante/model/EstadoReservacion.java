package com.mimesa.restaurante.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estado_reservacion")
public class EstadoReservacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstadoReservacion;

    @Column(nullable = false, length = 50)
    private String nombre;

    public Integer getIdEstadoReservacion() { return idEstadoReservacion; }
    public void setIdEstadoReservacion(Integer idEstadoReservacion) { this.idEstadoReservacion = idEstadoReservacion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}