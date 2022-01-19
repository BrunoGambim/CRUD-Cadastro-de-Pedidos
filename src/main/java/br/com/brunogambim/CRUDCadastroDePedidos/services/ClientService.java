package br.com.brunogambim.CRUDCadastroDePedidos.services;

import java.net.URI;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Address;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Client;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.AuthorizationException;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.DataIntegrityException;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.ObjectNotFoundException;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.ClientRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.services.utils.filestorage.ImageLoader;

@Service
public class ClientService {
	
	private ClientRepository clientRepository;
	private AddressService addressService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private FileStorageService fileStorageService;
	
	@Value("${img.prefix.client.profile}")
	private String profileImgPrefix;
	@Value("${img.client.profile.size}")
	private int profileImgSize;
	
	@Autowired
	public ClientService(ClientRepository clientRepository, AddressService addressService,
			BCryptPasswordEncoder bCryptPasswordEncoder, FileStorageService fileStorageService) {
		this.clientRepository = clientRepository;
		this.addressService = addressService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.fileStorageService = fileStorageService;
	}
	
	public Client findById(Long id) {
		if(!UserService.isAuthorized(id)){
			throw new AuthorizationException();
		}
		return this.clientRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(id.toString(), Client.class));
	}
	
	public Client findByEmail(String email) {
		if(!UserService.isAuthorized(email)){
			throw new AuthorizationException();
		}
		return this.clientRepository.findByEmail(email).orElseThrow(
				() -> new ObjectNotFoundException(email, Client.class));
	}
	
	public List<Client> findAll() {
		return this.clientRepository.findAll();
	}
	
	public Client insert(Client client) {
		client.setId(null);
		client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
		client.setAddresses((new HashSet<>(addressService.insertAll(client.getAddresses()))));
		Client newClient = this.clientRepository.save(client);
		return newClient;
	}
	
	public Address insertAddressToClient(Long clientId, Address address) {
		if(!UserService.isAuthorized(clientId)){
			throw new AuthorizationException();
		}
		Client client = findById(clientId);
		Address newAddress = addressService.insert(address);
		client.addAddress(newAddress);
		this.clientRepository.save(client);
		return newAddress;
	}
	
	public Client update(Client newClientData) {
		Client oldClientData = findById(newClientData.getId());
		updateData(newClientData, oldClientData);
		return this.clientRepository.save(oldClientData);
	}

	private void updateData(Client newClientData, Client oldClientData) {
		oldClientData.setName(newClientData.getName());
		oldClientData.setEmail(newClientData.getEmail());
	}

	public void delete(Long id) {
		findById(id);
		try {
			this.clientRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("DELETE", Client.class);
		}
	}
	
	public void deleteAddressFromClient(Long clientId, Long addressId) {
		Client client = findById(clientId);
		if(!UserService.isAuthorized() && (!UserService.isAuthorized(clientId) || !verifyIfAddressBelongsToClient(client,addressId))){
			throw new AuthorizationException();
		}
		client.removeAddressById(addressId);
		this.clientRepository.save(client);
	}
	
	private Boolean verifyIfAddressBelongsToClient(Client client, Long addressId) {
		return client.getAddresses().stream().map(address -> address.getId()).toList().contains(addressId);
	}
	
	public URI uploadProfilePicture(Long id, MultipartFile multipartFile) {
		if(!UserService.isAuthorized(id)){
			throw new AuthorizationException();
		}
		ImageLoader imageLoader = ImageLoader.createImageLoader(multipartFile);
		String imageName = this.profileImgPrefix + id + ".jpg";
		return this.fileStorageService.uploadFile(imageLoader.getJpgImage(profileImgSize), imageName, multipartFile.getContentType());
	}
}
