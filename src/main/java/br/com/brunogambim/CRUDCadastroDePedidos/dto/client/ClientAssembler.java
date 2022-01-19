package br.com.brunogambim.CRUDCadastroDePedidos.dto.client;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Client;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.utils.URL;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.v1.ClientResource;
import br.com.brunogambim.CRUDCadastroDePedidos.services.UserService;

@Component
public class ClientAssembler{
	
	@Value("${img.prefix.client.profile}")
	private String profileImgPrefix;

	public GetClientDTO toModel(Client client) {
		GetClientDTO clientDTO = new GetClientDTO(client.getId(), client.getName(), client.getEmail());
		addEntitySelfReference(clientDTO);
		return clientDTO;
	}
	
	public CollectionModel<GetClientDTO> assembleCollectionModel(Collection<Client> clientCollection) {
		CollectionModel<GetClientDTO> clientDTOCollection = CollectionModel.of(clientCollection.stream().map(client -> toModel(client)).toList());
		addCollectionSelfReference(clientDTOCollection);
		addCollectionCreateReference(clientDTOCollection);
		return clientDTOCollection;
	}
	
	public GetClientDTO assembleEntityModel(Client client) {
		GetClientDTO clientDTO = toModel(client);
		addEntityAddressReference(clientDTO);
		addEntityOrdersReference(clientDTO);
		addEntityUpdateReference(clientDTO);
		addEntityDeleteReference(clientDTO);
		addEntityUploadPictureReference(clientDTO);
		return clientDTO;
	}

	private void addCollectionSelfReference(CollectionModel<GetClientDTO> clientDTOCollection){
		clientDTOCollection.add(linkTo(ClientResource.class).withSelfRel());
	}
	
	private void addCollectionCreateReference(CollectionModel<GetClientDTO> clientDTOCollection){
		if(UserService.isAuthorized()){
			clientDTOCollection.add(linkTo(methodOn(ClientResource.class).create(null)).withRel("create"));
		}
	}
	
	private void addEntitySelfReference(GetClientDTO clientDTO) {
		clientDTO.add(linkTo(methodOn(ClientResource.class).findById(clientDTO.getId())).withSelfRel());
	}
	
	private void addEntityAddressReference(GetClientDTO clientDTO) {
		clientDTO.add(linkTo(methodOn(ClientResource.class).findAddressByClientId(clientDTO.getId())).withRel("address"));
	}
	
	private void addEntityUpdateReference(GetClientDTO clientDTO) {
		if(UserService.isAuthorized(clientDTO.getId())){
			clientDTO.add(linkTo(methodOn(ClientResource.class).update(null,clientDTO.getId())).withRel("update"));
		}
	}
	
	private void addEntityOrdersReference(GetClientDTO clientDTO) {
		if(UserService.isAuthorized(clientDTO.getId())){
			clientDTO.add(Link.of(URL.decodeParam(
					linkTo(methodOn(ClientResource.class).findPage(clientDTO.getId(),0,4,new String[]{"createdAt","asc"})).toString()))
					.withRel("order"));
			;
		}
	}
	
	private void addEntityUploadPictureReference(GetClientDTO clientDTO) {
		if(UserService.isAuthorized(clientDTO.getId())){
			clientDTO.add(linkTo(methodOn(ClientResource.class).uploadProfilePicture(clientDTO.getId(), null)).withRel("upload-picture"));
		}
	}
	
	private void addEntityDeleteReference(GetClientDTO clientDTO) {
		if(UserService.isAuthorized(clientDTO.getId())){
			clientDTO.add(linkTo(methodOn(ClientResource.class).delete(clientDTO.getId())).withRel("delete"));
		}
	}
}
