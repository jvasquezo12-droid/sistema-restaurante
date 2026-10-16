package com.mimesa.restaurante.controller;

import com.mimesa.restaurante.model.ProductoMenu;
import com.mimesa.restaurante.repository.CategoriaMenuRepository;
import com.mimesa.restaurante.repository.ProductoMenuRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/productos")
public class ProductoMenuController {

    @Autowired
    private ProductoMenuRepository productoRepository;

    @Autowired
    private CategoriaMenuRepository categoriaRepository;

    // Listar productos con filtros
    @GetMapping
    public String listarProductos(@RequestParam(value = "categoriaId", required = false) Long categoriaId,
                                  @RequestParam(value = "nombre", required = false) String nombre,
                                  Model model) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            model.addAttribute("productos", productoRepository.findByNombreContainingIgnoreCase(nombre));
        } else if (categoriaId != null) {
            model.addAttribute("productos", productoRepository.findByCategoria_IdCategoria(categoriaId));
        } else {
            model.addAttribute("productos", productoRepository.findAll());
        }
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "productos/lista";
    }

    // Formulario para nuevo producto (Resuelve el 404)
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("producto", new ProductoMenu());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "productos/formulario";
    }

    // Guardar o actualizar producto con validaciones
    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute("producto") ProductoMenu producto,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes redirect) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "productos/formulario";
        }
        productoRepository.save(producto);
        redirect.addFlashAttribute("exito", "Producto guardado correctamente.");
        return "redirect:/productos";
    }

    // Formulario para editar producto
    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        ProductoMenu producto = productoRepository.findById(id).orElse(null);
        if (producto == null) {
            redirect.addFlashAttribute("error", "El producto no existe.");
            return "redirect:/productos";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "productos/formulario";
    }

    // Eliminar producto
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long id, RedirectAttributes redirect) {
        productoRepository.deleteById(id);
        redirect.addFlashAttribute("exito", "Producto eliminado correctamente.");
        return "redirect:/productos";
    }
}