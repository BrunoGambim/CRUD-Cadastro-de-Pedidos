package br.com.brunogambim.CRUDCadastroDePedidos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.City;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.DataIntegrityException;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.ObjectNotFoundException;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.CityRepository;

@Service
public class CityService {
	
	private CityRepository cityRepository;
	
	@Autowired
	public CityService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}
	
	public City findById(Long id) {
		return this.cityRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(id.toString(), City.class));
	}
	
	public List<City> findAll() {
		return this.cityRepository.findAll();
	}
	
	public List<City> findAllByStateId(Long id) {
		return this.cityRepository.findAllByStateId(id);
	}
	
	public City insert(City city) {
		city.setId(null);
		return this.cityRepository.save(city);
	}
	
	public City update(City newCityData) {
		City oldCityData = findById(newCityData.getId());
		updateData(newCityData, oldCityData);
		return this.cityRepository.save(oldCityData);
	}

	private void updateData(City newCityData, City oldCityData) {
		oldCityData.setName(newCityData.getName());
	}

	public void delete(Long id) {
		findById(id);
		try {
			this.cityRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("DELETE", City.class);
		}
	}
}
