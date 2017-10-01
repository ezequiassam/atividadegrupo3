package br.ucsal.bes20172.bd2.aula07.enums;

public enum GrauParentescoEnum {
	FILHO("F"), CONJUGE("C"), OUTROS("O");

	String grau;

	private GrauParentescoEnum(String grau) {
		this.grau = grau;
	}

	public String getGrau() {
		return grau;
	}

}
