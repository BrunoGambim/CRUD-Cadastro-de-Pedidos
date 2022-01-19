package br.com.brunogambim.CRUDCadastroDePedidos.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Address;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.BankSlipPayment;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.OrderItem;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Order;
import br.com.brunogambim.CRUDCadastroDePedidos.enums.PaymentState;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.AuthorizationException;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.ObjectNotFoundException;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.OrderRepository;

@Service
public class OrderService {
	private OrderRepository orderRepository;
	private PaymentService paymentService;
	private OrderItemService orderItemService;
	private ProductService productService;
	private ClientService clientService;
	private EmailService emailService;
	private BankSlipService bankSlipService;
	
	@Autowired
	public OrderService(OrderRepository orderRepository, ProductService productService, BankSlipService bankSlipService,
			PaymentService paymentService, OrderItemService orderItemService,ClientService clientService, EmailService emailService) {
		this.orderRepository = orderRepository;
		this.productService = productService;
		this.bankSlipService = bankSlipService;
		this.paymentService = paymentService;
		this.orderItemService = orderItemService;
		this.clientService = clientService;
		this.emailService = emailService;
	}
	
	public Order findById(Long id) {
		return this.orderRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(id.toString(),Order.class));
	}
	
	public Address findDeliveryAdressByOrderId(Long id) {
		return findById(id).getDeliveryAddress();
	}
	
	public Page<Order> findPage(Long id, Integer page, Integer size, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		if(!UserService.isAuthorized(id)){
			throw new AuthorizationException();
		}
		return this.orderRepository.findByClient(clientService.findById(id),pageRequest);
	}

	public Order insert(Order order) {
		order.setId(null);
		order.setCreatedAt(new Date());
		order.getPayment().setPaymentState(PaymentState.PENDING);
		order.setClient(clientService.findById(order.getClient().getId()));
		if (order.getPayment() instanceof BankSlipPayment) {
			BankSlipPayment bankSlipPayment = (BankSlipPayment) order.getPayment();
			bankSlipService.fillBankSlipPayment(bankSlipPayment, order.getCreatedAt());
		}	
		
		paymentService.insert(order.getPayment());

		
		for(OrderItem item : order.getItems()){
			item.setProduct(productService.findById(item.getProduct().getId()));
			order.updateItem(item);
		}
		orderItemService.insertAll(order.getItems());
		
		order = orderRepository.save(order);
		
		emailService.sendOrderConfirmationEmail(order);
		return order;
	}
	
	
}
