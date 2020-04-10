package br.com.villsec.model.entities.enums;

public enum TipoElemento {

	TIPO_1(1, ""),
	TIPO_2(2, ""),
	TIPO_3(3, ""),
	TIPO_4(4, ""),
	TIPO_5(5, ""),
	TIPO_6(6, "");
	
	private int codigo;
	private String descricao;
	
	private TipoElemento(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoElemento toEnum(Integer codigo) {
		
		if (codigo == null) {
			return null;
		}
		
		for (TipoElemento x : TipoElemento.values()) {
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + codigo);
	}	
}
