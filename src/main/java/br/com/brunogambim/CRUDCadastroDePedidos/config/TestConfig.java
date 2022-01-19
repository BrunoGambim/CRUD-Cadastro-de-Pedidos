package br.com.brunogambim.CRUDCadastroDePedidos.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.brunogambim.CRUDCadastroDePedidos.services.MockedDBService;
import br.com.brunogambim.CRUDCadastroDePedidos.services.EmailService;
import br.com.brunogambim.CRUDCadastroDePedidos.services.MockEmailService;
import br.com.brunogambim.CRUDCadastroDePedidos.services.utils.filestorage.FileStorageStrategy;
import br.com.brunogambim.CRUDCadastroDePedidos.services.utils.filestorage.dbstrategy.DBImageFileStorage;

@Configuration
@Profile("test")
public class TestConfig {
	
	MockedDBService dbService;
	
	@Autowired
	public TestConfig(MockedDBService dbService) {
	 	this.dbService = dbService;
	}
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
	
	@Bean
	public FileStorageStrategy fileStorageStrategy() {
		return new DBImageFileStorage();
	}
}
