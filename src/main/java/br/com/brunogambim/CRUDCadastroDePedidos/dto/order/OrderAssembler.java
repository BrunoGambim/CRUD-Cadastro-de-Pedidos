package br.com.brunogambim.CRUDCadastroDePedidos.dto.order;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Order;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.order.orderitem.GetOrderItemDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.order.orderitem.OrderItemAssembler;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.order.payment.GetPaymentDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.order.payment.PaymentAssembler;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.utils.PaginationUtils;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.utils.URL;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.v1.ClientResource;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.v1.OrderResource;

@Component
public class OrderAssembler extends RepresentationModelAssemblerSupport<Order, GetOrderDTO> {

	private PagedResourcesAssembler<Order> pagedResourcesAssembler;
	private PaymentAssembler paymentAssembler;
	private OrderItemAssembler orderItemAssembler;
	
	@Autowired
	public OrderAssembler(PagedResourcesAssembler<Order> pagedResourcesAssembler, PaymentAssembler paymentAssembler,
			OrderItemAssembler orderItemAssembler) {
		super(OrderResource.class, GetOrderDTO.class);
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.paymentAssembler = paymentAssembler;
		this.orderItemAssembler = orderItemAssembler;
	}

	@Override
	public GetOrderDTO toModel(Order order) {
		Set<GetOrderItemDTO> items = this.orderItemAssembler.assembleCollection(order.getItems());
		GetPaymentDTO payment = this.paymentAssembler.toModel(order.getPayment());
		GetOrderDTO orderDTO = new GetOrderDTO(order.getId(), order.getCreatedAt(),
				payment, items, order.getTotal());
		addEntitySelfReference(orderDTO);
		return orderDTO;
	}
	
	public CollectionModel<GetOrderDTO> assembleCollectionModel(Collection<Order> orderCollection) {
		CollectionModel<GetOrderDTO> orderDTOCollection = CollectionModel.of(orderCollection.stream().map(order -> toModel(order)).toList());
		addCollectionSelfReference(orderDTOCollection);
		addCollectionCreateReference(orderDTOCollection);
		return orderDTOCollection;
	}
	
	public PagedModel<GetOrderDTO> assemblePagedModelByClientId(Page<Order> orderpage, Long id) {
		Integer page = orderpage.getNumber();
		Integer size = orderpage.getSize();
		String sort[] = PaginationUtils.sortToURIParam(orderpage.getSort());
		PagedModel<GetOrderDTO> orderDTOList = this.pagedResourcesAssembler.toModel(orderpage,this,
			Link.of(URL.decodeParam(linkTo(methodOn(ClientResource.class).findPage(id,page, size, sort)).toString())).withSelfRel());
		addCollectionCreateReference(orderDTOList);
		return orderDTOList;
	}
	
	public GetOrderDTO assembleEntityModel(Order order) {
		GetOrderDTO orderDTO = toModel(order);
		addEntityAddressReference(orderDTO);
		addEntityClientReference(orderDTO,order.getClient().getId());
		return orderDTO;
	}

	private void addCollectionSelfReference(CollectionModel<GetOrderDTO> orderCollection){
		orderCollection.add(linkTo(OrderResource.class).withSelfRel());
	}
	
	private void addCollectionCreateReference(CollectionModel<GetOrderDTO> orderCollection){
		orderCollection.add(linkTo(methodOn(OrderResource.class).create(null)).withRel("create"));
	}
	
	private void addEntityAddressReference(GetOrderDTO orderDTO) {
		orderDTO.add(linkTo(methodOn(OrderResource.class).findDeliveryAddressByOrderId(orderDTO.getId())).withRel("address"));
	}
	
	private void addEntityClientReference(GetOrderDTO orderDTO, Long clientId) {
		orderDTO.add(linkTo(methodOn(ClientResource.class).findById(clientId)).withRel("client"));
	}
	
	private void addEntitySelfReference(GetOrderDTO orderDTO) {
		orderDTO.add(linkTo(methodOn(OrderResource.class).findById(orderDTO.getId())).withSelfRel());
	}
}
