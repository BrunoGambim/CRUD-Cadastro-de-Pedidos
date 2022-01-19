package br.com.brunogambim.CRUDCadastroDePedidos.dto.state;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.State;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.v1.StateResource;
import br.com.brunogambim.CRUDCadastroDePedidos.services.StateService;
import br.com.brunogambim.CRUDCadastroDePedidos.services.UserService;

@Component
public class StateAssembler {
	
	private StateService stateService;
	
	public StateAssembler(StateService stateService) {
		this.stateService = stateService;
	}
	
	public GetStateDTO toModel(State state) {
		GetStateDTO stateDTO = new GetStateDTO(state.getId(), state.getName());
		addEntitySelfReference(stateDTO);
		return stateDTO;
	}
	
	public CollectionModel<GetStateDTO> assembleCollectionModel(Collection<State> stateCollection) {
		CollectionModel<GetStateDTO> stateDTOCollection = CollectionModel.of(stateCollection.stream().map(state -> toModel(state)).toList());
		addCollectionSelfReference(stateDTOCollection);
		addCollectionCreateReference(stateDTOCollection);
		return stateDTOCollection;
	}
	
	public GetStateDTO assembleEntityModel(State state) {
		GetStateDTO stateDTO = toModel(state);
		addEntityCitiesReference(stateDTO);
		addEntityUpdateReference(stateDTO);
		addEntityDeleteReference(stateDTO);
		return stateDTO;
	}
	
	private void addCollectionSelfReference(CollectionModel<GetStateDTO> stateDTOCollection){
		stateDTOCollection.add(linkTo(StateResource.class).withSelfRel());
	}
	
	private void addEntitySelfReference(GetStateDTO stateDTO) {
		stateDTO.add(linkTo(methodOn(StateResource.class).findById(stateDTO.getId())).withSelfRel());
	}
	
	private void addEntityCitiesReference(GetStateDTO stateDTO) {
		stateDTO.add(linkTo(methodOn(StateResource.class).findCitiesByStateId(stateDTO.getId())).withRel("cities"));
	}
	
	private void addCollectionCreateReference(CollectionModel<GetStateDTO> stateDTOCollection){
		if(UserService.isAuthorized()){
			stateDTOCollection.add(linkTo(methodOn(StateResource.class).create(null)).withRel("create"));
		}
	}
	
	private void addEntityUpdateReference(GetStateDTO stateDTO) {
		if(UserService.isAuthorized()){
			stateDTO.add(linkTo(methodOn(StateResource.class).update(null,stateDTO.getId())).withRel("update"));
		}
	}
	
	private void addEntityDeleteReference(GetStateDTO stateDTO) {
		if(UserService.isAuthorized() && this.stateService.canBeDeleted(stateDTO.getId())){
			stateDTO.add(linkTo(methodOn(StateResource.class).delete(stateDTO.getId())).withRel("delete"));
		}
	}
}
