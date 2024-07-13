package com.wwe.controlador;

import java.io.IOException;
import java.util.Optional;

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
import org.springframework.web.multipart.MultipartFile;

import com.wwe.modelo.Producto;
import com.wwe.modelo.Usuario;
import com.wwe.servicio.IUsuarioServicio;
import com.wwe.servicio.ProductoServicio;
import com.wwe.servicio.UploadFileServicio;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	
	
	@Autowired
	private ProductoServicio productoServicio;
	
	@Autowired
	private IUsuarioServicio usuarioServicio;
	
	@Autowired
	private UploadFileServicio upload;
	
	@GetMapping("")
	public String show(Model modelo) {
		modelo.addAttribute("productos", productoServicio.findALl());
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	
	
	@PostMapping("/save")
	public String save(Producto producto,@RequestParam("img") MultipartFile file, HttpSession session) throws IOException{
		
		
		
		Usuario u= usuarioServicio.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		producto.setUsuario(u);
		
		//img
		if (producto.getId() == null) {
			String nombreImg=upload.saveImg(file);
			producto.setImagen(nombreImg);
		}else {
			
		}
		
		productoServicio.save(producto);
		return "redirect:/productos";
	}
	
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model modelo) {
		Producto producto = new Producto();
		Optional<Producto> optionalProducto=productoServicio.get(id);
		producto = optionalProducto.get();
		
		modelo.addAttribute("producto", producto);
		
		 
		return "productos/edit";
	}
	
	@PostMapping("/update")
	public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		Producto p = new Producto();
		p=productoServicio.get(producto.getId()).get();
		
		
		if (file.isEmpty()) {
			
			producto.setImagen(p.getImagen());
		}else {
			
			if (!p.getImagen().equals("default.jpg")) {
				upload.deleteImg(p.getImagen());
			}
			String nombreImg = upload.saveImg(file);
			producto.setImagen(nombreImg);
		}
		
		producto.setUsuario(p.getUsuario());
		productoServicio.update(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		
		Producto p = new Producto();
		p=productoServicio.get(id).get();
		if (!p.getImagen().equals("default.jpg")) {
			upload.deleteImg(p.getImagen());
		}
		
		
		productoServicio.delete(id);
		return "redirect:/productos";
	}
	
	
}