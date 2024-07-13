package com.wwe.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wwe.modelo.Orden;
import com.wwe.modelo.Usuario;


@Repository
public interface IOrdenRepositorio extends JpaRepository<Orden, Integer>{

	List<Orden>findByUsuario(Usuario usuario);
	
}
