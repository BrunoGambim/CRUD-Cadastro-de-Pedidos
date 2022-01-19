package br.com.brunogambim.CRUDCadastroDePedidos.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Address;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.BankSlipPayment;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.CardPayment;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Category;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.City;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Client;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.OrderItem;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Order;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Payment;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Product;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.State;
import br.com.brunogambim.CRUDCadastroDePedidos.enums.ClientType;
import br.com.brunogambim.CRUDCadastroDePedidos.enums.PaymentState;
import br.com.brunogambim.CRUDCadastroDePedidos.enums.Perfil;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.AddressRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.CategoryRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.CityRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.ClientRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.OrderItemRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.OrderRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.PaymentRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.ProductRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.StateRepository;

@Service
public class MockedDBService {
	
	private CategoryRepository categoryRepository;
	private ProductRepository productRepository;
	private CityRepository cityRepository;
	private StateRepository stateRepository;
	private ClientRepository clientRepository; 
	private AddressRepository addressRepository;
	private PaymentRepository paymentRepository; 
	private OrderRepository orderRepository; 
	private OrderItemRepository itemOrderRepository; 
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public MockedDBService(CategoryRepository categoryRepository, ProductRepository productRepository, CityRepository cityRepository, StateRepository stateRepository,
			ClientRepository clientRepository, AddressRepository addressRepository, PaymentRepository paymentRepository, OrderRepository orderRepository,
			OrderItemRepository itemOrderRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.cityRepository = cityRepository;
		this.stateRepository = stateRepository;
		this.clientRepository = clientRepository;
		this.addressRepository = addressRepository;
		this.paymentRepository = paymentRepository;
		this.orderRepository = orderRepository;
		this.itemOrderRepository = itemOrderRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		
	}
	
	public void instantiateDatabase() throws ParseException {
		Category alimentos  = new Category(null, "Alimentos");
		Category bebidas = new Category(null, "Bebidas");
		Category automotivo = new Category(null, "Automotivo");
		Category eletronicos = new Category(null, "Eletrônicos");
		Category informatica = new Category(null, "Informatica");
		Category cozinha = new Category(null, "Cozinha");
		Category celulares = new Category(null, "Celulares");
		Category esportes = new Category(null, "Esportes");
		Category casa = new Category(null, "Casa");
		Category games = new Category(null, "Games");
		Category papelaria = new Category(null, "Papelaria");
		Category ferramentas = new Category(null, "Ferramentas");
		
		categoryRepository.saveAll(Arrays.asList(alimentos,bebidas,automotivo,eletronicos
				,informatica,cozinha,celulares,esportes,casa,games,papelaria,ferramentas));

		Product cafe = new Product(null, "Café",15.40D);
		cafe.addCategory(alimentos);
		
		Product pao = new Product(null, "Pão",10.19D);
		pao.addCategory(alimentos);
		
		Product molho = new Product(null, "Molho",3.45D);
		molho.addCategory(alimentos);
		
		Product achocolatado = new Product(null, "Achocolatado",13.99D);
		achocolatado.addCategory(alimentos);
		
		Product massa = new Product(null, "Massa",2.39D);
		massa.addCategory(alimentos);
		
		Product mel = new Product(null, "Mel",20.20D);
		mel.addCategory(alimentos);
		
		Product refri = new Product(null, "Refrigerante",5.40D);
		refri.addCategory(alimentos);
		refri.addCategory(bebidas);
		
		Product agua = new Product(null, "Água mineral",4.10D);
		agua.addCategory(alimentos);
		agua.addCategory(bebidas);
		
		Product leite = new Product(null, "Leite",4.60D);
		leite.addCategory(alimentos);
		leite.addCategory(bebidas);
		
		Product pneu = new Product(null, "Pneu",214.60D);
		pneu.addCategory(automotivo);
		
		Product som = new Product(null, "Som",54.60D);
		som.addCategory(automotivo);
		
		Product galao = new Product(null, "Galão de gasolina",23.60D);
		galao.addCategory(automotivo);
		galao.addCategory(ferramentas);
		
		Product tomada = new Product(null, "Tomada",4.60D);
		tomada.addCategory(eletronicos);
		
		Product lampada = new Product(null, "Lampada",9.65D);
		lampada.addCategory(eletronicos);
		
		Product notebook = new Product(null, "Notebook",3248.39D);
		notebook.addCategory(eletronicos);
		notebook.addCategory(informatica);
		notebook.addCategory(casa);
		
		Product desktop = new Product(null, "Computador de mesa",2599.99D);
		desktop.addCategory(eletronicos);
		desktop.addCategory(informatica);
		desktop.addCategory(casa);
		
		Product tv = new Product(null, "Televisão",1289.79D);
		tv.addCategory(eletronicos);
		tv.addCategory(casa);
		
		Product teclado = new Product(null, "Teclado",249.95D);
		teclado.addCategory(eletronicos);
		teclado.addCategory(informatica);
		
		Product fogao = new Product(null, "Fogão",549.29D);
		fogao.addCategory(cozinha);
		fogao.addCategory(casa);
		
		Product smartfone = new Product(null, "Smartfone",899.89D);
		smartfone.addCategory(eletronicos);
		smartfone.addCategory(celulares);
		
		Product caneta = new Product(null, "Caneta",0.59D);
		caneta.addCategory(papelaria);
		
		Product papel = new Product(null, "Papel",11.79D);
		papel.addCategory(papelaria);
		
		Product impressora = new Product(null, "Impressora",224.95D);
		impressora.addCategory(eletronicos);
		impressora.addCategory(informatica);
		impressora.addCategory(papelaria);
		impressora.addCategory(casa);
		
		productRepository.saveAll(Arrays.asList(cafe,pao,molho,achocolatado,massa,mel,refri,
				agua,leite,pneu,som,galao,tomada,lampada,notebook,desktop,tv,teclado,fogao,smartfone,caneta,papel,impressora));
		
		State RS = new State(null, "Rio Grande do Sul");
		State SP = new State(null, "São Paulo");
		State RJ = new State(null, "Rio de Janeiro");
		State SC = new State(null, "Santa Catarina");
		State CE = new State(null, "Ceará");
		
		City poa = new City(null,"Porto Alegre",RS);
		City caxias = new City(null,"Caxias",RS);
		City pelotas = new City(null,"Pelotas",RS);
		City SPO = new City(null,"São Paulo",SP);
		City jundiai = new City(null,"Jundiaí",SP);
		City campinas = new City(null,"Campinas",SP);
		City floripa = new City(null,"Florianópolis",SC);
		City niteroi = new City(null,"Niterói",SC);
		City fortaleza = new City(null,"Fortaleza",CE);
		
		stateRepository.saveAll(Arrays.asList(RS,SP,RJ,SC,CE));
		cityRepository.saveAll(Arrays.asList(poa,caxias,pelotas,SPO,jundiai,campinas,floripa,niteroi,fortaleza));
		
		Client joao = new Client(null,"Joao Silva","j.silva@mail.com","59260016096",ClientType.NATURALPERSON,bCryptPasswordEncoder.encode("123456"));
		joao.getTelephoneNumber().addAll(Arrays.asList("5552999999999","5553888888888"));
		
		Client manuela = new Client(null,"Manoela Costa","manuc@mail.com","16912297099",ClientType.NATURALPERSON,bCryptPasswordEncoder.encode("123456"));
		manuela.getTelephoneNumber().addAll(Arrays.asList("5551777777777","5551666666666"));
		
		Client empresaA = new Client(null,"empresaA","empresaA@mail.com","16125297000151",ClientType.LEGALPERSON,bCryptPasswordEncoder.encode("123456"));
		empresaA.getTelephoneNumber().add("5557111111111");
		
		Client admin = new Client(null,"admin","admin@mail.com","28240670000102",ClientType.LEGALPERSON,bCryptPasswordEncoder.encode("123456"));
		admin.addPerfil(Perfil.ADMIN);
		admin.getTelephoneNumber().add("5556000000000");
		
		clientRepository.saveAll(Arrays.asList(joao,manuela,empresaA,admin));
		
		Address address  = new Address(null, "Rua ABC", "403", "apt. 101", "Bairro 37", "12345-678", poa);
		Address address2 = new Address(null, "Rua DEF", "37", null, "Bairro 61", "23456-789", floripa);
		Address address3 = new Address(null, "Rua GHI", "43", "apt. 203", "Bairro A", "34567-890", fortaleza);
		Address address4 = new Address(null, "Rua JKL", "3", "complemento bla bla bla", "Bairro 3E", "45678-901", SPO);
		Address address5 = new Address(null, "Rua MNO", "502", "apt. 103", "Bairro AAA", "56789-012", SPO);
		Address address6 = new Address(null, "Rua PQR", "444", "apt. 609", "Bairro 66", "67890-123", niteroi);
		Address address7 = new Address(null, "Rua STU", "88", "apt. 902", "Bairro BB", "78901-234", pelotas);
		
		addressRepository.saveAll(Arrays.asList(address,address2,address3,address4,address5,address6,address7));
		
		joao.addAddress(address);
		joao.addAddress(address2);
		manuela.addAddress(address3);
		empresaA.addAddress(address4);
		empresaA.addAddress(address5);
		empresaA.addAddress(address6);
		admin.addAddress(address7);
		
		clientRepository.saveAll(Arrays.asList(joao,manuela,empresaA,admin));
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Order order  = new Order(null, simpleDateFormat.parse("01/01/2019 00:01"), joao, address);
		Order order2 = new Order(null, simpleDateFormat.parse("03/03/2020 10:30"), joao, address2);
		
		Order order3 = new Order(null, simpleDateFormat.parse("06/04/2021 00:01"), manuela, address3);
		Order order4 = new Order(null, simpleDateFormat.parse("12/11/2021 10:30"), manuela, address3);
		Order order5 = new Order(null, simpleDateFormat.parse("22/07/2020 10:30"), manuela, address3);
		
		Order order6 = new Order(null, simpleDateFormat.parse("01/01/2018 10:30"), empresaA, address5);
		
		Payment payment = new CardPayment(null, PaymentState.FINISHED, 2);
		order.setPayment(payment);
		
		Payment payment2 = new BankSlipPayment(null, PaymentState.FINISHED, simpleDateFormat.parse("06/03/2020 10:30"), null);
		order2.setPayment(payment2);
		
		Payment payment3 = new CardPayment(null, PaymentState.PENDING, 5);
		order3.setPayment(payment3);
		
		Payment payment4 = new CardPayment(null, PaymentState.CANCELED, 1);
		order4.setPayment(payment4);
		
		Payment payment5 = new BankSlipPayment(null, PaymentState.FINISHED, simpleDateFormat.parse("25/07/2020 10:30"), null);
		order5.setPayment(payment5);
		
		Payment payment6 = new BankSlipPayment(null, PaymentState.CANCELED, simpleDateFormat.parse("04/01/2018 10:30"), null);
		order6.setPayment(payment6);
		
		OrderItem itemOrder00 = new OrderItem(caneta, 0.05D, 20); 
		OrderItem itemOrder02 = new OrderItem(papel, 0D, 2);
		OrderItem itemOrder03 = new OrderItem(impressora, 30.85D, 1);
		
		OrderItem itemOrder20 = new OrderItem(desktop, 200D, 1); 
		OrderItem itemOrder22 = new OrderItem(teclado, 10.50D, 1);
		
		OrderItem itemOrder30 = new OrderItem(notebook, 0D, 1); 
		
		OrderItem itemOrder40 = new OrderItem(pneu, 17.40D, 4);
		
		OrderItem itemOrder50 = new OrderItem(fogao, 37.49D, 1); 
		
		OrderItem itemOrder60 = new OrderItem(pao, 2D, 2); 
		OrderItem itemOrder62 = new OrderItem(agua, 0.50D, 6);
		OrderItem itemOrder63 = new OrderItem(molho, 0D, 3);
		OrderItem itemOrder64 = new OrderItem(mel, 2.99D, 2); 
		OrderItem itemOrder65 = new OrderItem(massa, 0D, 7);
		OrderItem itemOrder66 = new OrderItem(refri, 0.10D, 3);
		
		order.addItem(itemOrder00);
		order.addItem(itemOrder02);
		order.addItem(itemOrder03);
		order2.addItem(itemOrder20);
		order2.addItem(itemOrder22);
		order3.addItem(itemOrder30);
		order4.addItem(itemOrder40);
		order5.addItem(itemOrder50);
		order6.addItem(itemOrder60);
		order6.addItem(itemOrder62);
		order6.addItem(itemOrder63);
		order6.addItem(itemOrder64);
		order6.addItem(itemOrder65);
		order6.addItem(itemOrder66);
		
		orderRepository.saveAll(Arrays.asList(order,order2,order3,order4,order5,order6));
		paymentRepository.saveAll(Arrays.asList(payment,payment2,payment3,payment4,payment5,payment6));
		itemOrderRepository.saveAll(Arrays.asList(itemOrder00,itemOrder02,itemOrder03,itemOrder20,itemOrder22,itemOrder30,itemOrder40,
				itemOrder50,itemOrder60,itemOrder62,itemOrder63,itemOrder64,itemOrder65,itemOrder66));
	}
}
