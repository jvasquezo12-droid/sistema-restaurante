package com.mimesa.restaurante.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @Column(length = 30)
    private String telefono;

    // La columna se llama "adress" en la base de datos
    @Column(length = 255)
    private String adress;

    @Column(nullable = false)
    private Integer loyalty = 0;

    // El nombre y el correo viven en la tabla usuario
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getAdress() { return adress; }
    public void setAdress(String adress) { this.adress = adress; }

    public Integer getLoyalty() { return loyalty; }
    public void setLoyalty(Integer loyalty) { this.loyalty = loyalty; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
