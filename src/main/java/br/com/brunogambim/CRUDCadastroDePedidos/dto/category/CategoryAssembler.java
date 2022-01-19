package br.com.brunogambim.CRUDCadastroDePedidos.dto.category;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Category;
import br.com.brunogambim.CRUDCadastroDePedidos.enums.Perfil;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.utils.PaginationUtils;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.utils.URL;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.v1.CategoryResource;
import br.com.brunogambim.CRUDCadastroDePedidos.security.UserSS;
import br.com.brunogambim.CRUDCadastroDePedidos.services.CategoryService;
import br.com.brunogambim.CRUDCadastroDePedidos.services.UserService;

@Component
public class CategoryAssembler extends RepresentationModelAssemblerSupport<Category, GetCategoryDTO> {

	private CategoryService categoryService;
	private PagedResourcesAssembler<Category> pagedResourcesAssembler;
	
	@Autowired
	public CategoryAssembler(CategoryService categoryService, PagedResourcesAssembler<Category> pagedResourcesAssembler) {
		super(CategoryResource.class, GetCategoryDTO.class);
		this.categoryService = categoryService;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
	}

	@Override
	public GetCategoryDTO toModel(Category category) {
		GetCategoryDTO categoryDTO = new GetCategoryDTO(category.getId(), category.getName());
		addEntitySelfReference(categoryDTO);
		return categoryDTO;
	}
	
	public CollectionModel<GetCategoryDTO> assembleCollectionModel(Collection<Category> categoryCollection) {
		CollectionModel<GetCategoryDTO> categoryDTOCollection = CollectionModel.of(categoryCollection.stream().map(category -> toModel(category)).toList());
		addCollectionSelfReference(categoryDTOCollection);
		addCollectionCreateReference(categoryDTOCollection);
		return categoryDTOCollection;
	}
	
	public PagedModel<GetCategoryDTO> assemblePagedModel(Page<Category> categoryPage) {
		Integer page = categoryPage.getNumber();
		Integer size = categoryPage.getSize();
		String sort[] = PaginationUtils.sortToURIParam(categoryPage.getSort());
		PagedModel<GetCategoryDTO> categoryDTOPage = this.pagedResourcesAssembler.toModel(categoryPage,this,
			Link.of(URL.decodeParam(linkTo(methodOn(CategoryResource.class).findPage(page, size, sort)).toString())).withSelfRel());
		addCollectionCreateReference(categoryDTOPage);
		return categoryDTOPage;
	}
	
	public GetCategoryDTO assembleEntityModel(Category category) {
		GetCategoryDTO categoryDTO = toModel(category);
		addEntityUpdateReference(categoryDTO);
		addEntityDeleteReference(categoryDTO);
		return categoryDTO;
	}

	private void addCollectionSelfReference(CollectionModel<GetCategoryDTO> categoryDTOCollection){
		categoryDTOCollection.add(linkTo(CategoryResource.class).withSelfRel());
	}
	
	private void addCollectionCreateReference(CollectionModel<GetCategoryDTO> categoryDTOCollection){
		Optional<UserSS>  user = UserService.authenticated();
		if(!user.isEmpty() && user.get().hasRole(Perfil.ADMIN)){
			categoryDTOCollection.add(linkTo(methodOn(CategoryResource.class).create(null)).withRel("create"));
		}
	}
	
	private void addEntitySelfReference(GetCategoryDTO categoryDTO) {
		categoryDTO.add(linkTo(methodOn(CategoryResource.class).findById(categoryDTO.getId())).withSelfRel());
	}
	
	private void addEntityUpdateReference(GetCategoryDTO categoryDTO) {
		Optional<UserSS>  user = UserService.authenticated();
		if(!user.isEmpty() && user.get().hasRole(Perfil.ADMIN)){
			categoryDTO.add(linkTo(methodOn(CategoryResource.class).update(null,categoryDTO.getId())).withRel("update"));
		}
	}
	
	private void addEntityDeleteReference(GetCategoryDTO categoryDTO) {
		if(UserService.isAuthorized() && this.categoryService.canBeDeleted(categoryDTO.getId())){
			categoryDTO.add(linkTo(methodOn(CategoryResource.class).delete(categoryDTO.getId())).withRel("delete"));
		}
	}
}
