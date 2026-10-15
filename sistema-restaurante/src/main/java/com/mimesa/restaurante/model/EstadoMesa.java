package com.mimesa.restaurante.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estado_mesa")
public class EstadoMesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstadoMesa;

    @Column(nullable = false, length = 50)
    private String nombre;

    public Integer getIdEstadoMesa() { return idEstadoMesa; }
    public void setIdEstadoMesa(Integer idEstadoMesa) { this.idEstadoMesa = idEstadoMesa; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
