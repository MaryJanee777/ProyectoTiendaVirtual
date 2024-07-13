package com.wwe.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wwe.modelo.Usuario;
import java.util.List;



@Repository
public interface IUsuarioRepositorio extends JpaRepository<Usuario, Integer>{
	
	Optional<Usuario>findByEmail(String email);
	
}
