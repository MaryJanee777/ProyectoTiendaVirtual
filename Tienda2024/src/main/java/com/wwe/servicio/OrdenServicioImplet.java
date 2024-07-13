package com.wwe.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wwe.modelo.Orden;
import com.wwe.modelo.Usuario;
import com.wwe.repositorio.IOrdenRepositorio;

@Service
public class OrdenServicioImplet implements IOrdenServicio {
	
	
	
	@Autowired
	private IOrdenRepositorio ordenRepositorio;
	
	
	@Override
	public Orden save(Orden orden) {
		return ordenRepositorio.save(orden);
	}

	@Override
	public List<Orden> findAll() {
		return ordenRepositorio.findAll();
	}

	public String generarNumeroOrden() {
		int numero = 0;
		String numeroConcatenado="";
		
		List<Orden>ordenes = findAll();
		
		List<Integer> numeros = new ArrayList<Integer>();
		
		
		ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNum())));
		
		if (ordenes.isEmpty()) {
			numero=1;
		}else{
			numero= numeros.stream().max(Integer::compare).get();
			numero++;
		}
		
		if (numero<10) {
			numeroConcatenado="000000000"+String.valueOf(numero);
		}else if(numero<100) {
			numeroConcatenado="00000000"+String.valueOf(numero);
		}else if(numero<1000) {
			numeroConcatenado="0000000"+String.valueOf(numero);
		}else if(numero<10000) {
			numeroConcatenado="000000"+String.valueOf(numero);
		}
			
		
		return numeroConcatenado;
	}

	@Override
	public List<Orden> findByUsuario(Usuario usuario) {
		
		return ordenRepositorio.findByUsuario(usuario);
	}

	@Override
	public Optional<Orden> findById(Integer id) {
		return ordenRepositorio.findById(id);
	}
	
	
	
	
	
	
}
