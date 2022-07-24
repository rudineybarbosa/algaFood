package com.algaworks.algafood.api.controller;

import static com.algaworks.algafood.infraestructure.repository.specificationPattern.RestauranteSpecificationPatternBuilder.comFreteGratis;
import static com.algaworks.algafood.infraestructure.repository.specificationPattern.RestauranteSpecificationPatternBuilder.comNomeSimilar;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.infraestructure.repository.specificationPattern.RestauranteComFreteGratisSpecification;
import com.algaworks.algafood.infraestructure.repository.specificationPattern.RestauranteComNomeSimilarSpecification;

@RestController
@RequestMapping("/teste")
public class JPA_TesteConsultasVariadas {
	/*
	 * Teste das consultas com prefixos de Query Methods
	 */

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@GetMapping("/restaurantes/likeNome")
	public List<Restaurante> findRestauranteByNomeContaining(
			String nome){
		
		return restauranteRepository.findRestauranteByNomeContaining(nome);
		
	}
	
	@GetMapping("/restaurantes/taxaFreteBetween")
	public List<Restaurante> taxaFreteBetween(
			BigDecimal taxaInicial, BigDecimal taxaFinal){
		
		return restauranteRepository.queryByTaxaFreteBetween(taxaInicial, taxaFinal);
	
	}
	
	@GetMapping("/restaurantes/likeNomeFirst")
	public List<Restaurante> findFirstRestauranteByNomeContaining(
			String nome){
		
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
		
	}

	@GetMapping("/restaurantes/likeNomeFirst3")
	public List<Restaurante> findFirst3RestauranteByNomeContaining(
			String nome){
		
		return restauranteRepository.findFirst3RestauranteByNomeContaining(nome);
		
	}
	
	@GetMapping("/restaurantes/findAllOrderDescending")
	public List<Restaurante> findAllOrderDescending(
			String nome){

		Sort sort = Sort.by("nome").descending();
		
		return restauranteRepository.findAll(sort);
		
	}
	
	@GetMapping("/restaurantes/likeNomeAndCozinhaId")
	public List<Restaurante> findRestauranteByNomeContainingAndCozinhaId(
			String nome, Long cozinhaId){
		
		return restauranteRepository.findRestauranteByNomeContainingAndCozinhaId(nome, cozinhaId);
		
	}
	
	@GetMapping("/restaurantes/countByCozinhaId")
	public int countByCozinhaId(Long cozinhaId) {
		
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
	
	@GetMapping("/restaurantes/findByCozinhaId")
	public List<Restaurante> findByCozinhaId(Long cozinhaId) {
		
		return restauranteRepository.findByCozinhaId(cozinhaId);
	}
	
	@GetMapping("/restaurantes/findCustomByNomeTaxaFreteBetweenJPQLDinamico")
	public List<Restaurante> findCustomByNomeTaxaFreteBetweenJPQLDinamico(String nome,
			BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal){
		
		return restauranteRepository
				.findCustomByNomeTaxaFreteBetweenJPQLDinamico(nome, taxaFreteInicial, 
				taxaFreteFinal);
	}

	@GetMapping("/restaurantes/findAllRestauranteSimpleCriteria")
	public List<Restaurante> findAllRestauranteSimpleCriteria(){
		
		return restauranteRepository.findAllRestauranteSimpleCriteria();
	}
	
	
	@GetMapping("/restaurantes/findByNomeTaxaFreteBetweenCriteria")
	public List<Restaurante> findByNomeTaxaFreteBetweenCriteria(String nome,
			BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal){
		
		return restauranteRepository
				.findRestauranteByNomeTaxaFreteBetweenCriteria(nome, taxaFreteInicial, taxaFreteFinal);
	}
	

	/***********************************************************************************
	 * Domain Driven Developement (DDD) com Specification
	 * 
	 * Specification é uma forma de consulta na qual isolamos os 
	 * 	predicados (termos da consulta) em uma classe, de forma que tal classe represente
	 *  apenas um tipo de consulta. Algo como: 
	 *  
	 *  	public class RestauranteComTaxaFreteGratis implements Specification<Restaurante> { 
	 *  		
	 *  	}
	 *  
	 *  	E para utilizar, seria algo como:
	 *  		RestauranteComTaxaFreteGratis comFreteGratis = new RestauranteComTaxaFreteGratis();
	 *  
	 *  		return repositorio.findAll(comFreteGratis);
	 */
	@GetMapping("/restaurantes/findByNomeComTaxaFreteGratisSpecificationPattern")
	public List<Restaurante> findByNomeComTaxaFreteGratisSpecificationPattern(
			String nome){
		
		RestauranteComFreteGratisSpecification comFreteGratis = new RestauranteComFreteGratisSpecification();
		RestauranteComNomeSimilarSpecification comNomeSimilar = new RestauranteComNomeSimilarSpecification(nome);
		
		return restauranteRepository.findAll(comNomeSimilar.and(comFreteGratis));
	}
	
	

	/**
	 * Este método é extamente igual ao anterior, porém, utilizando Buider/Factor com
	 * 		Lambda Expression, Interface Funcional. 
	 * 	Com isso, não é preciso criar classes para
	 * 		representar os Predicate Equal e Like
	 * @return
	 */
	@GetMapping("/restaurantes/findByNomeComTaxaFreteGratisLambdaExpressionSpecificationPattern")
	public List<Restaurante> findByNomeComTaxaFreteGratisLambdaExpressionSpecificationPattern(
			String nome){
		
		return restauranteRepository.findAll(comNomeSimilar(nome)
				.and(comFreteGratis()));
		
	}
	
	
	/**
	 * Este método é exatemente igual ao anterior, porém, ele só utiliza 
	 * 		o padrão Specification dentro da própria implmentação do repositório. Assim,
	 * 		facilitamos o reuso e combinaão de Specitications em futuros novos métodos
	 * 
	 */
	@GetMapping("/restaurantes/findByNomeComTaxaFreteGratisLambdaExpressionSpecificationPattern2")
	public List<Restaurante> findByNomeComTaxaFreteGratisLambdaExpressionSpecificationPattern2(
			String nome){
		
		return restauranteRepository.findByNomeComFreteGratis(nome);
		
	}
	
	
	@GetMapping("/restaurantes/findPrimeiroUsandoCustomJpaRepository")
	public Optional<Restaurante> findPrimeiroUsandoCustomJpaRepository(){
		
		return restauranteRepository.buscarPrimeiro();
		
	}
	
	@GetMapping("/cozinhas/findPrimeiraUsandoCustomJpaRepository")
	public Optional<Cozinha> findPrimeiraUsandoCustomJpaRepository(){
		
		return cozinhaRepository.buscarPrimeiro();
		
	}
}
