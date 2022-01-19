package br.com.brunogambim.CRUDCadastroDePedidos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Category;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Product;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.DataIntegrityException;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.ObjectNotFoundException;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.OrderItemRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.ProductRepository;

@Service
public class ProductService {
	private ProductRepository productRepository;
	private OrderItemRepository orderItemRepository;
	private CategoryService categoryService;
	
	@Autowired
	public ProductService(ProductRepository productRepository, CategoryService categoryService,
			OrderItemRepository orderItemRepository) {
		this.productRepository = productRepository;
		this.categoryService = categoryService;
		this.orderItemRepository = orderItemRepository;
	}
	
	public Product findById(Long id) {
		return this.productRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(id.toString(), Product.class));
	}
	
	public List<Product> findAll(){
		return this.productRepository.findAll();
	}
	
	public List<Product> findAllByCategoryId(Long id){
		return this.productRepository.findAllByCategoriesId(id);
	}
	
	public Page<Product> findPage(String name, List<Long> ids, Integer page, Integer size, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		List<Category> categories = this.categoryService.findAllById(ids);
		return this.productRepository.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);
	}
	
	public Product insert(Product product) {
		product.setId(null);
		return this.productRepository.save(product);
	}
	
	public Product update(Product newProductData) {
		Product oldProductData = findById(newProductData.getId());
		updateData(newProductData, oldProductData);
		return this.productRepository.save(oldProductData);
	}

	private void updateData(Product newProductData, Product oldProductData) {
		oldProductData.setName(newProductData.getName());
		oldProductData.setPrice(newProductData.getPrice());
	}

	public void delete(Long id) {
		findById(id);
		try {
			this.productRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("DELETE", Product.class);
		}
	}
	
	public boolean canBeDeleted(Long id) {
		return this.orderItemRepository.findAllByProductId(id).isEmpty();
	}
}
