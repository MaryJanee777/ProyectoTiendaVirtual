package com.wwe.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wwe.modelo.DetalleOrden;

@Repository
public interface IDetalleOrdenRepositorio extends JpaRepository<DetalleOrden, Integer>{
	
	
}
