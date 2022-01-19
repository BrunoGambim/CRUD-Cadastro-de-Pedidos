package br.com.brunogambim.CRUDCadastroDePedidos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.City;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.State;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.DataIntegrityException;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.ObjectNotFoundException;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.StateRepository;

@Service
public class StateService {
	
	private StateRepository stateRepository;
	private CityService cityService;
	
	@Autowired
	public StateService(StateRepository stateRepository, CityService cityService) {
		this.stateRepository = stateRepository;
		this.cityService = cityService;
	}
	
	public State findById(Long id) {
		return this.stateRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(id.toString(), State.class));
	}
	
	public List<State> findAll() {
		return this.stateRepository.findAllByOrderByName();
	}
	
	public State insert(State state) {
		state.setId(null);
		return this.stateRepository.save(state);
	}
	
	public State update(State newStateData) {
		State oldStateData = findById(newStateData.getId());
		updateData(newStateData, oldStateData);
		return this.stateRepository.save(oldStateData);
	}

	private void updateData(State newStateData, State oldStateData) {
		oldStateData.setName(newStateData.getName());
	}

	public void delete(Long id) {
		findById(id);
		try {
			this.stateRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("DELETE", State.class);
		}
	}
	
	public List<City> findCitiesAllByStateId(Long id) {
		return this.cityService.findAllByStateId(id);
	}
	
	public boolean canBeDeleted(Long id) {
		return this.cityService.findAllByStateId(id).isEmpty();
	}
}
