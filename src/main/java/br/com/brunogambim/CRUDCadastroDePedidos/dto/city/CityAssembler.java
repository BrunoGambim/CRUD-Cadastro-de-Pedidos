package br.com.brunogambim.CRUDCadastroDePedidos.dto.city;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.City;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.v1.CityResource;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.v1.StateResource;
import br.com.brunogambim.CRUDCadastroDePedidos.services.UserService;

@Component
public class CityAssembler {
	
	public GetCityDTO toModel(City city) {
		GetCityDTO cityDTO = new GetCityDTO(city.getId(), city.getName());
		addEntitySelfReference(cityDTO);
		return cityDTO;
	}
	
	public CollectionModel<GetCityDTO> assembleCollectionModel(Collection<City> cityCollection) {
		CollectionModel<GetCityDTO> cityDTOCollection = CollectionModel.of(cityCollection.stream().map(city -> toModel(city)).toList());
		addCollectionSelfReference(cityDTOCollection);
		addCollectionCreateReference(cityDTOCollection);
		return cityDTOCollection;
	}
		
	public CollectionModel<GetCityDTO> assembleCollectionModelByStateId(Collection<City> cityCollection, Long stateId) {
		CollectionModel<GetCityDTO> cityDTOCollection = CollectionModel.of(cityCollection.stream().map(city -> toModel(city)).toList());
		addCollectionSelfReferenceByStateId(cityDTOCollection, stateId);
		return cityDTOCollection;
	}
	
	public GetCityDTO assembleEntityModel(City city) {
		GetCityDTO cityDTO = toModel(city);
		addEntityUpdateReference(cityDTO);
		addEntityDeleteReference(cityDTO);
		addEntityReferenceToState(cityDTO, city.getState().getId());
		return cityDTO;
	}
	
	private void addCollectionSelfReference(CollectionModel<GetCityDTO> cityDTOCollection){
		cityDTOCollection.add(linkTo(CityResource.class).withSelfRel());
	}
	
	private void addCollectionCreateReference(CollectionModel<GetCityDTO> cityDTOCollection){
		if(UserService.isAuthorized()){
			cityDTOCollection.add(linkTo(methodOn(CityResource.class).create(null)).withRel("create"));
		}
	}
	
	private void addCollectionSelfReferenceByStateId(CollectionModel<GetCityDTO> cityDTOCollection, Long stateId){
		cityDTOCollection.add(linkTo(methodOn(StateResource.class).findCitiesByStateId(stateId)).withSelfRel());
	}
	
	private void addEntityReferenceToState(GetCityDTO cityDTO, Long stateId){
		cityDTO.add(linkTo(methodOn(StateResource.class).findById(stateId)).withRel("state"));
	}
	
	private void addEntitySelfReference(GetCityDTO cityDTO) {
		cityDTO.add(linkTo(methodOn(CityResource.class).findById(cityDTO.getId())).withSelfRel());
	}
	
	private void addEntityUpdateReference(GetCityDTO cityDTO) {
		if(UserService.isAuthorized()){
			cityDTO.add(linkTo(methodOn(CityResource.class).update(null,cityDTO.getId())).withRel("update"));
		}
	}
	
	private void addEntityDeleteReference(GetCityDTO cityDTO) {
		if(UserService.isAuthorized()){
			cityDTO.add(linkTo(methodOn(CityResource.class).delete(cityDTO.getId())).withRel("delete"));
		}
	}
}
