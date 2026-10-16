package com.mimesa.restaurante.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Datos del formulario de cliente. Junta los campos de las dos tablas:
 * nombre y correo (usuario) y telefono y direccion (cliente).
 */
public class ClienteForm {

    private Integer idCliente;
    private Integer idUsuario;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no puede pasar de 150 caracteres")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    @Size(max = 150, message = "El correo no puede pasar de 150 caracteres")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    // El formato solo se revisa si hay algo escrito (el vacio ya lo cubre @NotBlank)
    @Pattern(regexp = "^(\\+?[0-9][0-9 \\-]{6,28})?$",
            message = "El teléfono solo puede llevar números, espacios o guiones (mínimo 7 dígitos)")
    private String telefono;

    @Size(max = 255, message = "La dirección no puede pasar de 255 caracteres")
    private String adress;

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getAdress() { return adress; }
    public void setAdress(String adress) { this.adress = adress; }
}
