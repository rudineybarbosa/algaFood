package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.algaworks.algafood.domain.model.Restaurante;

/*
 * Learning prefixos de Query Methods
 * 	- JPA reconhece os prefixos e cria a consulta em tempo de execução
 * 
 * 	- JPQL
 * 
 * 	- Criteria
 */

public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>,
	JpaSpecificationExecutor<Restaurante>
		{

/**********************************************************************************
 * query methods: consultas geradas dinamicamente à partir do nome do método
 */
	
	
	List<Restaurante> queryByTaxaFreteBetween(
			BigDecimal taxaInicial, BigDecimal taxaFinal);

	List<Restaurante> findRestauranteByNomeContainingAndCozinhaId(
			String nome, Long cozinhaId);

	List<Restaurante> findRestauranteByNomeContaining(String nome);

	List<Restaurante> findFirstRestauranteByNomeContaining(String nome);

	List<Restaurante> findFirst3RestauranteByNomeContaining(String nome);

	List<Restaurante> findAll(Sort sort);

	int countByCozinhaId(Long cozinhaId);

	List<Restaurante> findByCozinhaId(Long cozinhaId);
//*********************************************************************************
	

/***********************************************************************************
 * JPQL
 */
	public List<Restaurante> findCustomByNomeTaxaFreteBetweenJPQLDinamico(String nome,
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
//*********************************************************************************
	

/***********************************************************************************
 * Criteria
 */
	public List<Restaurante> findAllRestauranteSimpleCriteria();
	
	public List<Restaurante> findRestauranteByNomeTaxaFreteBetweenCriteria(String nome,
			BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal);
//*********************************************************************************		


	public List<Restaurante> findByNomeComFreteGratis(String nome);
	
	
	
}
