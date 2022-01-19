package br.com.brunogambim.CRUDCadastroDePedidos.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Client;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Transactional(readOnly = true)
	Page<Order> findByClient(Client client, Pageable pageRequest);
}
