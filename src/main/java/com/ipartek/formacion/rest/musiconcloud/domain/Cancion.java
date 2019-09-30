package com.ipartek.formacion.rest.musiconcloud.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="cancion")
@ApiModel(value="Cancion", description="ejemplo para documentación")
public class Cancion {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "ID generado por la base de datos")
	int id;
	
	@NotBlank
	@Basic
	@Column(name="nombre", nullable=false, unique=true)
	@ApiModelProperty(notes = "Nombre Cancion", required = true)
	String nombre;

	public Cancion() {
		super();
		this.id = 0;
		this.nombre = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Cancion [id=" + id + ", nombre=" + nombre + "]";
	}

}
