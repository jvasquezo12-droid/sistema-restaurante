package com.mimesa.restaurante.controller;

import com.mimesa.restaurante.model.CategoriaMenu;
import com.mimesa.restaurante.model.ProductoMenu;
import com.mimesa.restaurante.service.CategoriaMenuService;
import com.mimesa.restaurante.service.ProductoMenuService;
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
    private ProductoMenuService productoService;

    @Autowired
    private CategoriaMenuService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        ProductoMenu producto = new ProductoMenu();
        producto.setCategoria(new CategoriaMenu());
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.listarTodos());
        return "productos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Integer id, Model model) {
        model.addAttribute("producto", productoService.buscarPorId(id));
        model.addAttribute("categorias", categoriaService.listarTodos());
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("producto") ProductoMenu producto,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirect) {
        if (result.hasErrors()) {
            // Se vuelve a cargar la lista de categorías para que el desplegable HTML no aparezca vacío
            model.addAttribute("categorias", categoriaService.listarTodos());
            return "productos/formulario";
        }
        productoService.guardar(producto);
        redirect.addFlashAttribute("exito", "Producto guardado correctamente");
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            productoService.eliminar(id);
            redirect.addFlashAttribute("exito", "Producto eliminado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error",
                    "No se puede eliminar este producto porque ya esta siendo usado en pedidos");
        }
        return "redirect:/productos";
    }
}