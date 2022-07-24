package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.NoRepositoryBean;

import com.algaworks.algafood.infraestructure.repository.CustomJpaRepositoryImpl;

/**
 * O objetivo desta classe é criar novos métodos que ficarão
 * 		disponíveis em qualquer classe que herde deste repositório customizado.
 * 	Ou seja, quem herdar esta classe terá acesso a todos os métodos fornecidos 
 * 		pelo framework SpringDataJpa + os novos métodos definidos aqui.
 * 	
 * 	Entao, as classes repositório agora devem extender a classe concreta que
 * 		implementa está interface:
 * 			CustomJpaRepositoryImpl.java
 * 
 * 	A classe principal do projeto, a classe Application, agora deve referenciar
 * 		a classe que implementa este repositorio com a seguinte Annotation:
 * 			@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
 * 
 * 	As interfaces do tipo repositório agora precisam extender esta interface ao invés
 * 		de extender JpaRepository
 * 
 * @author rudiney
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {

	public Optional<T> buscarPrimeiro();
}
