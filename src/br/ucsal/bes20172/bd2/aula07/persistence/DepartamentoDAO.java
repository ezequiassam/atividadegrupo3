package br.ucsal.bes20172.bd2.aula07.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ucsal.bes20172.bd2.aula07.domain.Departamento;
import br.ucsal.bes20172.bd2.aula07.domain.Funcionario;

public class DepartamentoDAO extends AbstractDAO {

	private static final String INSERT_SQL = "insert into departamento (cod, nome, id_gerente) values (?, ?, ?) returning cod";
	private static final String UPDATE_SQL = "update departamento set cod = ?, nome = ?, id_gerente = ? where cod = ?";
	private static final String DELETE_SQL = "delete from departamento where cod = ?";
	private static final String SELECT_BASE_SQL = "select cod, nome, id_gerente from departamento";

	public static List<Departamento> findAllUtilizandoTryResource() throws SQLException, ClassNotFoundException {
		List<Departamento> departamentos = new ArrayList<>();
		try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
			// Disparando a consulta
			ResultSet resultSet = statement.executeQuery(SELECT_BASE_SQL);

			// Sobre cada registro do conjunto resultado, eu posso tratar os
			// respectivos dados de cada tupla
			while (resultSet.next()) {
				Departamento departamento = convertResultSetToDepartamento(resultSet);
				departamentos.add(departamento);
			}
		}
		return departamentos;
	}

	public static Departamento findByCod(String cod) throws SQLException, ClassNotFoundException {
		Connection connection = getConnection();

		String query = SELECT_BASE_SQL + " where cod = ?";

		PreparedStatement preparedstatement = connection.prepareStatement(query);
		preparedstatement.setString(1, cod);
		ResultSet resultSet = preparedstatement.executeQuery();

		Departamento departamento = null;
		if (resultSet.next()) {
			departamento = convertResultSetToDepartamento(resultSet);
		}

		connection.close();

		return departamento;
	}

	private static Departamento convertResultSetToDepartamento(ResultSet resultSet)
			throws SQLException, ClassNotFoundException {
		// Atribuir � vari�veis locais os valores dos campos do registro atual
		String cod = resultSet.getString("cod");
		String nome = resultSet.getString("nome");
		Integer idGerente = resultSet.getInt("id_gerente");

		Funcionario gerente = FuncionarioDAO.findById(idGerente);
		Departamento departamento = new Departamento(cod, nome, gerente);
		return departamento;
	}

	public static Departamento insert(Departamento departamento) throws ClassNotFoundException, SQLException {
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_SQL)) {
			preparedStatement.setString(1, departamento.getCod());
			preparedStatement.setString(2, departamento.getNome());
			preparedStatement.setInt(3, departamento.getGerente().getId());
			preparedStatement.executeQuery();
		}
		return departamento;
	}

	public static Departamento update(Departamento departamento, String cod)
			throws ClassNotFoundException, SQLException {
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE_SQL)) {
			preparedStatement.setString(1, cod);
			preparedStatement.setString(2, departamento.getNome());
			preparedStatement.setInt(3, departamento.getGerente().getId());
			preparedStatement.setString(4, cod);
			preparedStatement.executeUpdate();
			departamento.setCod(cod);
		}
		return departamento;
	}

	public static Departamento update(Departamento departamento) throws ClassNotFoundException, SQLException {
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE_SQL)) {
			preparedStatement.setString(1, departamento.getCod());
			preparedStatement.setString(2, departamento.getNome());
			preparedStatement.setInt(3, departamento.getGerente().getId());
			preparedStatement.setString(4, departamento.getCod());
			preparedStatement.executeUpdate();
		}
		return departamento;
	}

	public static Boolean delete(Departamento departamento) throws ClassNotFoundException, SQLException {
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_SQL)) {
			preparedStatement.setString(1, departamento.getCod());
			return preparedStatement.execute();
		}
	}

	public static Boolean delete(Integer id) throws ClassNotFoundException, SQLException {
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_SQL)) {
			preparedStatement.setInt(1, id);
			return preparedStatement.execute();
		}
	}

	public static List<Departamento> findAll() throws SQLException, ClassNotFoundException {
		List<Departamento> departamentos = new ArrayList<>();

		PreparedStatement preparedStatement = getConnection().prepareStatement(SELECT_BASE_SQL);
		ResultSet resultSet = preparedStatement.executeQuery();

		// Sobre cada registro do conjunto resultado, eu posso tratar os
		// respectivos dados de cada tupla
		while (resultSet.next()) {
			Departamento departamento = convertResultSetToDepartamento(resultSet);
			departamentos.add(departamento);
		}
		return departamentos;
	}

//	public static Departamento findById(Integer id) throws SQLException, ClassNotFoundException {
//		String query = SELECT_BASE_SQL + " where cod = ?";
//
//		PreparedStatement preparedstatement = getConnection().prepareStatement(query);
//		preparedstatement.setInt(1, id);
//		ResultSet resultSet = preparedstatement.executeQuery();
//
//		Departamento departamento = null;
//		if (resultSet.next()) {
//			departamento = convertResultSetToDepartamento(resultSet);
//		}
//
//		return departamento;
//	}

	// Deste m�todo serve apenas para "brincar" com os m�todos do DAO
//	public static void main(String[] args) throws SQLException, ClassNotFoundException {
//		List<Departamento> departamentos = DepartamentoDAO.findAll();
//		System.out.println("Todos departamentos:");
//		for (Departamento departamento : departamentos) {
//			System.out.println(departamento);
//		}
//
//		System.out.println("\n\nDepartamento com c�digo VND:");
//		Departamento departamento = DepartamentoDAO.findByCod("VND");
//		System.out.println(departamento);
//	}

}
