package br.com.villsec.model.entities.enums;

public enum DiaDaSemana {

	DOMINGO(1, "Domingo"),
	SEGUNDA_FEIRA(2, "Segunda-Feira"),
	TERCA_FEIRA(3, "Terça-Feira"),
	QUARTA_FEIRA(4, "Quarta-Feira"),
	QUINTA_FEIRA(5, "Quinta-Feira"),
	SEXTA_FEIRA(6, "Sexta_Feira"),
	SABADO(7, "Sábado");
	
	
	private int codigo;
	private String descricao;
	
	private DiaDaSemana(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static DiaDaSemana toEnum(Integer codigo) {
		
		if (codigo == null) {
			return null;
		}
		
		for (DiaDaSemana x : DiaDaSemana.values()) {
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + codigo);
	}	
}
