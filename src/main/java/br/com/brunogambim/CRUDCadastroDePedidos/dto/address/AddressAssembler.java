package br.com.brunogambim.CRUDCadastroDePedidos.dto.address;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Address;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.city.CityAssembler;
import br.com.brunogambim.CRUDCadastroDePedidos.enums.Perfil;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.v1.ClientResource;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.v1.OrderResource;
import br.com.brunogambim.CRUDCadastroDePedidos.security.UserSS;
import br.com.brunogambim.CRUDCadastroDePedidos.services.AddressService;
import br.com.brunogambim.CRUDCadastroDePedidos.services.UserService;

@Component
public class AddressAssembler{

	private CityAssembler cityAssembler;
	private AddressService addressService;
	
	@Autowired
	public AddressAssembler(CityAssembler cityAssembler, AddressService addressService) {
		this.cityAssembler = cityAssembler;
		this.addressService = addressService;
	}
	
	public GetAddressDTO toModel(Address address) {
		GetAddressDTO addressDTO = new GetAddressDTO(address.getId(), address.getStreet(), address.getNumber(), address.getComplement(),
				address.getDistrict(), address.getCep(), this.cityAssembler.assembleEntityModel(address.getCity()));
		
		return addressDTO;
	}
	
	public CollectionModel<GetAddressDTO> assembleCollectionModelByClientId(Collection<Address> addressCollection, Long clientId) {
		CollectionModel<GetAddressDTO> addressDTOCollection = CollectionModel.of(addressCollection.stream().map(address -> toModel(address)).toList());
		addCollectionElementsDeleteReference(addressDTOCollection, clientId);
		addCollectionSelfReferenceByClientId(addressDTOCollection, clientId);
		addCollectionClientReferenceByClientId(addressDTOCollection, clientId);
		addCollectionCreateReference(addressDTOCollection, clientId);
		return addressDTOCollection;
	}
	
	public GetAddressDTO assembleEntityModelByOrderId(Address address, Long orderId) {
		GetAddressDTO addressDTO = toModel(address);
		addEntitySelfReferenceByOrderId(addressDTO, orderId);
		addEntityOrderReferenceByOrderId(addressDTO, orderId);
		return addressDTO;
	}
	
	public GetAddressDTO assembleEntityModelByClientId(Address address, Long clientId) {
		GetAddressDTO addressDTO = toModel(address);
		addEntitySelfReferenceByClientId(addressDTO, clientId);
		addEntityClientReferenceByClientId(addressDTO, clientId);
		addEntityDeleteReference(addressDTO, clientId);
		return addressDTO;
	}
	
	private void addCollectionElementsDeleteReference(CollectionModel<GetAddressDTO> addressDTOCollection, Long clientId){
		addressDTOCollection.forEach(addressDTO -> addEntityDeleteReference(addressDTO, clientId));
	}
	
	private void addCollectionSelfReferenceByClientId(CollectionModel<GetAddressDTO> addressDTOCollection, Long clientId){
		addressDTOCollection.add(linkTo(methodOn(ClientResource.class).findAddressByClientId(clientId)).withSelfRel());
	}
	
	private void addCollectionClientReferenceByClientId(CollectionModel<GetAddressDTO> addressDTOCollection, Long clientId){
		addressDTOCollection.add(linkTo(methodOn(ClientResource.class).findById(clientId)).withRel("client"));
	}
	
	private void addCollectionCreateReference(CollectionModel<GetAddressDTO> addressDTOCollection, Long clientId){
		Optional<UserSS>  user = UserService.authenticated();
		if(!user.isEmpty() && (user.get().hasRole(Perfil.ADMIN) || user.get().getId().equals(clientId))){
			addressDTOCollection.add(linkTo(methodOn(ClientResource.class).createAddressToClient(null, clientId)).withRel("create"));
		}
	}
	
	private void addEntitySelfReferenceByOrderId(GetAddressDTO addressDTO, Long orderId){
		addressDTO.add(linkTo(methodOn(OrderResource.class).findDeliveryAddressByOrderId(orderId)).withSelfRel());
	}
	
	private void addEntityOrderReferenceByOrderId(GetAddressDTO addressDTO, Long orderId){
		addressDTO.add(linkTo(methodOn(OrderResource.class).findById(orderId)).withRel("order"));
	}
	
	private void addEntitySelfReferenceByClientId(GetAddressDTO addressDTO, Long clientId){
		addressDTO.add(linkTo(methodOn(ClientResource.class).findAddressByClientId(clientId)).withSelfRel());
	}
	
	private void addEntityClientReferenceByClientId(GetAddressDTO addressDTO, Long clientId){
		addressDTO.add(linkTo(methodOn(ClientResource.class).findById(clientId)).withRel("client"));
	}
	private void addEntityDeleteReference(GetAddressDTO addressDTO, Long clientId) {
		if(UserService.isAuthorized(clientId) && this.addressService.canBeDeleted(addressDTO.getId())){
			addressDTO.add(linkTo(methodOn(ClientResource.class).deleteAddressFromClient(clientId,addressDTO.getId())).withRel("delete"));
		}
	}
}