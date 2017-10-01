package br.ucsal.bes20172.bd2.aula07.enums;

public enum SituacaoFuncionarioEnum {

	ATIVO("A"), DESLIGADO("D");

	private String codigo;

	private SituacaoFuncionarioEnum(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

}
