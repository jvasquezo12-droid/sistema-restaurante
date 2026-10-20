package com.mimesa.restaurante.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
public class Pedido {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPedido")
    private Integer idPedido;

    @Column(name = "fCreation", nullable = false)
    private LocalDateTime fCreation;

    @Column(name = "fUpdate")
    private LocalDateTime fUpdate;

    @Size(max = 500, message = "La nota no puede tener más de 500 caracteres")
    @Column(name = "nota", length = 500)
    private String nota;

    @Size(max = 255, message = "El motivo no puede tener más de 255 caracteres")
    @Column(name = "motivoCancelacion", length = 255)
    private String motivoCancelacion;

    @ManyToOne
    @JoinColumn(name = "idMesa", nullable = false)
    private Mesa mesa;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idEstadoPedido", nullable = false)
    private EstadoPedido estado;

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDateTime getFCreation() {
        return fCreation;
    }

    public void setFCreation(LocalDateTime fCreation) {
        this.fCreation = fCreation;
    }

    public LocalDateTime getFUpdate() {
        return fUpdate;
    }

    public void setFUpdate(LocalDateTime fUpdate) {
        this.fUpdate = fUpdate;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }
}
