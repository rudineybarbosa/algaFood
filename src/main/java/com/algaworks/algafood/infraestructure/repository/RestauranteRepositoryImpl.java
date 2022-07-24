package com.algaworks.algafood.infraestructure.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueriesCustom;
import static com.algaworks.algafood.infraestructure.repository.specificationPattern.RestauranteSpecificationPatternBuilder.*;

/**
 * Esta classe Impl deve possuir como prefixo o nome da 
 * 	interface repositorio a ser implementada.
 * O nome da interface é RestauranteRepository, então, o 
 * 	nome da classe deve ser RestauranteRepositoryImpl
 *
 */
@Repository
public class RestauranteRepositoryImpl 
	implements RestauranteRepositoryQueriesCustom
	{

	@Autowired
	private EntityManager manager;
	
	//5.19. Injetando o próprio repositório na implementação customizada e a anotação @Lazy
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;
	
	/**
	 * Consulta Dinâmica com JQPL
	 * 
	 * JPQL é bom para consultas mais simples
	 */
	@Override
	public List<Restaurante> findCustomByNomeTaxaFreteBetweenJPQLDinamico(String nome,
			BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal){
		
		var jpqlDynamic = new StringBuilder();
		
		jpqlDynamic.append("from Restaurante where 0 = 0 ");
		
		var parameters = new HashMap<String, Object>();
		
		if(StringUtils.hasLength(nome)) {
			jpqlDynamic.append(" and nome like :nome ");
			parameters.put("nome", "%" + nome + "%");
		}
		
		if(taxaFreteInicial != null) {
			jpqlDynamic.append(" and taxaFrete >= :taxaInicial ");
			parameters.put("taxaInicial", taxaFreteInicial);
			
		}

		if(taxaFreteFinal!= null) {
			jpqlDynamic.append(" and taxaFrete <= :taxaFinal ");
			parameters.put("taxaFinal", taxaFreteFinal);
			
		}
		
		TypedQuery<Restaurante> query = manager.createQuery(jpqlDynamic.toString(), Restaurante.class);
		parameters.forEach((chave,valor) -> query.setParameter(chave, valor));
		
		return query.getResultList();
		
	}
	
	/**
	 * Consulta com Criteria simples
	 * 
	 * Criteria é bom para consultas complexas
	 */
	@Override
	public List<Restaurante> findAllRestauranteSimpleCriteria(){
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Restaurante> criteriaQuery = builder.createQuery(Restaurante.class);
		
		criteriaQuery.from(Restaurante.class);
		
		
		TypedQuery<Restaurante> typedQuery = manager.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
		
		
	}
	
	/**
	 * Consulta com Criteria não tão simples
	 * 
	 * Criteria é bom para consultas complexas
	 */
	@Override
	public List<Restaurante> findRestauranteByNomeTaxaFreteBetweenCriteria(String nome,
			BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal){
		
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Restaurante> criteriaQuery = criteriaBuilder.createQuery(Restaurante.class);
		
		Root<Restaurante> rootEntity = criteriaQuery.from(Restaurante.class);
		
		if(Strings.isNotBlank(nome)) {
			Predicate predicateLike = criteriaBuilder.like(rootEntity.get("nome"), "%"+nome+"%");
			criteriaQuery = criteriaQuery.where(predicateLike);
			
		}

		Predicate predicateGraterThanOrEqualTo = null;
		if(taxaFreteInicial != null) {
			 
			predicateGraterThanOrEqualTo = criteriaBuilder.greaterThanOrEqualTo(rootEntity.get("taxaFrete"), taxaFreteInicial);
			criteriaQuery = criteriaQuery.where(predicateGraterThanOrEqualTo);
		}
		
		Predicate predicateLessThanOrEqualTo = null;
		if(taxaFreteFinal!= null) {
			
			 predicateLessThanOrEqualTo = criteriaBuilder.lessThanOrEqualTo(rootEntity.get("taxaFrete"), taxaFreteFinal);
			 criteriaQuery = criteriaQuery.where(predicateLessThanOrEqualTo);
		}
		
		TypedQuery<Restaurante> typedQuery = manager.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	public List<Restaurante> findByNomeComFreteGratis(String nome){
		
		return restauranteRepository.findAll(comFreteGratis().and(comNomeSimilar(nome)));
	}
	
	
}
