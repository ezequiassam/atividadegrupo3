package br.ucsal.bes20172.bd2.aula07.business;

import java.sql.SQLException;
import java.util.List;

import br.ucsal.bes20172.bd2.aula07.domain.Departamento;
import br.ucsal.bes20172.bd2.aula07.persistence.DepartamentoDAO;

public class DepartamentoBO {

	public static List<Departamento> recuperarDepartamentos() throws ClassNotFoundException, SQLException {
		return DepartamentoDAO.findAll();
	}

}
