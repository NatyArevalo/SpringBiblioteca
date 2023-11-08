package com.egg.library.controladores;

import com.egg.library.entidades.Autor;
import com.egg.library.excepciones.MiException;
import com.egg.library.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/autor")
public class AutorControlador {
    @Autowired
    private AutorServicio autorServicio;
    @GetMapping("/registrar") //localhost:8080/autor/registrar
    public String registrar(){
        return "autor_form.html";
    }
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) throws MiException {
        try{
            autorServicio.crearAutor(nombre);
            modelo.put("exito","El autor fue cargado correctamente");
        }catch (Exception e){
            modelo.put("error", e.getMessage());
            return "autor_form.html";
        }
        return "index.html";

    }
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Autor> autores = autorServicio.listarautor();
        modelo.addAttribute("autores", autores);
        return "autor_list.html";
    }
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("autor", autorServicio.getOne(id));
        return "autor_modificar.html";
    }
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,String nombre, ModelMap modelo){
        try{
            autorServicio.modificarAutor(nombre, id);
            return "redirect:../lista";
        } catch (MiException e){
            modelo.put("error", e.getMessage());
            return "autor_modificar.html";
        }

    }

}

