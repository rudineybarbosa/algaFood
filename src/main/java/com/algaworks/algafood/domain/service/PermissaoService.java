package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import com.algaworks.algafood.infraestructure.ValidateRequest;

@Service
public class PermissaoService {

	@Autowired
	private PermissaoRepository repositorio;
	
	public List<Permissao> listar() {
		
		return repositorio.findAll();
	}

	public Permissao findById(Long id) {

		Permissao permissao = repositorio.findById(id)
			.orElseThrow( 
					() -> new EntidadeNaoEncontradaException(
							String.format("Não existe permissao com id %d", id)));
	
		return permissao;
	}

	public Permissao save(Permissao permissao) {
		
		if(permissao == null) {
			throw new IllegalArgumentException("Entidade Permissao não pode ser nula");
		}
		
		ValidateRequest.validatePermissao(permissao);
		permissao = repositorio.save(permissao);
		return permissao;
		
	}

	public Permissao atualizar(Permissao permissao) {
		
		return save(permissao);
	}

	public void remove(Long id) {

		try {
			repositorio.deleteById(id);
			
		} catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Permissao não encontrada com id %d", id));
			
		}
	}
}
