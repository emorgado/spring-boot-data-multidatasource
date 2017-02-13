package br.com.emorgado.factory.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.emorgado.factory.domain.Product;

@RepositoryRestResource(collectionResourceRel = "data", path = "products")
public interface ProductRepository 
						extends JpaRepository<Product, Long>{

}
