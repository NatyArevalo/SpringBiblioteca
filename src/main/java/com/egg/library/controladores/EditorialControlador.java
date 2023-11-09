package com.egg.library.controladores;

import com.egg.library.entidades.Editorial;
import com.egg.library.excepciones.MiException;
import com.egg.library.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    @Autowired
    EditorialServicio editorialServicio;
    @GetMapping("/registrar") //localhost:8080/editorial/registrar
    public String registrar(){
        return "editorial_form.html";
    }
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) throws MiException {
        try{
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "La editorial fue cargada con exito");
        }catch (Exception e){
            modelo.put("error", e.getMessage());
            return "editorial_form.html";
        }
        return "index.html";
    }
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Editorial> editorales = editorialServicio.listareditoriales();
        modelo.addAttribute("editoriales", editorales);
        return "editorial_list.html";
    }
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("editorial", editorialServicio.getOne(id));
        return "editorial_modificar.html";
    }
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        try{
            editorialServicio.modificareditorial(nombre, id);
            return "redirect:../lista";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "editorial_modificar.html";
        }
    }
}
