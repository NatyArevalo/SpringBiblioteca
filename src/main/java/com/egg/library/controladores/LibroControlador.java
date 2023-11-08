package com.egg.library.controladores;

import com.egg.library.entidades.Autor;
import com.egg.library.entidades.Editorial;
import com.egg.library.entidades.Libro;
import com.egg.library.excepciones.MiException;
import com.egg.library.servicios.AutorServicio;
import com.egg.library.servicios.EditorialServicio;
import com.egg.library.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/libro")
public class LibroControlador {
    @Autowired
    LibroServicio libroServicio;
    @Autowired
    AutorServicio autorServicio;
    @Autowired
    EditorialServicio editorialServicio;

    @GetMapping("/registrar") //localhost:8080/libro/registrar
    public String registrar(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarautor();
        List<Editorial> editoriales = editorialServicio.listareditoriales();
        modelo.addAttribute("autores",autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo, @RequestParam(required = false) Integer ejemplares,
                           @RequestParam String idAutor, @RequestParam String idEditorial, ModelMap modelo) throws MiException {
        try {
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito","El libro fue cargado correctamente");

        } catch (Exception e) {
            List<Autor> autores = autorServicio.listarautor();
            List<Editorial> editoriales = editorialServicio.listareditoriales();
            modelo.addAttribute("autores",autores);
            modelo.addAttribute("editoriales", editoriales);
            modelo.put("error", e.getMessage());
            return "libro_form.html";
        }
        return "index.html";
    }
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Libro> libros = libroServicio.listarlibros();
        modelo.addAttribute("libros", libros);
        return "libro_list.html";
    }
}
