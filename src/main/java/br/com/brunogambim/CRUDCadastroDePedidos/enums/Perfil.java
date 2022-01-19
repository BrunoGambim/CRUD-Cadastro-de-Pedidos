package br.com.brunogambim.CRUDCadastroDePedidos.enums;

public enum Perfil {
	CLIENT(1, "ROLER_CLIENT"),
	ADMIN(2, "ROLE_ADMIN");

	private int code;
	private String description;
	
	private Perfil(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static Perfil toEnum(Integer code) {
		if(code == null) {
			return null;
		}
		return Perfil.toEnum((int) code);
	}
	
	public static Perfil toEnum(int code) {
		for(Perfil clientType : Perfil.values()) {
			if(code == clientType.getCode()) {
				return clientType;
			}
		}
		throw new IllegalArgumentException("Argumento inválido! Id:" + code);
	}
}
