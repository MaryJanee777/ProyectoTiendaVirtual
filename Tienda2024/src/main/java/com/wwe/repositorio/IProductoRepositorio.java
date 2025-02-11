package com.wwe.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wwe.modelo.Producto;

@Repository
public interface IProductoRepositorio extends JpaRepository<Producto, Integer> {

}
