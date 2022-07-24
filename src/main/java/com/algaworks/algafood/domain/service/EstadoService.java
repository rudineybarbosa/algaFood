package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.BadRequestException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repository;

	public List<Estado> listar() {
		return this.repository.findAll();
	}

	public Estado buscar(Long id) {

		Estado estado = repository.findById(id).orElseThrow(
				
					() -> new EntidadeNaoEncontradaException(
							String.format("Não existe estado com id %d", id))
					
				);
		
//		if(estado == null) {
//			throw new EntidadeNaoEncontradaException(
//					String.format("Não existe estado com id %d", id));
//		}
		
		return estado;
	}

	public Estado salvar(Estado estado) {
		String nome = estado.getNome();
		
		if(nome == null || nome.isEmpty()) {
			throw new BadRequestException("É obrigatório fornecer nome do estado");
		}
		
		return repository.save(estado);
		
	}
	
	public Estado atualizar(Estado estado) {
		Long id = estado.getId();
		
		if(id == null || id.intValue() == 0) {
			throw new BadRequestException("Campo id deve ser fornecido");
		}
		
		return salvar(estado);
		
	}

	public void remover(Long id) {
		try {
			repository.deleteById(id);
			
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Remoção não permitida."
							+ "O estado %d está sendo referênciado por uma cidade", id));
			
		} catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe estado com id %d", id));
		}
	}

	
	
}
