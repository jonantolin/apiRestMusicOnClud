package com.ipartek.formacion.rest.musiconcloud.controller;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.formacion.rest.musiconcloud.domain.Categoria;
import com.ipartek.formacion.rest.musiconcloud.domain.ReponseMensaje;
import com.ipartek.formacion.rest.musiconcloud.model.CategoriaRepository;

@RestController
public class CategoriasController {

	@Autowired // nuestro antoguo getInstance()
	CategoriaRepository categoriaRepository; // nuestro antiguo dao

	@RequestMapping(value = { "/categoria/", "/categoria" }, method = RequestMethod.GET)
	public ResponseEntity<Object> listar() {

		ResponseEntity<Object> response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);

		try {

			ArrayList<Categoria> lista = (ArrayList<Categoria>) categoriaRepository.findAll();

			if (!lista.isEmpty()) {
				response = new ResponseEntity<Object>(lista, HttpStatus.OK);
			} else {

				response = new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@RequestMapping(value = { "/categoria/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<Object> detalle(@PathVariable int id) {

		ResponseEntity<Object> response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);

		try {

			Optional<Categoria> categoria = categoriaRepository.findById(id);

			if (categoria.isPresent()) {
				response = new ResponseEntity<Object>(categoria, HttpStatus.OK);
			} else {
				// Prueba chorra
				// response = new ResponseEntity<Object>("<h1>No hay contenido</h1>",
				// HttpStatus.OK);
				response = new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return response;

	}

	@RequestMapping(value = { "/categoria/" }, method = RequestMethod.POST)
	public ResponseEntity<Object> crear(@RequestBody Categoria cat) {

		ResponseEntity<Object> response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);

		try {

			categoriaRepository.save(cat);
			response = new ResponseEntity<Object>(cat, HttpStatus.CREATED);

		} catch (DataIntegrityViolationException e) {

			ReponseMensaje mensaje = new ReponseMensaje("Ya existe el nombre");
			response = new ResponseEntity<Object>(mensaje, HttpStatus.CONFLICT);

		} catch (ConstraintViolationException e) {

			ReponseMensaje mensaje = new ReponseMensaje("El nombre debe estar entre 3 y 150 caracteres");
			response = new ResponseEntity<Object>(mensaje, HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;

	}

	@RequestMapping(value = { "/categoria/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<Object> eliminar(@PathVariable int id) {

		ResponseEntity<Object> response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);

		try {

			categoriaRepository.deleteById(id);
			response = new ResponseEntity<Object>(new ReponseMensaje("Eliminado"), HttpStatus.OK);

		} catch (EmptyResultDataAccessException e) {
			response = new ResponseEntity<Object>(HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping(value = { "/categoria/" }, method = RequestMethod.PUT)
	public ResponseEntity<Object> actualizar(@RequestBody Categoria cat, @PathVariable int id) {

		ResponseEntity<Object> response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);

		try {

			cat.setId(id);
			categoriaRepository.save(cat);

			response = new ResponseEntity<Object>(cat, HttpStatus.CREATED);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return response;
	}

}
