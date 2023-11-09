package com.egg.library.servicios;

import com.egg.library.entidades.Editorial;
import com.egg.library.excepciones.MiException;
import com.egg.library.repositorio.EditorialRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EditorialServicio {
    @Autowired
    EditorialRepositorio editorialRepositorio;

    @Transactional //Todos los metodos que generen cambios en la base de datos deben ser listados como transactional
    //En caso de arrojar una excepción, se ejecutara un rollback y no habran cambios en la BD
    public void crearEditorial(String nombre) throws MiException {
        if(nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }
    public List<Editorial> listareditoriales(){
        List<Editorial> editoriales = new ArrayList();
        editoriales = editorialRepositorio.findAll();
        return editoriales;
    }
    @Transactional
    public void modificareditorial(String nombre, String id) throws MiException {
        validar(nombre, id);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);

            editorialRepositorio.save(editorial);
        }
    }
    public Editorial getOne(String id){
        return editorialRepositorio.getOne(id);
    }
    public void validar(String nombre, String id) throws MiException {
        if(id == null || id.isEmpty()){
            throw new MiException("El ID no puede ser nulo o estar vacio");
        }
        if(nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
    }
}
