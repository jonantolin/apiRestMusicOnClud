package com.ipartek.formacion.rest.musiconcloud.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ipartek.formacion.rest.musiconcloud.domain.Cancion;

public interface CancionesRepository extends CrudRepository<Cancion, Integer>{
	
	List<Cancion> findByNombreContaining( String nombre);

}
