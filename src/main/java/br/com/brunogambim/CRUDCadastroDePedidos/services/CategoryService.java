package br.com.brunogambim.CRUDCadastroDePedidos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Category;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.DataIntegrityException;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.ObjectNotFoundException;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.CategoryRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.ProductRepository;

@Service
public class CategoryService {
	
	private CategoryRepository categoryRepository;
	private ProductRepository productRepository;
	
	@Autowired
	public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
	}

	public Category findById(Long id) {
		return this.categoryRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(id.toString(), Category.class));
	}
	
	public List<Category> findAll() {
		return this.categoryRepository.findAll();
	}
	
	public List<Category> findAllById(List<Long> ids) {
		return this.categoryRepository.findAllById(ids);
	}
	
	public Page<Category> findPage(Integer page, Integer size, String sort, String direction){
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), sort);
		return this.categoryRepository.findAll(pageRequest);
	}
	
	public Category insert(Category category) {
		category.setId(null);
		return this.categoryRepository.save(category);
	}
	
	public Category update(Category newCategoryData) {
		Category oldCategoryData = findById(newCategoryData.getId());
		updateData(newCategoryData, oldCategoryData);
		return this.categoryRepository.save(oldCategoryData);
	}

	private void updateData(Category newCategoryData, Category oldCategoryData) {
		oldCategoryData.setName(newCategoryData.getName());
	}

	public void delete(Long id) {
		findById(id);
		try {
			this.categoryRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("DELETE", Category.class);
		}
	}
	
	public boolean canBeDeleted(Long id) {
		return this.productRepository.findAllByCategoriesId(id).isEmpty();
	}
}
