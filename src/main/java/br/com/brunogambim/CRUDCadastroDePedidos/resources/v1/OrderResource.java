package br.com.brunogambim.CRUDCadastroDePedidos.resources.v1;

import java.net.URI;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Address;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Order;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.address.AddressAssembler;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.address.GetAddressDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.order.CreateOrderDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.order.GetOrderDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.order.OrderAssembler;
import br.com.brunogambim.CRUDCadastroDePedidos.services.OrderService;

@RestController
@RequestMapping(value = "/v1/orders")
public class OrderResource {
	
	private OrderService orderService;
	private AddressAssembler addressAssembler;
	private OrderAssembler orderAssembler;
	
	@Autowired
	public OrderResource (OrderService OrderService, AddressAssembler addressAssembler,
			OrderAssembler orderAssembler) {
		this.orderService = OrderService;
		this.addressAssembler = addressAssembler;
		this.orderAssembler = orderAssembler;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<GetOrderDTO> findById(@PathVariable Long id){
		GetOrderDTO orderDTO = this.orderAssembler.assembleEntityModel(this.orderService.findById(id));
		return ResponseEntity.ok().body(orderDTO);
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<GetOrderDTO> create(@Valid @RequestBody CreateOrderDTO orderDTO){
		Order order = orderDTO.toEntity();
		order.setCreatedAt(new Date());
		Order createdOrder = this.orderService.insert(order);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdOrder.getId()).toUri();
		GetOrderDTO createdOrderDTO = this.orderAssembler.assembleEntityModel(createdOrder);
		return ResponseEntity.created(uri).body(createdOrderDTO);
	}
	
	@RequestMapping(value = "/{id}/deliveryAdress")
	public  ResponseEntity<GetAddressDTO> findDeliveryAddressByOrderId(@PathVariable Long id){
		Address address = this.orderService.findDeliveryAdressByOrderId(id);
		GetAddressDTO addressDTO = this.addressAssembler.assembleEntityModelByOrderId(address, id);
		return ResponseEntity.ok().body(addressDTO);
	}
}
