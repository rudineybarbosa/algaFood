package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.BadRequestException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequestMapping("/restaurantes")
@RestController
public class RestauranteController {

	@Autowired
	private RestauranteService service;

	@GetMapping
	public List<Restaurante> listar() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		
		try {
			Restaurante restaurante = service.buscar(id);
			
			return ResponseEntity.ok().body(restaurante);
		} catch(EntidadeNaoEncontradaException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}

	}


	@PostMapping 
	public ResponseEntity<?> salvar(@RequestBody
			Restaurante restaurante){

		try {
			
			restaurante = service.salvar(restaurante);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(restaurante); 

		} catch(BadRequestException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@RequestBody Restaurante restaurante,
			@PathVariable Long id){
		try {

			Restaurante restauranteFromDb = service.buscar(id);
			
			BeanUtils.copyProperties(restaurante, restauranteFromDb, "id");
			
			restaurante = service.salvar(restauranteFromDb);

			return ResponseEntity.ok().body(restaurante); 

		} catch(EntidadeNaoEncontradaException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		
		} catch(BadRequestException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id){
		
		try {
			service.remover(id);
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			
		} catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long id,
			@RequestBody Map<String, Object> dadosOrigem){
		
		Restaurante restauranteDestino = service.buscar(id);
		
		if(restauranteDestino == null) {
			return ResponseEntity.notFound().build();
		}
		
		atualizarParcial(dadosOrigem, restauranteDestino);
		
		return atualizar(restauranteDestino, id);
		
	}
	
	private void atualizarParcial(Map<String, Object> dadosOrigem, 
			Restaurante restauranteDestino) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		Restaurante restauranteOrigem = 
				objectMapper.convertValue(dadosOrigem, Restaurante.class);
		
		dadosOrigem.forEach((campo, valor) -> {
			System.out.println(campo + ": " + valor);
			
			Field field = ReflectionUtils.findField(Restaurante.class, campo);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
			
			}
		);
	}
}
