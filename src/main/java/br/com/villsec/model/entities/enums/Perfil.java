package br.com.villsec.model.entities.enums;

public enum Perfil {
	
	ADMINISTRADOR(1, "Administrador", "ROLE_ADMINISTRADOR"),
	PROPRIETARIO(2, "Academia", "ROLE_ACADEMIA"),
	SEGUIDOR(3, "Aluno(a)", "ROLE_ALUNO"),
	TESTE(4, "Teste", "ROLE_TESTE");
	
	private int codigo;
	private String descricao;
	private String role;
	
	private Perfil(int codigo, String descricao, String role) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.role = role;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public String getRole() {
		return role;
	}

	public static Perfil toEnum(Integer codigo) {
		
		if (codigo == null) {
			return null;
		}
		
		for (Perfil x : Perfil.values()) {
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + codigo);
	}	
}
