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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Address;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Client;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Order;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.client.UpdateClientDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.order.GetOrderDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.order.OrderAssembler;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.utils.PaginationUtils;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.address.AddressAssembler;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.address.CreateAddressDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.address.GetAddressDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.client.ClientAssembler;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.client.CreateClientDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.client.GetClientDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.services.ClientService;
import br.com.brunogambim.CRUDCadastroDePedidos.services.OrderService;

@RestController
@RequestMapping(value = "/v1/clients")
public class ClientResource {
	
	private ClientService clientService;
	private OrderService orderService;
	private ClientAssembler clientAssembler;
	private AddressAssembler addressAssembler;
	private OrderAssembler orderAssembler;
	
	@Autowired
	public ClientResource (ClientService clientService, ClientAssembler clientAssembler,
			AddressAssembler addressAssembler, OrderAssembler orderAssembler, OrderService orderService) {
		this.clientService = clientService;
		this.clientAssembler = clientAssembler;
		this.addressAssembler = addressAssembler;
		this.orderAssembler = orderAssembler;
		this.orderService = orderService;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<GetClientDTO> findById(@PathVariable Long id) {
		GetClientDTO clientDTO = this.clientAssembler.assembleEntityModel(this.clientService.findById(id));
		return ResponseEntity.ok().body(clientDTO);
	}
	
	@RequestMapping(value="{id}/orders/page" ,method = RequestMethod.GET)
	public ResponseEntity<PagedModel<GetOrderDTO>> findPage(
			@PathVariable Long id,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "4") Integer size,
			@RequestParam(value = "sort", defaultValue = "createdAt,asc") String[] sort) {
		Page<Order> orderPage = this.orderService.findPage(id, page, size, 
				PaginationUtils.getSortOrderByFromURIParam(sort), 
				PaginationUtils.getSortDirectionFromURIParam(sort));
		PagedModel<GetOrderDTO> orderDTOPage = this.orderAssembler.assemblePagedModelByClientId(orderPage,id);
		return ResponseEntity.ok().body(orderDTOPage);
	}
	
	@RequestMapping(value = "/{id}/addresses", method = RequestMethod.GET)
	public ResponseEntity<CollectionModel<GetAddressDTO>> findAddressByClientId(@PathVariable Long id) {
		Client client = this.clientService.findById(id);
		CollectionModel<GetAddressDTO> addressDTOList = this.addressAssembler.assembleCollectionModelByClientId(client.getAddresses(), id);
		return ResponseEntity.ok().body(addressDTOList);
	}
	
	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public ResponseEntity<GetClientDTO> findByEmail(@RequestParam(value = "email") String email) {
		GetClientDTO clientDTO = this.clientAssembler.assembleEntityModel(this.clientService.findByEmail(email));
		return ResponseEntity.ok().body(clientDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<CollectionModel<GetClientDTO>> findAll() {
		List<Client> clientList = this.clientService.findAll();
		CollectionModel<GetClientDTO> clientDTOCollection = this.clientAssembler.assembleCollectionModel(clientList);
		return ResponseEntity.ok().body(clientDTOCollection);
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<GetClientDTO> create(@Valid @RequestBody CreateClientDTO clientDTO){
		Client client = clientDTO.toEntity();
		Client createdClient = this.clientService.insert(client);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdClient.getId()).toUri();
		GetClientDTO createdClientDTO = this.clientAssembler.assembleEntityModel(createdClient);
		return ResponseEntity.created(uri).body(createdClientDTO);
	}
	
	@Transactional
	@RequestMapping(value = "/{id}/addresses",method = RequestMethod.POST)
	public ResponseEntity<GetAddressDTO> createAddressToClient(@Valid @RequestBody CreateAddressDTO addressDTO, @PathVariable Long id){
		Address address = addressDTO.toEntity();
		Address createdAddress = this.clientService.insertAddressToClient(id, address);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdAddress.getId()).toUri();
		GetAddressDTO createdAddressDTO = this.addressAssembler.assembleEntityModelByClientId(createdAddress,id);
		return ResponseEntity.created(uri).body(createdAddressDTO);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<GetClientDTO> update(@Valid @RequestBody UpdateClientDTO clientDTO, @PathVariable Long id){
		clientDTO.setId(id);
		Client client = clientDTO.toEntity();
		Client updatedClient = this.clientService.update(client);
		GetClientDTO updatedClientDTO = this.clientAssembler.assembleEntityModel(updatedClient);
		return ResponseEntity.ok(updatedClientDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id){
		this.clientService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{clientId}/addresses/{addressId}", method = RequestMethod.DELETE)
	public ResponseEntity<CollectionModel<GetAddressDTO>> deleteAddressFromClient(@PathVariable Long clientId, @PathVariable Long addressId) {
		this.clientService.deleteAddressFromClient(clientId, addressId);
		return ResponseEntity.noContent().build();
	}
	
	@Transactional
	@RequestMapping(value = "/{id}/picture" ,method = RequestMethod.POST, consumes = {"multipart/form-data"})
	public ResponseEntity<URI> uploadProfilePicture(@PathVariable Long id ,@ModelAttribute MultipartFile file){
		URI uri = clientService.uploadProfilePicture(id, file);
		return ResponseEntity.created(uri).build();
	}
}
