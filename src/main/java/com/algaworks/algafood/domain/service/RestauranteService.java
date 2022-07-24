package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.BadRequestException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.infraestructure.ValidateRequest;


@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository repositorio;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	public List<Restaurante> listar(){
		return repositorio.findAll();
	}
	
	public Restaurante buscar(Long id) {
		Restaurante restaurante = repositorio.findById(id).orElseThrow(
					
					() -> new EntidadeNaoEncontradaException(
						String.format("Não existe restaurante com o id %d", id))
				
				);

//		if(restaurante == null) {
//		
//			throw new EntidadeNaoEncontradaException(
//					String.format("Não existe restaurante com o id %d", id));
//		}
		
		return restaurante;
	}

	public Restaurante salvar(Restaurante restaurante) {
		ValidateRequest.validateRestaurante(restaurante);
		
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha;
		try {
			
			cozinha = cozinhaService.buscar(cozinhaId);
			
		} catch (Exception e) {
			
			throw new BadRequestException(
					String.format("Não existe cozinha com código %d", cozinhaId));
		}
		
		restaurante.setCozinha(cozinha);
		
		return repositorio.save(restaurante);
	}
	
	public void remover(Long id) {
		
		try {
			
			repositorio.deleteById(id);
			
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Remoção não permitida."
							+ "O restaurante %d está sendo referênciado por uma cozinha", id));
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe restaurante com o id %d", id));
		}
	}
	
	
}
