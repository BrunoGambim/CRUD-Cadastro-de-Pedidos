package br.com.brunogambim.CRUDCadastroDePedidos.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.OrderItem;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.OrderItemRepository;

@Service
public class OrderItemService {
	
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	public OrderItemService(OrderItemRepository orderItemRepository) {
		this.orderItemRepository = orderItemRepository;
	}
	
	public OrderItem insert(OrderItem orderItem) {
		orderItem.setId(null);
		return this.orderItemRepository.save(orderItem);
	}
	
	public Collection<OrderItem> insertAll(Collection<OrderItem> orderItemList) {
		orderItemList.forEach(orderItem -> {
			orderItem.setId(null);
		});
		return this.orderItemRepository.saveAll(orderItemList);
	}
}
