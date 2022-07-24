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

import com.algaworks.algafood.api.controller.model.CozinhasXmlWrapper;
import com.algaworks.algafood.domain.exception.BadRequestException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaService service;
	
	@GetMapping
	private List<Cozinha> listar(){
		return service.listar();
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml() {
		return new CozinhasXmlWrapper(service.listar());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id) {
		try {
			Cozinha cozinha = service.buscar(id);
			
			return ResponseEntity.ok().body(cozinha);

		} catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Cozinha cozinha){
		try {
			cozinha = service.salvar(cozinha);
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
					.path("/{id}")
					.buildAndExpand(cozinha.getId()).toUri();
			
			return ResponseEntity.created(uri).body(cozinha);
			
		} catch (BadRequestException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, 
			@RequestBody Cozinha cozinha) {
		
		Cozinha cozinhaFromDB = service.buscar(id);
		if(cozinhaFromDB != null) {
			
			BeanUtils.copyProperties(cozinha, cozinhaFromDB, "id");
			
			cozinhaFromDB = service.salvar(cozinhaFromDB);
			
			return ResponseEntity.ok().body(cozinhaFromDB);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> remove(@PathVariable Long id){

		try {
			service.excluir(id);

			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());

		} catch(EntidadeEmUsoException e) {

			return ResponseEntity.status(HttpStatus.CONFLICT).body("Não é "
					+ "possível deletar esta cozinha devido a referência com Restaurante");
		}
	}
}
