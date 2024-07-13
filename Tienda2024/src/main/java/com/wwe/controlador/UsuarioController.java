package com.wwe.controlador;

import java.util.List;
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

import com.wwe.modelo.Orden;
import com.wwe.modelo.Usuario;
import com.wwe.servicio.IOrdenServicio;
import com.wwe.servicio.IUsuarioServicio;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioServicio usuarioServicio;
	
	@Autowired
	private IOrdenServicio ordenServicio;
	
	// /usuario/registro
	@GetMapping("/registro")
	public String create() {
		
		return "usuario/registro";
	}
	
	@PostMapping("/save")
	public String save(Usuario usuario) {
		logger.info("Usuario registro:  {}", usuario);
		
		usuario.setTipo("USER");
		usuarioServicio.save(usuario);
		
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "usuario/login";
	}
	
	
	@PostMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session) {
		
		logger.info("Accesos: {}", usuario);
		
		Optional<Usuario> user=usuarioServicio.findbyEmail(usuario.getEmail());
		//logger.info("Usuario de db:  {}", user.get());
		
		if (user.isPresent()) {
			
			session.setAttribute("idusuario", user.get().getId());
			if (user.get().getTipo().equals("ADMIN")) {
				return "redirect:/administrador";
			}else {
				return "redirect:/";
			}
		}else {
			logger.info("Usuario no existe");
		}
		
		
		return "redirect:/";
	}
	
	@GetMapping("/compras")
	public String obtenerCompras(Model modelo,HttpSession session) {
		
		modelo.addAttribute("session", session.getAttribute("idusuario"));
		
		Usuario usuario = usuarioServicio.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		List<Orden> ordenes = ordenServicio.findByUsuario(usuario);
		
		modelo.addAttribute("ordenes", ordenes);
		
		return "usuario/compras";
	}
	
	
	@GetMapping("/detalle/{id}")
	public String detalleComprar(@PathVariable Integer id, HttpSession session, Model modelo) {
		
		logger.info("Id de la orden: {}", id);
		
		Optional<Orden> orden= ordenServicio.findById(id);
		
		
		modelo.addAttribute("detalles", orden.get().getDetalle());
		
		
		modelo.addAttribute("session", modelo.getAttribute("idusuario"));
		
		return "usuario/detallecompra";
	}
	
	
	@GetMapping("/cerrar")
	public String cerrarSesion( HttpSession session ) {
		session.removeAttribute("idusuario");
		return "redirect:/";
	}
	
}
