package com.wwe.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wwe.modelo.DetalleOrden;
import com.wwe.repositorio.IDetalleOrdenRepositorio;

@Service
public class DetalleOrdenServicioImpl implements IDetalleOrdenServicio {
	
	
	@Autowired
	private IDetalleOrdenRepositorio detalleOrdenRepository;

	@Override
	public DetalleOrden save(DetalleOrden detalleOrden) {
		return detalleOrdenRepository.save(detalleOrden);
	}

	
}
