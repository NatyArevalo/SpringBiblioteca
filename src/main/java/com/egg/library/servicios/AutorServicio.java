package com.egg.library.servicios;

import com.egg.library.entidades.Autor;
import com.egg.library.excepciones.MiException;
import com.egg.library.repositorio.AutorRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutorServicio {
    @Autowired
    AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MiException{
        if(nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);

    }
    public List<Autor> listarautor(){
        List<Autor> autores = new ArrayList();
        autores = autorRepositorio.findAll();
        return autores;
    }
    @Transactional
    public void modificarAutor(String nombre, String id) throws MiException {
        validar(nombre, id);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }
    }
    public Autor getOne(String id){
        return autorRepositorio.getOne(id);
    }
    public void validar(String nombre, String id) throws MiException{
        if(id == null || id.isEmpty()){
            throw new MiException("El ID no puede ser nulo o estar vacio");
        }
        if(nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
    }
}
