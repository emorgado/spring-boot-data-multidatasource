package br.com.emorgado.financial.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.emorgado.financial.domain.Sale;

@RepositoryRestResource(collectionResourceRel = "data", path = "sales")
public interface SaleRepository 
					extends JpaRepository<Sale, Long> {

}
