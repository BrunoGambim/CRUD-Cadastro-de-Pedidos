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

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Product;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.product.CreateProductDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.product.GetProductDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.product.ProductAssembler;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.product.UpdateProductDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.utils.PaginationUtils;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.utils.URL;
import br.com.brunogambim.CRUDCadastroDePedidos.services.ProductService;

@RestController
@RequestMapping(value = "/v1/products")
public class ProductResource {
	
	private ProductService productService;
	private ProductAssembler productAssembler;
	
	@Autowired
	public ProductResource (ProductService productService, ProductAssembler productAssembler) {
		this.productService = productService;
		this.productAssembler = productAssembler;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<GetProductDTO> findById(@PathVariable Long id){
		GetProductDTO productDTO = this.productAssembler.assembleEntityModel(this.productService.findById(id));
		return ResponseEntity.ok().body(productDTO);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<CollectionModel<GetProductDTO>> findAll() {
		List<Product> productList = this.productService.findAll();
		CollectionModel<GetProductDTO> productDTOCollection = this.productAssembler.assembleCollectionModel(productList);
		return ResponseEntity.ok().body(productDTOCollection);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<PagedModel<GetProductDTO>> findPage(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "ids", defaultValue = "") String ids,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "sort", defaultValue = "name,asc") String[] sort) {
		Page<Product> productPage = this.productService.findPage(URL.decodeParam(name), URL.decodeLongList(ids), page, size, 
				PaginationUtils.getSortOrderByFromURIParam(sort), 
				PaginationUtils.getSortDirectionFromURIParam(sort));
		PagedModel<GetProductDTO> productDTOPage = this.productAssembler.assemblePagedModel(productPage,name,ids);
		return ResponseEntity.ok()
				.body(productDTOPage);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<GetProductDTO> create(@Valid @RequestBody CreateProductDTO productDTO){
		Product product = productDTO.toEntity();
		Product createdProduct = this.productService.insert(product);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdProduct.getId()).toUri();
		GetProductDTO createdProductDTO = this.productAssembler.assembleEntityModel(createdProduct);
		return ResponseEntity.created(uri).body(createdProductDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<GetProductDTO> update(@Valid @RequestBody UpdateProductDTO productDTO, @PathVariable Long id){
		Product product = productDTO.toEntity(id);
		Product updatedProduct = this.productService.update(product);
		GetProductDTO updatedProductDTO = this.productAssembler.assembleEntityModel(updatedProduct);
		return ResponseEntity.ok().body(updatedProductDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id){
		this.productService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
