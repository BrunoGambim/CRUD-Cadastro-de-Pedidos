package br.com.brunogambim.CRUDCadastroDePedidos.enums;

public enum PaymentState {
	PENDING(1,"Pendente"),
	FINISHED(2,"Finalizado"),
	CANCELED(3,"Cancelado");
	
	private int code;
	private String description;
	
	private PaymentState(int code,String description) {
		this.code = code;
		this.description = description;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getDescription() {
		return description;
	}

	public static PaymentState toEnum(Integer code) {
		if(code == null) {
			return null;
		}
		return PaymentState.toEnum((int) code);
	}
	
	public static PaymentState toEnum(int code) {
		for(PaymentState PaymentState : PaymentState.values()) {
			if(code == PaymentState.getCode()) {
				return PaymentState;
			}
		}
		throw new IllegalArgumentException("Argumento inválido! Id:" + code);
	}
}
