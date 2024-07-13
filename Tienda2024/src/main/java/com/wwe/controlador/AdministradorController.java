package com.wwe.controlador;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wwe.modelo.Orden;
import com.wwe.modelo.Producto;
import com.wwe.servicio.IOrdenServicio;
import com.wwe.servicio.IUsuarioServicio;
import com.wwe.servicio.ProductoServicio;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private ProductoServicio productoServicio;
	
	@Autowired
	private IUsuarioServicio usuarioServicio;
	
	@Autowired
	private IOrdenServicio ordenServicio;
	
	
	private Logger logg=LoggerFactory.getLogger(AdministradorController.class);
	
	
	@GetMapping("")
	public String home(Model modelo) {
		List<Producto> productos = productoServicio.findALl();
		modelo.addAttribute("productos", productos);
		return "administrador/home";
	}
	
	
	@GetMapping("/usuarios")
	public String usuarios(Model modelo) {
		
		modelo.addAttribute("usuarios", usuarioServicio.findAll());
		return "administrador/usuarios";
	}
	
	
	@GetMapping("/ordenes")
	public String ordenes(Model modelo) {
		
		modelo.addAttribute("ordenes", ordenServicio.findAll());
		
		return "administrador/ordenes";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalle(Model modelo, @PathVariable Integer id) {
		
		logg.info("id de la orden: {}", id);
		Orden orden= ordenServicio.findById(id).get();
		modelo.addAttribute("detalles", orden.getDetalle());
		return "administrador/detalleorden";
	}
	
}
