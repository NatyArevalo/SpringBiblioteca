package com.egg.library.servicios;

import com.egg.library.entidades.Editorial;
import com.egg.library.entidades.Libro;
import com.egg.library.excepciones.MiException;
import com.egg.library.repositorio.AutorRepositorio;
import com.egg.library.repositorio.EditorialRepositorio;
import com.egg.library.repositorio.LibroRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.egg.library.entidades.Autor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LibroServicio {
    @Autowired //Inyeccion de dependencias: Indicarle al servidor que esta variable es inicializada por el
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());

        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libroRepositorio.save(libro);
    }
    public List<Libro> listarlibros(){
        List<Libro> libros = new ArrayList();
        libros = libroRepositorio.findAll();
        return libros;
    }
    public void modificarlibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if(respuestaAutor.isPresent()){
            autor = respuestaAutor.get();
        }
        if(respuestaEditorial.isPresent()){
            editorial = respuestaEditorial.get();
        }

        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);

            libroRepositorio.save(libro);

        }
    }
    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
        if(isbn == null){
            throw new MiException("El ISBN no puede ser nulo");
        }
        if(titulo == null || titulo.isEmpty()){
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        if(ejemplares == null){
            throw new MiException("Los ejemplares no pueden ser nulos");
        }
        if(idAutor.isEmpty() || idAutor == null){
            throw new MiException("El ID del autor no puede ser nulo o estar vacio");
        }
        if(idEditorial.isEmpty() || idEditorial == null) {
            throw new MiException("El ID de la editorial no puede ser nulo o estar vacio");
        }
    }
}
