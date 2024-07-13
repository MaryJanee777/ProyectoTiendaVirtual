package com.wwe.servicio;

import java.util.List;
import java.util.Optional;

import com.wwe.modelo.Orden;
import com.wwe.modelo.Usuario;

public interface IOrdenServicio {
	
	
	List<Orden> findAll();
	Optional<Orden> findById(Integer id);
	Orden save (Orden orden);
	String generarNumeroOrden();
	List<Orden> findByUsuario(Usuario usuario);
	
}
