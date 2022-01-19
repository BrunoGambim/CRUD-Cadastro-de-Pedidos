package br.com.brunogambim.CRUDCadastroDePedidos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{
	@Transactional(readOnly = true)
	List<City> findAllByStateId(Long id);
}
