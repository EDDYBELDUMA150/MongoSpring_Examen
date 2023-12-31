package com.proyecto5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto5.model.Usuarios;
import com.proyecto5.service.UsuariosService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class UsuariosController {

	@Autowired
	private UsuariosService usuaServ;

	@GetMapping("/usuarios/list")
	public ResponseEntity<List<Usuarios>> list() {
		try {
			return new ResponseEntity<>(usuaServ.findByAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	
	@GetMapping("/usuarios/search/{id}")
	public ResponseEntity<Usuarios> search(@PathVariable("id") Integer id) {
		try {
			return new ResponseEntity<>(usuaServ.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	

	@PostMapping("/usuarios/create")
	public ResponseEntity<Usuarios> create(@RequestBody Usuarios actividad) {
		try {
			return new ResponseEntity<>(usuaServ.save(actividad), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	

	@DeleteMapping("/usuarios/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
		try {
			usuaServ.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al elminar el Registro");
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/usuarios/update/{id}")
	public ResponseEntity<Usuarios> update(@RequestBody Usuarios actividadRb, @PathVariable("id") Integer id) {
		Usuarios act = usuaServ.findById(id);

		if (act == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			try {
				act.setUsu_nombre(actividadRb.getUsu_nombre());
				act.setUsu_contra(actividadRb.getUsu_contra());
				act.setUsu_correo(actividadRb.getUsu_correo());
				act.setUsu_nivelacademico(actividadRb.getUsu_nivelacademico());
				act.setUsu_fecha_inic(actividadRb.getUsu_fecha_inic());
				act.setUsu_fecha_nacimiento(actividadRb.getUsu_fecha_nacimiento());
                                act.setRoles(actividadRb.getRoles());
				return new ResponseEntity<>(usuaServ.save(actividadRb), HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
}
