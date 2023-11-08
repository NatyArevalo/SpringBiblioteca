package com.egg.library.controladores;

import com.egg.library.excepciones.MiException;
import com.egg.library.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String registro(@RequestParam String nombre) throws MiException {
        try{
            editorialServicio.crearEditorial(nombre);
            System.out.println("Nombre: " + nombre);
        }catch (Exception e){
            Logger.getLogger(EditorialControlador.class.getName()).log(Level.SEVERE,null, e);
            return "editorial_form.html";
        }
        return "index.html";

    }
}
