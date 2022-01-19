package br.com.brunogambim.CRUDCadastroDePedidos.dto.order.orderitem;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.OrderItem;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.product.GetProductDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.product.ProductAssembler;

@Component
public class OrderItemAssembler {
	private ProductAssembler productAssembler;
	
	public OrderItemAssembler(ProductAssembler productAssembler) {
		this.productAssembler = productAssembler;
	}
	
	public GetOrderItemDTO toModel(OrderItem orderItem) {
		GetProductDTO productDTO = this.productAssembler.toModel(orderItem.getProduct());
		GetOrderItemDTO orderItemDTO = new GetOrderItemDTO(orderItem.getNumberOfItens(), orderItem.getDiscount(),
				productDTO, orderItem.getSubtotal());
		return orderItemDTO;
	}
	
	public Set<GetOrderItemDTO> assembleCollection(Set<OrderItem> orderItemList){
		return orderItemList.stream().map(orderItem -> toModel(orderItem)).collect(Collectors.toSet());
	}
}
