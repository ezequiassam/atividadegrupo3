package br.ucsal.bes20172.bd2.aula07.tui;

import java.sql.SQLException;
import java.util.List;

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
		nextLine();
		System.out.print("Operações de CRUD BASICO:");
		nextLine();
		Departamento departamento = DepartamentoDAO.findByCod("INF");
		Funcionario f1 = new Funcionario(0, "Gustavo", "79562", "Rua k", SituacaoFuncionarioEnum.ATIVO, 1.000d,
				departamento);
		Dependente dd1 = new Dependente(FuncionarioDAO.findById(27), 4, "Teresa", GrauParentescoEnum.CONJUGE);
		Departamento dp1 = new Departamento("JRU", "Juridico", FuncionarioDAO.findById(26));

		nextLine();

		System.out.println("[insert] Insert do Funcinario:");
		Funcionario funcInserido = FuncionarioDAO.insert(f1);
		System.out.println(funcInserido);

		nextLine();

		System.out.println("[findAll] Lista de Funcinarios:");
		for (Funcionario func : FuncionarioDAO.findAll()) {
			System.out.println(func);
		}

		nextLine();

		System.out.println("[findById] Lista do Funcinario com id = 15:");
		System.out.println(FuncionarioDAO.findById(15));

		nextLine();

		System.out.println("[update] Atualização do Funcinario:");
		System.out.println(FuncionarioDAO.update(funcInserido));

		nextLine();

		System.out.println("[delete] Deletar o Funcinario:");
		System.out.println(FuncionarioDAO.delete(FuncionarioDAO.findById(0)));

		nextLine();

		System.out.println("[insert] Insert do Dependente:");
		Dependente depandInserido = DependenteDAO.insert(dd1);
		System.out.println(depandInserido);

		nextLine();

		System.out.println("[findAll] Lista de Dependentes:");
		for (Dependente index : DependenteDAO.findAll()) {
			System.out.println(index);
		}

		nextLine();

		System.out.println("[findById] Lista do Dependente com id = 1:");
		System.out.println(DependenteDAO.findById(1));

		nextLine();

		System.out.println("[update] Atualização do Dependente:");
		System.out.println(DependenteDAO.update(depandInserido));

		nextLine();

		System.out.println("[delete] Deletar o Dependente:");
		System.out.println(DependenteDAO.delete(DependenteDAO.findById(4)));

		nextLine();

		System.out.println("[insert] Insert do Departamento:");
		Departamento depInserido = DepartamentoDAO.insert(dp1);
		System.out.println(depInserido);

		nextLine();

		System.out.println("[findAll] Lista de Departamento:");
		for (Departamento index : DepartamentoDAO.findAll()) {
			System.out.println(index);
		}

		nextLine();

		System.out.println("[findById] Lista do Departamento com cod = INF:");
		System.out.println(DepartamentoDAO.findByCod("INF"));

		nextLine();

		System.out.println("[update] Atualização do Departamento:");
		System.out.println(DepartamentoDAO.update(depInserido));

		nextLine();

		System.out.println("[delete] Deletar o Departamento:");
		System.out.println(DepartamentoDAO.delete(DepartamentoDAO.findByCod("CNT")));

		nextLine();

		System.out.println("[findFuncionarioOnInformatica] Lista de Funcionarios do departamento de Informática:");
		for (Funcionario index : FuncionarioDAO.findFuncionarioOnInformatica()) {
			System.out.println(index);
		}

		nextLine();

		System.out.println("[findOnFuncionario] Lista de Derpatamentos e seus Funcionarios:");
		listPrint(DepartamentoDAO.findOnFuncionario());

		nextLine();

		System.out.println("[findCaracteristcFuncionario] Lista de Derpatamentos  e suas características salariais:");
		listPrint(DepartamentoDAO.findCaracteristcFuncionario());

		nextLine();

		System.out.println("[findDepartamentoAndFuncionario] Lista de Derpatamentose dos funcionários:");
		listPrint(FuncionarioDAO.findDepartamentoAndFuncionario());

		nextLine();

		System.out.println("[findDepatamentoSalarioMil] Lista de Derpatamentose salario superior à 10000:");
		listPrint(DepartamentoDAO.findDepatamentoSalarioMil());

		nextLine();

		System.out.println("[viewDependentes] View dos Funcionarios seus dependentes:");
		listPrint(DependenteDAO.viewDependentes());

	}

	private static void listPrint(List<String> list) throws SQLException, ClassNotFoundException {
		for (String index : list) {
			System.out.println(index);
		}
	}

	private static void nextLine() {
		System.out.printf("\n********************************************************************\n");
	}
}
