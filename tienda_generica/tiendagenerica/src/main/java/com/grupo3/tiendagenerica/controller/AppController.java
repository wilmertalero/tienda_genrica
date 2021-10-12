package com.grupo3.tiendagenerica.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.grupo3.tiendagenerica.entity.User;
import com.grupo3.tiendagenerica.repository.UserRepository;
import com.grupo3.tiendagenerica.repository.AuthorityRepository;
import com.grupo3.tiendagenerica.service.UserService;

@Controller
public class AppController {
	
	@Autowired
	AuthorityRepository AuthorityRepository;
	
	@Autowired 
	UserService userService;

	@GetMapping({"/","/login"})
	public String index() {
		return "login";
	}
	
	@GetMapping("/menu")
	public String menu() {
		return "menu";
	}
	
	@GetMapping("/usuarios")
	public String getUserForm(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("authority",AuthorityRepository.findAll());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("listTab","active");
		return "/usuarios";
	}
	
	@PostMapping("/userForm")
	public String postUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
			if(result.hasErrors()) {
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
			}else {
				try {
					userService.createUser(user);
					model.addAttribute("userForm", new User());
					model.addAttribute("listTab","active");
				} catch (Exception e) {
					model.addAttribute("formErrorMessage", e.getMessage());
					model.addAttribute("userForm", user);
					model.addAttribute("formTab","active");
					model.addAttribute("authority",AuthorityRepository.findAll());
					model.addAttribute("userList", userService.getAllUsers());
				}
			}
			model.addAttribute("authority",AuthorityRepository.findAll());
			model.addAttribute("userList", userService.getAllUsers());

		return "/usuarios";
	}
	
	@GetMapping("/editUser/{cedula_usuario}")
	public String getEditUserForm(Model model, @PathVariable(name="cedula_usuario") Long cedula_usuario) throws Exception {
		User userToEdit = userService.getUserByCedula(cedula_usuario);
		
		model.addAttribute("userForm", userToEdit);
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("authority",AuthorityRepository.findAll());
		model.addAttribute("formTab","active");//Activa el tab del formulario.
		
		model.addAttribute("editMode",true);//Mira siguiente seccion para mas informacion
		
		return "/usuarios";
	}
	
	@PostMapping("/editUser")
	public String postEditUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
		}else {
			try {
				userService.updateUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("authority",AuthorityRepository.findAll());
				model.addAttribute("editMode","true");
			}
		}
		
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("authority",AuthorityRepository.findAll());
		return "/usuarios";
		
	}
	
	@GetMapping("/userForm/cancel")
	public String cancelEditUser(ModelMap model) {
		return "redirect:/usuarios";
	}
	
	@GetMapping("/deleteUser/{cedula_usuario}")
	public String deteUser(Model model, @PathVariable(name="cedula_usuario")Long cedula_usuario) {
		try {
			userService.deleteUser(cedula_usuario);
		}catch(Exception e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		return getUserForm(model);
	}
	
	@GetMapping("/clientes")
	public String clientes() {
		return "clientes";
	}
	
	@GetMapping("/proveedores")
	public String proveedores() {
		return "Proveedores";
	}
	
	@GetMapping("/productos")
	public String productos() {
		return "Productos";
	}
	
	@GetMapping("/ventas")
	public String ventas() {
		return "ventas";
	}
	
	@GetMapping("/reportes")
	public String reportes() {
		return "reportes";
	}
}