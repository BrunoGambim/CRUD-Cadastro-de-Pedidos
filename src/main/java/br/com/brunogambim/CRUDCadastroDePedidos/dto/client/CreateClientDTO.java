package br.com.brunogambim.CRUDCadastroDePedidos.dto.client;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Client;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.address.CreateAddressDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.enums.ClientType;
import br.com.brunogambim.CRUDCadastroDePedidos.services.validation.ClientInsert;

@ClientInsert
public class CreateClientDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String name;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Email(message = "Email inválido")
	private String email;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String cpfOrCnpj;
	private Integer clientType;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String password;
	
	private CreateAddressDTO address;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private Set<String> telephones;
	
	public CreateClientDTO() {
	}
	
	public Client toEntity() {
		Client client = new Client(null, name, email, cpfOrCnpj, ClientType.toEnum(clientType), password);
		client.addAllTelephoneNumber(telephones);
		client.addAddress(address.toEntity());
		return client;
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

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CreateAddressDTO getAddress() {
		return address;
	}

	public void setAddress(CreateAddressDTO address) {
		this.address = address;
	}

	public Set<String> getTelephones() {
		return telephones;
	}

	public void setTelephones(Set<String> telephones) {
		this.telephones = telephones;
	}
}
