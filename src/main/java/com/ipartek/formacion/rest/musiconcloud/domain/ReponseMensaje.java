package com.ipartek.formacion.rest.musiconcloud.domain;

public class ReponseMensaje {

	private String info;

	public ReponseMensaje() {
		super();
		this.info = "";
	}
	
	public ReponseMensaje( String info ) {
		this();
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "ReponseMensaje [info=" + info + "]";
	}
	
	
	
	
	
}
