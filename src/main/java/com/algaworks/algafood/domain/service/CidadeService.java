package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.infraestructure.ValidateRequest;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repositorio;

	@Autowired
	private EstadoService estadoService;
	public List<Cidade> listar() {

		return repositorio.findAll();
		
	}

	public Cidade buscar(Long id) {
		Cidade cidade = repositorio.findById(id).orElseThrow(
					() -> new EntidadeNaoEncontradaException(
							String.format("Não existe cidade com id %d", id))
				);
		
//		if(cidade == null) {
//			throw new EntidadeNaoEncontradaException(
//					String.format("Não existe cidade com id %d", id));
//		}
		
		return cidade;
	}

	public Cidade salvar(Cidade cidade) {
		
		ValidateRequest.validarCidade(cidade);
		
		Estado estado = estadoService.buscar(cidade.getEstado().getId());
		
		cidade.setEstado(estado);

		cidade = repositorio.save(cidade);
		
		return cidade;
	}

	

	public Cidade atualizar(Cidade cidade) {

//		Long id = cidade.getId();
//		
//		if(id == null) {
//			throw new BadRequestException("Campo id da Cidade é obrigatório");
//		}
		
		return salvar(cidade);
	}

	public void remover(Long id) {
		try {
			
			repositorio.deleteById(id);
			
		} catch(EmptyResultDataAccessException e) {
			
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cidade com id %d", id));
		}
		
	}

}
