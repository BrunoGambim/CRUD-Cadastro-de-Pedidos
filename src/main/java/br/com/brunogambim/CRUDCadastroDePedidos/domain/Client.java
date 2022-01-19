package br.com.brunogambim.CRUDCadastroDePedidos.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.brunogambim.CRUDCadastroDePedidos.enums.ClientType;
import br.com.brunogambim.CRUDCadastroDePedidos.enums.Perfil;

@Entity
public class Client implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@Column(unique = true)
	private String email;
	
	@Column(unique = true)
	private String cpfOrCnpj;
	private Integer clientType;

	@JsonIgnore
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	private Set<Integer> perfis = new HashSet<>();
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "client_id")
	Set<Address> addresses = new HashSet<>();
	
	@ElementCollection
	@CollectionTable(name = "TELEPHONE")
	private Set<String> telephoneNumber = new HashSet<>();
	
	public Client () {
		addPerfil(Perfil.CLIENT);
	}

	public Client(Long id, String name, String email, String cpfOrCnpj, ClientType clientType, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpfOrCnpj = cpfOrCnpj;
		this.clientType = clientType == null ? null : clientType.getCode();
		this.password = password;
		addPerfil(Perfil.CLIENT);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOrCnpj() {
		return cpfOrCnpj;
	}

	public void setCpfOrCnpj(String cpfOrCnpj) {
		this.cpfOrCnpj = cpfOrCnpj;
	}

	public ClientType getClientType() {
		return ClientType.toEnum(this.clientType);
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType.getCode();
	}

	public Set<Address> getAddresses() {
		return new HashSet<>(addresses);
	}
	
	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public void addAddress(Address address) {
		this.addresses.add(address);
	}
	
	public void removeAddress(Address address) {
		this.addresses.remove(address);
	}
	
	public void removeAddressById(Long addressId) {
		Address address = new Address();
		address.setId(addressId);
		this.addresses.remove(address);
	}

	public Set<String> getTelephoneNumber() {
		return telephoneNumber;
	}
	
	public void addTelephoneNumber(String telephoneNumber) {
		if(telephoneNumber != null) {
			this.telephoneNumber.add(telephoneNumber);
		}
	}
	
	public void addAllTelephoneNumber(Set<String> telephoneNumber) {
		telephoneNumber.forEach((phoneNumber)->{
			this.addTelephoneNumber(phoneNumber);
		});
	}
	
	public void removeTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber.remove(telephoneNumber);
		
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<Perfil> getPerfis(){
		return this.perfis.stream().map(perfil -> Perfil.toEnum(perfil)).collect(Collectors.toSet());
	}
	
	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCode());
	}
	
	public void removePerfil(Perfil perfil) {
		this.perfis.remove(perfil.getCode());
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
		Client other = (Client) obj;
		return Objects.equals(id, other.id);
	}
}
