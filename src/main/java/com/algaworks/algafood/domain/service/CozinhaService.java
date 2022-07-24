package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.infraestructure.ValidateRequest;

@Service
public class CozinhaService {

	@Autowired
	private CozinhaRepository repositorioCozinha;
//	private IRepository<Cozinha, Long> repositorioCozinha;
	
	public List<Cozinha> listar(){
		return repositorioCozinha.findAll();
	}
	
	public Cozinha salvar(Cozinha cozinha) {
		
		ValidateRequest.validarCozinha(cozinha);
		
		return repositorioCozinha.save(cozinha);
	}

	public Cozinha buscar(Long id) {
		
		Cozinha cozinha = repositorioCozinha.findById(id)
				.orElseThrow(
						() -> new EntidadeNaoEncontradaException(
								String.format("Não existe cozinha com o id %d", id))
				);
		
//		if(cozinha == null) {
//			
//			throw new EntidadeNaoEncontradaException(
//					String.format("Não existe cozinha com o id %d", id));
//		}
		
		return cozinha;
	}
	
	
	public void excluir(Long id) {
		try {
			
			repositorioCozinha.deleteById(id);
			
		} catch(DataIntegrityViolationException e) {
		
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d não pode ser removida", id));
		
		} catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cozinha com o id %d", id));
		}
	}
	
	
}
