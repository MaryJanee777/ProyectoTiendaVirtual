package com.wwe.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wwe.modelo.DetalleOrden;
import com.wwe.modelo.Orden;
import com.wwe.modelo.Producto;
import com.wwe.modelo.Usuario;
import com.wwe.servicio.ProductoServicio;
import com.wwe.servicio.IDetalleOrdenServicio;
import com.wwe.servicio.IOrdenServicio;
import com.wwe.servicio.IUsuarioServicio;

@Controller
@RequestMapping("/")
public class HomeController {
	
	
	
	private final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	
	@Autowired
	private ProductoServicio productoServicio;
	
	@Autowired
	private IUsuarioServicio usuarioServicio;
	
	@Autowired
	private IOrdenServicio ordenServicio;
	
	@Autowired
	private IDetalleOrdenServicio detalleOrdenServicio;
	
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
	
	Orden orden = new Orden();
	
	@GetMapping("")
	public String home(Model modelo, HttpSession session) {
		
		log.info("Sesion del Usuario: {}", session.getAttribute("idusuario"));
		
		modelo.addAttribute("productos", productoServicio.findALl());
		
		//session	
		modelo.addAttribute("sesion", session.getAttribute("idusuario"));
		
		
		return "usuario/home";
	}
	
	
	@GetMapping("productohome/{id}")
	public String productoHome(@PathVariable Integer id, Model modelo) {
		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoServicio.get(id);
		producto = productoOptional.get();
		
		modelo.addAttribute("producto", producto);
		return "usuario/productohome";
	}
	
	
	@PostMapping("/cart")
	public String addcart(@RequestParam Integer id, @RequestParam int cantidad, Model modelo) {
		
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = new Producto();
		double sumaTotal = 0;
		
		Optional<Producto> optionalProducto = productoServicio.get(id);
		
		producto = optionalProducto.get();
		
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(producto.getPrecio());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setTotal(producto.getPrecio()*cantidad);
		detalleOrden.setProducto(producto);
		
		
		//validar que el producto no se añada 2 veces
		
		Integer idProducto = producto.getId();
		boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId()==idProducto);
		
		
		if (!ingresado) {
			
			detalles.add(detalleOrden);
		}
		
		
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		
		orden.setTotal(sumaTotal);
		modelo.addAttribute("cart", detalles);
		modelo.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}
	
	@GetMapping("/delete/cart/{id}")
	public String deleteProductoCart(@PathVariable Integer id, Model modelo) {
		
		List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();
		
		for(DetalleOrden detalleorden: detalles) {
			if (detalleorden.getProducto().getId()!=id) {
				ordenesNueva.add(detalleorden);
			}
		}
		
		detalles = ordenesNueva;
		double sumaTotal = 0;
		
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		
		orden.setTotal(sumaTotal);
		modelo.addAttribute("cart", detalles);
		modelo.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}
	
	
	@GetMapping("/getCart")
	public String getCart(Model modelo, HttpSession session) {
		
		
		
		modelo.addAttribute("cart", detalles);
		modelo.addAttribute("orden", orden);
		
		//session
		modelo.addAttribute("sesion", session.getAttribute("idusuario"));
		
		return "/usuario/carrito";
	}
	
	
	@GetMapping("/order")
	public String order(Model modelo, HttpSession session) {
		
		
		Usuario usuario = usuarioServicio.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get(); 
		
		modelo.addAttribute("cart", detalles);
		modelo.addAttribute("orden", orden);
		modelo.addAttribute("usuario", usuario);
		
		
		return "usuario/resumenorden";
	}
	
	
	@GetMapping("/saveOrder")
	public String saveOrder(HttpSession session) {
		
		Date fechaCreacion = new Date();
		orden.setFechaCreacion(fechaCreacion);
		orden.setNum(ordenServicio.generarNumeroOrden());
		
		//usuario
		Usuario usuario = usuarioServicio.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get(); 
		orden.setUsuario(usuario);
		ordenServicio.save(orden);
		
		for(DetalleOrden dt:detalles) {
			dt.setOrden(orden);
			detalleOrdenServicio.save(dt);
		}
		
		
		//limpiar
		orden = new Orden();
		detalles.clear();
		
		return "redirect:/";
	}
	
	@PostMapping("/search")
	public String searchProduct(@RequestParam String nombre, Model modelo) {
		
		log.info("Nombre del producto: ()", nombre);
		List<Producto> productos = productoServicio.findALl().stream().filter(p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
		
		
		modelo.addAttribute("productos", productos);
		
		return "usuario/home";
	}
	
	
	
	
	
	
	
}