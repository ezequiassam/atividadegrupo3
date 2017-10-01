package br.ucsal.bes20172.bd2.aula07.tui;

import java.sql.SQLException;

import br.ucsal.bes20172.bd2.aula07.domain.Departamento;
import br.ucsal.bes20172.bd2.aula07.domain.Dependente;
import br.ucsal.bes20172.bd2.aula07.domain.Funcionario;
import br.ucsal.bes20172.bd2.aula07.enums.GrauParentescoEnum;
import br.ucsal.bes20172.bd2.aula07.enums.SituacaoFuncionarioEnum;
import br.ucsal.bes20172.bd2.aula07.persistence.DepartamentoDAO;
import br.ucsal.bes20172.bd2.aula07.persistence.DependenteDAO;
import br.ucsal.bes20172.bd2.aula07.persistence.FuncionarioDAO;

public class MainTui {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		System.out.println("Operações de CRUD BASICO:");
		Departamento departamento = DepartamentoDAO.findByCod("INF");
		Funcionario f1 = new Funcionario(0, "Gustavo", "79562", "Rua k", SituacaoFuncionarioEnum.ATIVO, 1.000d,
				departamento);
		Dependente dd1 = new Dependente(FuncionarioDAO.findById(27), 4, "Teresa", GrauParentescoEnum.CONJUGE);
		Departamento dp1 = new Departamento("JRU", "Juridico", FuncionarioDAO.findById(26));

		System.out.println("[insert] Insert do Funcinario:");
		Funcionario funcInserido = FuncionarioDAO.insert(f1);
		System.out.println(funcInserido);

		System.out.println("[findAll] Lista de Funcinarios:");
		for (Funcionario func : FuncionarioDAO.findAll()) {
			System.out.println(func);
		}

		System.out.println("[findById] Lista do Funcinario com id = 15:");
		System.out.println(FuncionarioDAO.findById(15));

		System.out.println("[update] Atualização do Funcinario:");
		System.out.println(FuncionarioDAO.update(funcInserido));

		System.out.println("[delete] Deletar o Funcinario:");
		System.out.println(FuncionarioDAO.delete(FuncionarioDAO.findById(0)));

		System.out.println("[insert] Insert do Dependente:");
		Dependente depandInserido = DependenteDAO.insert(dd1);
		System.out.println(depandInserido);

		System.out.println("[findAll] Lista de Dependentes:");
		for (Dependente index : DependenteDAO.findAll()) {
			System.out.println(index);
		}

		System.out.println("[findById] Lista do Dependente com id = 1:");
		System.out.println(DependenteDAO.findById(1));

		System.out.println("[update] Atualização do Dependente:");
		System.out.println(DependenteDAO.update(depandInserido));

		System.out.println("[delete] Deletar o Dependente:");
		System.out.println(DependenteDAO.delete(DependenteDAO.findById(4)));

		System.out.println("[insert] Insert do Departamento:");
		Departamento depInserido = DepartamentoDAO.insert(dp1);
		System.out.println(depInserido);

		System.out.println("[findAll] Lista de Departamento:");
		for (Departamento index : DepartamentoDAO.findAll()) {
			System.out.println(index);
		}

		System.out.println("[findById] Lista do Departamento com cod = INF:");
		System.out.println(DepartamentoDAO.findByCod("INF"));

		System.out.println("[update] Atualização do Departamento:");
		System.out.println(DepartamentoDAO.update(depInserido));

		System.out.println("[delete] Deletar o Departamento:");
		System.out.println(DepartamentoDAO.delete(DepartamentoDAO.findByCod("CNT")));

	}
}
