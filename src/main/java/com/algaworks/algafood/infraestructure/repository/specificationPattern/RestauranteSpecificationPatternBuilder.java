package com.algaworks.algafood.infraestructure.repository.specificationPattern;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.model.Restaurante;

public class RestauranteSpecificationPatternBuilder {

	public static Specification<Restaurante> comFreteGratis(){

		//Lambda Expression e Interface Funcional (Specification, método toPredicate)
		return (root, query, criteriaBuilder) ->
			criteriaBuilder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}
	
	public static Specification<Restaurante> comNomeSimilar(String nome){
	
		//Lambda Expression e Interface Funcional (Specification, método toPredicate)
		return (root, query, criteriaBuilder) ->
			criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
	}
	
}
