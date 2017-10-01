package br.ucsal.bes20172.bd2.aula07.tui;

import java.sql.SQLException;
import java.util.List;

import br.ucsal.bes20172.bd2.aula07.business.DepartamentoBO;
import br.ucsal.bes20172.bd2.aula07.domain.Departamento;

public class DepartamentoTui {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		exibirDepartamentos();
	}

	public static void exibirDepartamentos() throws ClassNotFoundException, SQLException {
		List<Departamento> departamentos = DepartamentoBO.recuperarDepartamentos();
		System.out.println("Departamentos:");
		for (Departamento departamento : departamentos) {
			System.out.println(departamento);
		}
	}

}
