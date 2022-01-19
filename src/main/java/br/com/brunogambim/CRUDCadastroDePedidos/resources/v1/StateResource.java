package br.com.brunogambim.CRUDCadastroDePedidos.resources.v1;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.State;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.city.CityAssembler;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.city.GetCityDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.state.CreateStateDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.state.GetStateDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.state.UpdateStateDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.state.StateAssembler;
import br.com.brunogambim.CRUDCadastroDePedidos.services.StateService;

@RestController
@RequestMapping(value="/v1/states")
public class StateResource {
	
	private StateService stateService;
	private CityAssembler cityAssembler;
	private StateAssembler stateAssembler;
	
	@Autowired
	public StateResource(StateService stateService, CityAssembler cityAssembler,
			StateAssembler stateAssembler) {
		this.stateService = stateService;
		this.cityAssembler = cityAssembler;
		this.stateAssembler = stateAssembler;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<GetStateDTO> findById(@PathVariable Long id){
		GetStateDTO stateDTO = this.stateAssembler.assembleEntityModel(this.stateService.findById(id));
		return ResponseEntity.ok().body(stateDTO);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<CollectionModel<GetStateDTO>> findAll() {
		List<State> stateList = this.stateService.findAll();
		CollectionModel<GetStateDTO> stateDTOList = this.stateAssembler.assembleCollectionModel(stateList);
		return ResponseEntity.ok().body(stateDTOList);
	}
	
	@RequestMapping(value = "/{id}/cities",method = RequestMethod.GET)
	public ResponseEntity<CollectionModel<GetCityDTO>> findCitiesByStateId(@PathVariable Long id) {
		CollectionModel<GetCityDTO> cityDTOList = this.cityAssembler.assembleCollectionModelByStateId(this.stateService.findCitiesAllByStateId(id), id);
		return ResponseEntity.ok().body(cityDTOList);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<GetStateDTO> create(@Valid @RequestBody CreateStateDTO stateDTO){
		State state = stateDTO.toEntity();
		State createdState = this.stateService.insert(state);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdState.getId()).toUri();
		GetStateDTO createdStateDTO = this.stateAssembler.assembleEntityModel(createdState);
		return ResponseEntity.created(uri).body(createdStateDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<GetStateDTO> update(@Valid @RequestBody UpdateStateDTO stateDTO, @PathVariable Long id){
		State state = stateDTO.toEntity(id);
		State updatedState = this.stateService.update(state);
		GetStateDTO updatedStateDTO = this.stateAssembler.assembleEntityModel(updatedState);
		return ResponseEntity.ok().body(updatedStateDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id){
		this.stateService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
