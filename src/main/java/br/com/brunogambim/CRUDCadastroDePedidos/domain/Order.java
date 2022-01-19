package br.com.brunogambim.CRUDCadastroDePedidos.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name="Order_Table")
public class Order  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date createdAt;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address deliveryAddress;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Payment payment;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "order_id")
	private Set<OrderItem> items = new HashSet<>();
	
	public Order() {
	}

	public Order(Long id, Date createdAt, Client client, Address deliveryAddress) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.client = client;
		this.deliveryAddress = deliveryAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Set<OrderItem> getItems() {
		return new HashSet<>(items);
	}
	
	public void updateItem(OrderItem item) {
		this.items.remove(item);
		this.items.add(item);
	}

	public void addItem(OrderItem item) {
		this.items.add(item);
	}
	
	public void removeItem(OrderItem item) {
		this.items.remove(item);
	}
	
	public Double getTotal() {
		return items.stream().map(itemOrder -> itemOrder.getSubtotal()).reduce((subtotal, parcialSum) -> parcialSum + subtotal).get();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		StringBuilder builder = new StringBuilder();
		builder.append("Pedido número:")
		.append(getId())
		.append(", Criado em:")
		.append(simpleDateFormat.format(getCreatedAt()))
		.append(", Cliente:")
		.append(client.getName())
		.append(", Situação do pagamento:")
		.append(getPayment().getPaymentState().getDescription())
		.append("\nDetalhes:\n");
		items.forEach(item ->{
			builder.append(item);
		});
		builder.append("Valor Total:")
		.append(numberFormat.format(getTotal()));
		return builder.toString();
	}
	
	
}