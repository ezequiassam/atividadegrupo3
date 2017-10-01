package br.ucsal.bes20172.bd2.aula07.domain;

import br.ucsal.bes20172.bd2.aula07.enums.GrauParentescoEnum;

public class Dependente {

	private Funcionario funcionario;
	private Integer seq;
	private String nome;
	private GrauParentescoEnum grauParentesco;

	public Dependente(Funcionario funcionario, Integer seq, String nome, GrauParentescoEnum grauParentesco) {
		super();
		this.funcionario = funcionario;
		this.seq = seq;
		this.nome = nome;
		this.grauParentesco = grauParentesco;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public GrauParentescoEnum getGrauParentesco() {
		return grauParentesco;
	}

	public void setGrauParentesco(GrauParentescoEnum grauParentesco) {
		this.grauParentesco = grauParentesco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcionario == null) ? 0 : funcionario.hashCode());
		result = prime * result + ((grauParentesco == null) ? 0 : grauParentesco.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((seq == null) ? 0 : seq.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dependente other = (Dependente) obj;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
			return false;
		if (grauParentesco != other.grauParentesco)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (seq == null) {
			if (other.seq != null)
				return false;
		} else if (!seq.equals(other.seq))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Dependente [funcionario=" + funcionario + ", seq=" + seq + ", nome=" + nome + ", grauParentesco="
				+ grauParentesco + "]";
	}

}
