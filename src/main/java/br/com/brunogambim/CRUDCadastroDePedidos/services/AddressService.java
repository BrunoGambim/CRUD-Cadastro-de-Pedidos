package br.com.brunogambim.CRUDCadastroDePedidos.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Address;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Client;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.ObjectNotFoundException;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.AddressRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.ClientRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.security.UserSS;

@Service
public class AddressService {
	
	private AddressRepository addressRepository;
	private ClientRepository clientRepository;
	private CityService cityService;
	
	@Autowired
	public AddressService(AddressRepository addressRepository, ClientRepository clientRepository,
			CityService cityService) {
		this.addressRepository = addressRepository;
		this.clientRepository = clientRepository;
		this.cityService = cityService;
	}
	
	public Collection<Address> insertAll(Collection<Address> addressCollection) {
		addressCollection.forEach(address -> address.setId(null));
		return this.addressRepository.saveAll(addressCollection);
	}

	public Address insert(Address address) {
		address.setId(null);
		address.setCity(this.cityService.findById(address.getCity().getId()));
		return this.addressRepository.save(address);
	}

	public boolean canBeDeleted(Long id) {
		Optional<UserSS>  user = UserService.authenticated();
		Client client =  this.clientRepository.findByAddressesId(id).orElseThrow(() -> new ObjectNotFoundException("Address id " + id.toString(), Address.class));
		return user.get().getId().equals(client.getId());
	}
}
