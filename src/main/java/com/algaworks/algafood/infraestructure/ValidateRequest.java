package com.algaworks.algafood.infraestructure;

import java.math.BigDecimal;

import com.algaworks.algafood.domain.exception.BadRequestException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.Restaurante;

public class ValidateRequest {
	
	public static void validarCozinha(Cozinha cozinha) {
		String nome = cozinha.getNome();
		if(nome == null || nome.isEmpty()) {
			throw new BadRequestException(
					"Campo nome da Cozinha é obrigatório");
		}
	}

	public static void validarCidade(Cidade cidade) {
		String nome = cidade.getNome();
		if(nome == null || nome.isEmpty()) {
			throw new BadRequestException(
					"Campo nome da Cidade é obrigatório");
		}
		
		Estado estado = cidade.getEstado();
		if(estado == null) {
			throw new BadRequestException(
					"Campo Estado é obrigatório");
		}
		
		Long id = estado.getId();
		if(id == null) {
			throw new BadRequestException(
					"Campo id do Estado é obrigatório");
		}
	}

	public static void validateRestaurante(Restaurante restaurante) {
		Cozinha cozinha = restaurante.getCozinha();

		if(cozinha == null) {
			
			throw new BadRequestException(
					String.format("Informe uma cozinha para o restaurante"));
		}
		
		Long cozinhaId = cozinha.getId();
		if(cozinhaId == null) {
			
			throw new BadRequestException(
					String.format("Informe o id da cozinha"));
		}
		
		String nome = restaurante.getNome();
		if(nome == null) {
			
			throw new BadRequestException(
					String.format("Informe o nome do restaurante"));
		}

		BigDecimal taxaFrete = restaurante.getTaxaFrete();
		if(taxaFrete == null) {
			
			throw new BadRequestException(
					String.format("Informe a taxaFrete"));
		}
		
	}

	public static void validatePermissao(Permissao permissao) {
		if(permissao.getDescricao() == null
				|| permissao.getDescricao() != null 
					&& permissao.getDescricao().isEmpty()) {
			
			throw new BadRequestException("Informa a descrição");
		}
		
		if(permissao.getNome() == null
				|| permissao.getNome() != null 
				&& permissao.getNome().isEmpty()) {
			
			throw new BadRequestException("Informa o nome");
		}
		
	}
}
