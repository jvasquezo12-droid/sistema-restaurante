package com.mimesa.restaurante.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name= "usuario")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column (nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "fCreation")
    private LocalDateTime fCreation = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDateTime getFCreation() { return fCreation; }
    public void setFCreation(LocalDateTime fCreation) { this.fCreation = fCreation; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

}
