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

import br.com.brunogambim.CRUDCadastroDePedidos.domain.City;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.city.CityAssembler;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.city.CreateCityDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.city.GetCityDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.city.UpdateCityDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.services.CityService;

@RestController
@RequestMapping(value="/v1/cities")
public class CityResource {
	
	private CityService cityService;
	private CityAssembler cityAssembler;
	
	@Autowired
	public CityResource(CityService cityService, CityAssembler cityAssembler) {
		this.cityService = cityService;
		this.cityAssembler = cityAssembler;
	}
	 
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<GetCityDTO> findById(@PathVariable Long id) {
		City city = this.cityService.findById(id);
		GetCityDTO cityDTO = this.cityAssembler.assembleEntityModel(city);
		return ResponseEntity.ok()
				.body(cityDTO);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<CollectionModel<GetCityDTO>> findAll() {
		List<City> cityList = this.cityService.findAll();
		CollectionModel<GetCityDTO> cityDTOCollection = this.cityAssembler.assembleCollectionModel(cityList);
		return ResponseEntity.ok().body(cityDTOCollection);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<GetCityDTO> create(@Valid @RequestBody CreateCityDTO cityDTO){
		City city = cityDTO.toEntity();
		City createdCity = this.cityService.insert(city);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdCity.getId()).toUri();
		GetCityDTO createdCityDTO = this.cityAssembler.assembleEntityModel(createdCity);
		return ResponseEntity.created(uri).body(createdCityDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<GetCityDTO> update(@Valid @RequestBody UpdateCityDTO cityDTO, @PathVariable Long id){
		City city = cityDTO.toEntity(id);
		City updatedCity = this.cityService.update(city);
		GetCityDTO updatedCityDTO = this.cityAssembler.assembleEntityModel(updatedCity);
		return ResponseEntity.ok().body(updatedCityDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id){
		this.cityService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
