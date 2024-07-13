package com.wwe.servicio;

import java.util.List;
import java.util.Optional;

import com.wwe.modelo.Usuario;

public interface IUsuarioServicio {
	
	List<Usuario> findAll();
	Optional<Usuario> findById(Integer id);
	Usuario save(Usuario usuario);
	Optional<Usuario>findbyEmail(String email);
	
}
