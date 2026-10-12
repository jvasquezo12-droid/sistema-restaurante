package com.mimesa.restaurante.controller;

import com.mimesa.restaurante.model.CategoriaMenu;
import com.mimesa.restaurante.service.CategoriaMenuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CategoriaMenuController {

    @Autowired
    private CategoriaMenuService categoriaService;

    @GetMapping("/categorias")
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodos());
        return "categorias/lista";
    }

    @GetMapping("/categorias/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("categoria", new CategoriaMenu());
        return "categorias/formulario";
    }

    @PostMapping("/categorias/guardar")
    public String guardar(@Valid @ModelAttribute("categoria") CategoriaMenu categoria,
                          BindingResult result,
                          RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "categorias/formulario";
        }
        categoriaService.guardar(categoria);
        redirect.addFlashAttribute("exito", "Categoría guardada correctamente");
        return "redirect:/categorias";
    }

    @GetMapping("/categorias/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        CategoriaMenu categoria = categoriaService.buscarPorId(id);
        if (categoria == null) {
            return "redirect:/categorias";
        }
        model.addAttribute("categoria", categoria);
        return "categorias/formulario";
    }

    @GetMapping("/categorias/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            categoriaService.eliminar(id);
            redirect.addFlashAttribute("exito", "Categoría eliminada correctamente");
        } catch (DataIntegrityViolationException e) {
            redirect.addFlashAttribute("error",
                    "No se puede eliminar: la categoría tiene productos asociados.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Ocurrió un error al intentar eliminar la categoría.");
        }
        return "redirect:/categorias";
    }
}