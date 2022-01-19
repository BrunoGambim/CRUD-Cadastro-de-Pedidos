package br.com.brunogambim.CRUDCadastroDePedidos.enums;

public enum ClientType {
	NATURALPERSON(1, "Pessoa física"),
	LEGALPERSON(2, "Pessoa jurídica");

	private int code;
	private String description;
	
	private ClientType(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static ClientType toEnum(Integer code) {
		if(code == null) {
			return null;
		}
		return ClientType.toEnum((int) code);
	}
	
	public static ClientType toEnum(int code) {
		for(ClientType clientType : ClientType.values()) {
			if(code == clientType.getCode()) {
				return clientType;
			}
		}
		throw new IllegalArgumentException("Argumento inválido! Id:" + code);
	}
}
