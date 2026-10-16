package com.mimesa.restaurante.service;

import com.mimesa.restaurante.dto.ClienteForm;
import com.mimesa.restaurante.model.Cliente;
import com.mimesa.restaurante.model.Usuario;
import com.mimesa.restaurante.repository.ClienteRepository;
import com.mimesa.restaurante.repository.RolRepository;
import com.mimesa.restaurante.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    // idRol 4 = Cliente en la tabla rol
    private static final int ID_ROL_CLIENTE = 4;

    @Autowired
    private ClienteRepository clienteRepositorio;

    @Autowired
    private UsuarioRepository usuarioRepositorio;

    @Autowired
    private RolRepository rolRepositorio;

    public List<Cliente> listarTodos() {
        return clienteRepositorio.findAll();
    }

    public Cliente buscarPorId(Integer id) {
        return clienteRepositorio.findById(id).orElse(null);
    }

    // Arma el formulario con los datos de las dos tablas
    public ClienteForm obtenerFormulario(Integer id) {
        Cliente cliente = buscarPorId(id);
        if (cliente == null) {
            return null;
        }
        ClienteForm form = new ClienteForm();
        form.setIdCliente(cliente.getIdCliente());
        form.setIdUsuario(cliente.getUsuario().getIdUsuario());
        form.setNombre(cliente.getUsuario().getNombre());
        form.setEmail(cliente.getUsuario().getEmail());
        form.setTelefono(cliente.getTelefono());
        form.setAdress(cliente.getAdress());
        return form;
    }

    // Guarda en usuario y en cliente dentro de una sola transaccion:
    // si falla una de las dos, no se guarda ninguna
    @Transactional
    public Cliente guardar(ClienteForm form) {
        Cliente cliente;
        Usuario usuario;

        if (form.getIdCliente() == null) {
            usuario = new Usuario();
            usuario.setRol(rolRepositorio.findById(ID_ROL_CLIENTE).orElseThrow());
            // El cliente no inicia sesion: se guarda una clave aleatoria que nadie conoce
            usuario.setPassword("sin_acceso_" + UUID.randomUUID());
            cliente = new Cliente();
        } else {
            cliente = clienteRepositorio.findById(form.getIdCliente()).orElseThrow();
            usuario = cliente.getUsuario();
        }

        usuario.setNombre(form.getNombre().trim());
        usuario.setEmail(form.getEmail().trim());
        usuarioRepositorio.save(usuario);

        cliente.setUsuario(usuario);
        cliente.setTelefono(form.getTelefono().trim());
        cliente.setAdress(form.getAdress() == null || form.getAdress().isBlank()
                ? null : form.getAdress().trim());
        return clienteRepositorio.save(cliente);
    }

    // Borra el cliente y el usuario que se creo para el. Si el cliente tiene
    // reservaciones, MySQL lo rechaza y se deshace todo.
    @Transactional
    public void eliminar(Integer id) {
        Cliente cliente = clienteRepositorio.findById(id).orElseThrow();
        Usuario usuario = cliente.getUsuario();
        clienteRepositorio.delete(cliente);
        clienteRepositorio.flush();
        usuarioRepositorio.delete(usuario);
        usuarioRepositorio.flush();
    }

    // Devuelve true si otro usuario distinto ya usa ese correo
    public boolean correoEnUso(String email, Integer idUsuarioActual) {
        return usuarioRepositorio.findByEmail(email.trim())
                .map(u -> !u.getIdUsuario().equals(idUsuarioActual))
                .orElse(false);
    }
}
