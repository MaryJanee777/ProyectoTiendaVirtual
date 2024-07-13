package com.wwe.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wwe.modelo.Usuario;
import com.wwe.repositorio.IUsuarioRepositorio;


@Service
public class UsuarioServiceImplet implements IUsuarioServicio{
	
	@Autowired
	private IUsuarioRepositorio usuarioRepositorio;
	
	@Override
	public Optional<Usuario> findById(Integer id) {
		return usuarioRepositorio.findById(id);
	}

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioRepositorio.save(usuario);
	}

	@Override
	public Optional<Usuario> findbyEmail(String email) {
		return usuarioRepositorio.findByEmail(email);
	}

	@Override
	public List<Usuario> findAll() {
	
		return usuarioRepositorio.findAll();
	}
	
	
	
	
	
}
