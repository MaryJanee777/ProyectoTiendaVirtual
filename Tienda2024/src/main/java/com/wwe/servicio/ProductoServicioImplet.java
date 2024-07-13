package com.wwe.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wwe.modelo.Producto;
import com.wwe.repositorio.IProductoRepositorio;

@Service
public class ProductoServicioImplet implements ProductoServicio {

	@Autowired
	private IProductoRepositorio productoRepositorio;
	
	
	@Override
	public Producto save(Producto producto) {
		// TODO Auto-generated method stub
		return productoRepositorio.save(producto);
	}

	@Override
	public Optional<Producto> get(Integer id) {
		// TODO Auto-generated method stub
		return productoRepositorio.findById(id);
	}

	@Override
	public void update(Producto producto) {
		// TODO Auto-generated method stub
		productoRepositorio.save(producto);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		productoRepositorio.deleteById(id);
	}

	@Override
	public List<Producto> findALl() {
		// TODO Auto-generated method stub
		return productoRepositorio.findAll();
	}

}