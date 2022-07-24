package com.algaworks.algafood.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeService service;
	
	@GetMapping
	public ResponseEntity<?> listar(){
		List<Cidade> list = service.listar();
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id) {
		
		try {

			Cidade cidade = service.buscar(id);

			return ResponseEntity.ok(cidade);
			
		} catch(EntidadeNaoEncontradaException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Cidade cidade){
		
		try {
			cidade = service.salvar(cidade);
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
					.path("/{id}")
					.buildAndExpand(cidade.getId()).toUri();
			
			//return ResponseEntity.ok(cidade);
			return ResponseEntity.created(uri).body(cidade);
			
		} catch (BadRequestException e) {
		
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(
			@RequestBody Cidade cidade,
			@PathVariable Long id){
		
		try {
			
			Cidade cidadeFromDB = service.buscar(id);
			
			BeanUtils.copyProperties(cidade, cidadeFromDB, "id");
			
			cidade = service.atualizar(cidadeFromDB);
			
			return ResponseEntity.ok(cidadeFromDB);
			
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
		
		}
	}
	
}
