package com.ipartek.formacion.rest.musiconcloud.controller;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.formacion.rest.musiconcloud.domain.Cancion;
import com.ipartek.formacion.rest.musiconcloud.domain.ReponseMensaje;
import com.ipartek.formacion.rest.musiconcloud.model.CancionesRepository;
import com.ipartek.formacion.rest.musiconcloud.model.CategoriaRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "canciones", description = "Crud basico para Canciones")
public class CancionesController {

	@Autowired
	CancionesRepository cancionesRepository;

	@Autowired
	CategoriaRepository categoriaRepository; // TODO mirar hoy no, mañana

	@RequestMapping(value = { "/cancion/", "/cancion" }, method = RequestMethod.GET)
	@ApiOperation(value = "Listado de Canciones, soporta busqueda por nombre de cancion")
	public ResponseEntity<Object> listar(@RequestParam(required = false) String nombre) {

		ResponseEntity<Object> result = null;
		ArrayList<Cancion> lista = null;
		try {

			if (nombre != null && !"".equals(nombre)) {
				lista = (ArrayList<Cancion>) cancionesRepository.findByNombreContaining(nombre);
			} else {
				lista = (ArrayList<Cancion>) cancionesRepository.findAll();
			}

			if (lista.isEmpty()) {
				result = new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			} else {
				result = new ResponseEntity<Object>(lista, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;
	}

	@RequestMapping(value = "/cancion/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> detalle(@PathVariable int id) {
		ResponseEntity<Object> result = null;
		try {
			Optional<Cancion> cancion = cancionesRepository.findById(id);
			if (!cancion.isPresent()) {
				result = new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			} else {
				result = new ResponseEntity<Object>(cancion, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;

	}

	@RequestMapping(value = "/cancion/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> eliminar(@PathVariable int id) {

		ResponseEntity<Object> result = null;
		try {

			cancionesRepository.deleteById(id);
			result = new ResponseEntity<Object>(new ReponseMensaje("registro eliminado"), HttpStatus.OK);

		} catch (EmptyResultDataAccessException e) {
			result = new ResponseEntity<Object>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;

	}

	@RequestMapping(value = "/cancion/", method = RequestMethod.POST)
	public ResponseEntity<Object> insertar(@Valid @RequestBody Cancion cancion) {

		ResponseEntity<Object> result = null;
		try {
			cancionesRepository.save(cancion);
			result = new ResponseEntity<Object>(cancion, HttpStatus.CREATED);

		} catch (DataIntegrityViolationException e) {

			result = new ResponseEntity<Object>(new ReponseMensaje("Existe el nombre de la Cancion"),
					HttpStatus.CONFLICT);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;

	}

	@RequestMapping(value = "/cancion/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@Valid @RequestBody Cancion cancion, @PathVariable int id) {

		ResponseEntity<Object> result = null;
		try {
			cancion.setId(id);
			cancionesRepository.save(cancion);
			result = new ResponseEntity<Object>(cancion, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;

	}

}
