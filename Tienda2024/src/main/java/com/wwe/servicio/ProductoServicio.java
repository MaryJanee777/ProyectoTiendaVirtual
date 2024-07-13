package com.wwe.servicio;

import java.util.List;
import java.util.Optional;

import com.wwe.modelo.Producto;

public interface ProductoServicio {
	public Producto save( Producto producto);
	public Optional<Producto> get(Integer id);
	public void update(Producto producto);
	public void delete(Integer id);
	public List<Producto> findALl();
}
