package br.com.villsec.model.entities.enums;

public enum TipoEvento {

	ADULTO(1, "Adulto +18"), INFANTIL(2, "Infantil"), LGBTQ(3, "LGBTQ+"), LIVE(4, "Live"), PUBLICO(5,
			"Público"), RESTRITO(6, "Restrito à Convidados"), TERCEIRA_IDADE(7, "Terceira Idade"), VIP(8, "VIP");

	private int codigo;
	private String descricao;

	private TipoEvento(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoEvento toEnum(Integer codigo) {

		if (codigo == null) {
			return null;
		}

		for (TipoEvento x : TipoEvento.values()) {
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + codigo);
	}
}
