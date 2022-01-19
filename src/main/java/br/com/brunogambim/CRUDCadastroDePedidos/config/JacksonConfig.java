package br.com.brunogambim.CRUDCadastroDePedidos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.brunogambim.CRUDCadastroDePedidos.dto.order.payment.CreateOrderBankSlipPaymentDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.order.payment.CreateOrderCardPaymentDTO;

@Configuration
public class JacksonConfig {
	
	@Bean
	public Jackson2ObjectMapperBuilder ObjectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(CreateOrderCardPaymentDTO.class);
				objectMapper.registerSubtypes(CreateOrderBankSlipPaymentDTO.class);
				super.configure(objectMapper);
			}
		};
		return builder;
	}
}
