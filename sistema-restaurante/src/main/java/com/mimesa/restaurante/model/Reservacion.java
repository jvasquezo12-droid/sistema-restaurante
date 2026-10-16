package com.mimesa.restaurante.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "reservacion")
public class Reservacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReservacion;

    @NotNull(message = "Debe indicar la fecha")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate fecha;

    @NotNull(message = "Debe indicar la hora")
    @DateTimeFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime hora;

    @NotNull(message = "Debe indicar la cantidad de personas")
    @Min(value = 1, message = "La cantidad de personas debe ser mayor que cero")
    @Column(nullable = false)
    private Integer personas;

    @Column(name = "fCreation")
    private LocalDateTime fCreation = LocalDateTime.now();

    @Column(length = 255)
    private String motivoCancelacion;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idMesa", nullable = false)
    private Mesa mesa;

    @ManyToOne
    @JoinColumn(name = "idEstadoReservacion", nullable = false)
    private EstadoReservacion estado;

    public Integer getIdReservacion() { return idReservacion; }
    public void setIdReservacion(Integer idReservacion) { this.idReservacion = idReservacion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public Integer getPersonas() { return personas; }
    public void setPersonas(Integer personas) { this.personas = personas; }

    public LocalDateTime getFCreation() { return fCreation; }
    public void setFCreation(LocalDateTime fCreation) { this.fCreation = fCreation; }

    public String getMotivoCancelacion() { return motivoCancelacion; }
    public void setMotivoCancelacion(String motivoCancelacion) { this.motivoCancelacion = motivoCancelacion; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Mesa getMesa() { return mesa; }
    public void setMesa(Mesa mesa) { this.mesa = mesa; }

    public EstadoReservacion getEstado() { return estado; }
    public void setEstado(EstadoReservacion estado) { this.estado = estado; }

}
