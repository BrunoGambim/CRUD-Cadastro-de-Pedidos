package br.com.brunogambim.CRUDCadastroDePedidos.resources.v1;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Category;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.category.CategoryAssembler;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.category.CreateCategoryDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.category.GetCategoryDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.category.UpdateCategoryDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.utils.PaginationUtils;
import br.com.brunogambim.CRUDCadastroDePedidos.services.CategoryService;

@RestController
@RequestMapping(value="/v1/categories")
public class CategoryResource {
	
	private CategoryService categoryService;
	private CategoryAssembler categoryAssembler;
	
	@Autowired
	public CategoryResource(CategoryService categoryService, CategoryAssembler categoryAssembler) {
		this.categoryService = categoryService;
		this.categoryAssembler = categoryAssembler;
	}
	 
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<GetCategoryDTO> findById(@PathVariable Long id) {
		Category category = this.categoryService.findById(id);
		GetCategoryDTO categoryDTO = this.categoryAssembler.assembleEntityModel(category);
		return ResponseEntity.ok()
				.body(categoryDTO);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<CollectionModel<GetCategoryDTO>> findAll() {
		List<Category> categoryList = this.categoryService.findAll();
		CollectionModel<GetCategoryDTO> categoryDTOCollection = this.categoryAssembler.assembleCollectionModel(categoryList);
		return ResponseEntity.ok().body(categoryDTOCollection);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<PagedModel<GetCategoryDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "6") Integer size,
			@RequestParam(value = "sort", defaultValue = "name,asc") String[] sort) {
		Page<Category> categoryPage = this.categoryService.findPage(page, size, 
				PaginationUtils.getSortOrderByFromURIParam(sort), 
				PaginationUtils.getSortDirectionFromURIParam(sort));
		PagedModel<GetCategoryDTO> categoryDTOPage = this.categoryAssembler.assemblePagedModel(categoryPage);
		return ResponseEntity.ok().body(categoryDTOPage);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<GetCategoryDTO> create(@Valid @RequestBody CreateCategoryDTO categoryDTO){
		Category category = categoryDTO.toEntity();
		Category createdCategory = this.categoryService.insert(category);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdCategory.getId()).toUri();
		GetCategoryDTO createdCategoryDTO = this.categoryAssembler.assembleEntityModel(createdCategory);
		return ResponseEntity.created(uri).body(createdCategoryDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<GetCategoryDTO> update(@Valid @RequestBody UpdateCategoryDTO categoryDTO, @PathVariable Long id){
		Category category = categoryDTO.toEntity(id);
		Category updatedCategory = this.categoryService.update(category);
		GetCategoryDTO updatedCategoryDTO = this.categoryAssembler.assembleEntityModel(updatedCategory);
		return ResponseEntity.ok().body(updatedCategoryDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id){
		this.categoryService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
