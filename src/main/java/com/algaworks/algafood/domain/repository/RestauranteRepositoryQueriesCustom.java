package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueriesCustom {
	/**
	 * Consulta Dinâmica com JQPL
	 * 
	 * JPQL é bom para consultas mais simples
	 */
	List<Restaurante> findCustomByNomeTaxaFreteBetweenJPQLDinamico(String nome, 
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

	
	/**
	 * Consulta com Criteria simples
	 * 
	 * Criteria é bom para consultas complexas
	 */
	List<Restaurante> findAllRestauranteSimpleCriteria();

	
	/**
	 * Consulta com Criteria não tão simples
	 * 
	 * Criteria é bom para consultas complexas
	 */
	List<Restaurante> findRestauranteByNomeTaxaFreteBetweenCriteria(String nome, BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal);

}