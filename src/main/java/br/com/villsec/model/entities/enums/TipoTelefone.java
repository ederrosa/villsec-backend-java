package br.com.villsec.model.entities.enums;

public enum TipoTelefone {

	CELULAR(1, "Celular"),
	CORPORATIVO(2, "Corporativo"),
	RECADO(3, "Recado"),
	RESIDENCIAL(4, "Residêncial"),
	SAC(5, "SAC"),
	WHATSAPP(6, "Whatsapp");
	
	private int codigo;
	private String descricao;
	
	private TipoTelefone(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoTelefone toEnum(Integer codigo) {
		
		if (codigo == null) {
			return null;
		}
		
		for (TipoTelefone x : TipoTelefone.values()) {
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + codigo);
	}	
}
