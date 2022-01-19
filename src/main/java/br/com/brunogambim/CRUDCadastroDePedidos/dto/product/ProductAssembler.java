package br.com.brunogambim.CRUDCadastroDePedidos.dto.product;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Product;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.utils.PaginationUtils;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.utils.URL;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.v1.ProductResource;
import br.com.brunogambim.CRUDCadastroDePedidos.services.ProductService;
import br.com.brunogambim.CRUDCadastroDePedidos.services.UserService;

@Component
public class ProductAssembler extends RepresentationModelAssemblerSupport<Product, GetProductDTO> {

	private PagedResourcesAssembler<Product> pagedResourcesAssembler;
	private ProductService productService;
	
	@Autowired
	public ProductAssembler(PagedResourcesAssembler<Product> pagedResourcesAssembler, ProductService productService) {
		super(ProductResource.class, GetProductDTO.class);
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.productService = productService;
	}

	@Override
	public GetProductDTO toModel(Product product) {
		GetProductDTO productDTO = new GetProductDTO(product.getId(), product.getName(), product.getPrice());
		addEntitySelfReference(productDTO);
		return productDTO;
	}
	
	public CollectionModel<GetProductDTO> assembleCollectionModel(Collection<Product> productCollection) {
		CollectionModel<GetProductDTO> productDTOCollection = CollectionModel.of(productCollection.stream().map(product -> toModel(product)).toList());
		addCollectionSelfReference(productDTOCollection);
		return productDTOCollection;
	}
	
	public PagedModel<GetProductDTO> assemblePagedModel(Page<Product> productPage,String name, String ids) {
		Integer page = productPage.getNumber();
		Integer size = productPage.getSize();
		String sort[] = PaginationUtils.sortToURIParam(productPage.getSort());
		PagedModel<GetProductDTO> productDTOList = this.pagedResourcesAssembler.toModel(productPage,this,
			Link.of(URL.decodeParam(linkTo(methodOn(ProductResource.class).findPage(name,ids,page, size, sort)).toString())).withSelfRel());
		addCollectionCreateReference(productDTOList);
		return productDTOList;
	}
	
	public GetProductDTO assembleEntityModel(Product product) {
		GetProductDTO productDTO = toModel(product);
		addEntityUpdateReference(productDTO);
		addEntityDeleteReference(productDTO);
		return productDTO;
	}

	private void addCollectionSelfReference(CollectionModel<GetProductDTO> productDTOCollection){
		productDTOCollection.add(linkTo(ProductResource.class).withSelfRel());
	}
	
	private void addCollectionCreateReference(CollectionModel<GetProductDTO> productDTOCollection){
		if(UserService.isAuthorized()){
			productDTOCollection.add(linkTo(methodOn(ProductResource.class).create(null)).withRel("create"));
		}
	}
	
	private void addEntitySelfReference(GetProductDTO productDTO) {
		productDTO.add(linkTo(methodOn(ProductResource.class).findById(productDTO.getId())).withSelfRel());
	}
	
	private void addEntityUpdateReference(GetProductDTO productDTO) {
		if(UserService.isAuthorized()){
			productDTO.add(linkTo(methodOn(ProductResource.class).update(null,productDTO.getId())).withRel("update"));
		}
	}
	
	private void addEntityDeleteReference(GetProductDTO productDTO) {
		if(UserService.isAuthorized() && this.productService.canBeDeleted(productDTO.getId())){
			productDTO.add(linkTo(methodOn(ProductResource.class).delete(productDTO.getId())).withRel("delete"));
		}
	}
}
