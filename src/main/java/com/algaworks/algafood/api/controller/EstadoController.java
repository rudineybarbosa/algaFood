package com.algaworks.algafood.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafood.domain.exception.BadRequestException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Estado> listarJson(){
		System.out.println("LISTAR JSON");
		return service.listar();
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public List<Estado> listarXml(){
		System.out.println("LISTAR XML");
		return service.listar();
	}
	
	@GetMapping("/{estadoId}")
	public ResponseEntity<?> buscar(@PathVariable Long estadoId) {
		
		try {

			Estado estado = service.buscar(estadoId);

			return ResponseEntity.ok(estado);
			
		} catch(EntidadeNaoEncontradaException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Estado estado){
		
		try {
			estado = service.salvar(estado);
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
					.path("/{id}")
					.buildAndExpand(estado.getId()).toUri();
			
			return ResponseEntity.created(uri).body(estado);
			
		} catch (BadRequestException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(
			@RequestBody Estado estado,
			@PathVariable Long id){
		
		try {
			
			Estado estadoFromDB = service.buscar(id);
			
			BeanUtils.copyProperties(estado, estadoFromDB, "id");
			
			estado = service.atualizar(estadoFromDB);
			
			return ResponseEntity.ok(estadoFromDB);
			
		} catch(BadRequestException e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		
		} catch(EntidadeNaoEncontradaException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
			
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id){
		try {
			
			service.remover(id);

			return ResponseEntity.noContent().build();
			
		}catch(EntidadeNaoEncontradaException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		
		}catch(EntidadeEmUsoException e) {

			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(e.getMessage());

		}
		
	}
	
}
