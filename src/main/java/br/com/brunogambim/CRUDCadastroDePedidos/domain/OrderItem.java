package br.com.brunogambim.CRUDCadastroDePedidos.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OrderItem implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double discount;
	private Integer numberOfItens;
	
	@OneToOne
	private Product product;
	
	public OrderItem() {
	}
	
	public OrderItem(Product product, Double discount, Integer numberOfItens) {
		super();
		this.product = product;
		this.discount = discount;
		this.numberOfItens = numberOfItens;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Integer getNumberOfItens() {
		return numberOfItens;
	}

	public void setNumberOfItens(Integer numberOfItens) {
		this.numberOfItens = numberOfItens;
	}
	
	public Double getSubtotal() {
		return (getProduct().getPrice() - discount) * numberOfItens;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getProduct().getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		return Objects.equals(getProduct().getId(), other.getProduct().getId());
	}

	@Override
	public String toString() {
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
		StringBuilder builder = new StringBuilder();
		builder.append(getProduct().getName())
		.append(", Qte:")
		.append(getNumberOfItens())
		.append(", Preço unitário:")
		.append(numberFormat.format(getProduct().getPrice()))
		.append(", Subtotal:")
		.append(numberFormat.format(getSubtotal()))
		.append("\n");
		return builder.toString();
	}
}
