package com.mimesa.restaurante.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estado_pedido")
public class EstadoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEstadoPedido")
    private Integer idEstadoPedido;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    public Integer getIdEstadoPedido() {
        return idEstadoPedido;
    }

    public void setIdEstadoPedido(Integer idEstadoPedido) {
        this.idEstadoPedido = idEstadoPedido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
