package com.mimesa.restaurante.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "mesa")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMesa;

    @NotNull(message = "El número de mesa es obligatorio")
    @Positive(message = "El número de mesa debe ser mayor que cero")
    @Column(nullable = false, unique = true)
    private Integer numero;

    @NotNull(message = "La capacidad es obligatoria")
    @Positive(message = "La capacidad debe ser mayor que cero")
    @Column(nullable = false)
    private Integer capacidad;

    @Size(max = 100, message = "La ubicación no puede pasar de 100 caracteres")
    @Column(length = 100)
    private String loc;

    @ManyToOne
    @JoinColumn(name = "idEstadoMesa", nullable = false)
    private EstadoMesa estado;

    public Integer getIdMesa() { return idMesa; }
    public void setIdMesa(Integer idMesa) { this.idMesa = idMesa; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }

    public String getLoc() { return loc; }
    public void setLoc(String loc) { this.loc = loc; }

    public EstadoMesa getEstado() { return estado; }
    public void setEstado(EstadoMesa estado) { this.estado = estado; }
}
