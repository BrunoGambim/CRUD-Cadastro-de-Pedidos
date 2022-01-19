package br.com.brunogambim.CRUDCadastroDePedidos.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Category;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	@Transactional(readOnly = true)
	Page<Product> findDistinctByNameContainingAndCategoriesIn(String name, List<Category> categories, Pageable pageRequest);
	
	@Transactional(readOnly = true)
	List<Product> findAllByCategoriesId(Long id);
	
}
