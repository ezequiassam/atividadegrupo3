package br.ucsal.bes20172.bd2.aula07.domain;

import br.ucsal.bes20172.bd2.aula07.enums.SituacaoFuncionarioEnum;

public class Funcionario {

	private Integer id;

	private String nome;

	private String cpf;

	private String endereco;

	private SituacaoFuncionarioEnum situacao;

	private Double salario;

	private Departamento departamento;

	public Funcionario(Integer id, String nome, String cpf, String endereco, SituacaoFuncionarioEnum situacao,
			Double salario, Departamento departamento) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
		this.situacao = situacao;
		this.salario = salario;
		this.departamento = departamento;
	}

	public Funcionario() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public SituacaoFuncionarioEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoFuncionarioEnum situacao) {
		this.situacao = situacao;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((departamento == null) ? 0 : departamento.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((salario == null) ? 0 : salario.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
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
		Funcionario other = (Funcionario) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (departamento == null) {
			if (other.departamento != null)
				return false;
		} else if (!departamento.equals(other.departamento))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (salario == null) {
			if (other.salario != null)
				return false;
		} else if (!salario.equals(other.salario))
			return false;
		if (situacao != other.situacao)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", endereco=" + endereco + ", situacao="
				+ situacao + ", salario=" + salario + ", departamento=" + departamento + "]";
	}

}
