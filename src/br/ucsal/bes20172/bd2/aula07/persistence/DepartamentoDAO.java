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

	// -- 6 - A lista de departamentos (código e nome) e seus respectivos
	// funcionários (nome), ordenado por nome de departamento e nome do funcionário.
	public static List<String> findOnFuncionario() throws SQLException, ClassNotFoundException {
		List<String> lista = new ArrayList<>();
		String sql = "SELECT cod, coalesce(f.nome, 'Nenhum funcionário alocado neste departamento') nome\n"
				+ "FROM departamento d LEFT JOIN funcionario f ON f.id_departamento = d.cod\n"
				+ "	ORDER BY d.nome, f.nome;";

		PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			String cod = resultSet.getString("cod");
			String nome = resultSet.getString("nome");
			lista.add("Departamento: " + cod + ", Funcionario: " + nome);
		}
		return lista;
	}

	// -- 7 - Os departamentos (código e nome) e suas respectivas características
	// salariais: maior salário, menor salário e salário médio.
	public static List<String> findCaracteristcFuncionario() throws SQLException, ClassNotFoundException {
		List<String> lista = new ArrayList<>();
		String sql = "select d.cod codigo, d.nome, max(f.salario) maior, min(f.salario) menor, avg(f.salario) medio\n"
				+ "from departamento d inner join funcionario f on f.id_departamento = d.cod WHERE f.situacao='A'\n"
				+ "group by d.cod, d.nome";

		PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			String cod = resultSet.getString("codigo");
			String nome = resultSet.getString("nome");
			String maior = resultSet.getString("maior");
			String menor = resultSet.getString("menor");
			String medio = resultSet.getString("medio");
			String text = "Codigo: " + cod + ", Departamento: " + nome + ", Maior Salario: " + maior
					+ ", Menor Salario: " + menor + ", Salario Medio: " + medio;
			lista.add(text);
		}
		return lista;
	}

	// -- 10- Os departamentos (código e nome) cuja média salarial seja superior à
	// 10000.
	public static List<String> findDepatamentoSalarioMil() throws SQLException, ClassNotFoundException {
		List<String> lista = new ArrayList<>();
		String sql = "select d.cod, d.nome from departamento d\n"
				+ "	inner join  funcionario f on f.id_departamento = d.cod GROUP BY d.cod, d.nome \n"
				+ "    HAVING avg (f.salario) > 10000";

		PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			String cod = resultSet.getString("cod");
			String nome = resultSet.getString("nome");
			String text = "Codigo: " + cod + ", Departamento: " + nome;
			lista.add(text);
		}
		return lista;
	}

	// public static Departamento findById(Integer id) throws SQLException,
	// ClassNotFoundException {
	// String query = SELECT_BASE_SQL + " where cod = ?";
	//
	// PreparedStatement preparedstatement =
	// getConnection().prepareStatement(query);
	// preparedstatement.setInt(1, id);
	// ResultSet resultSet = preparedstatement.executeQuery();
	//
	// Departamento departamento = null;
	// if (resultSet.next()) {
	// departamento = convertResultSetToDepartamento(resultSet);
	// }
	//
	// return departamento;
	// }

	// Deste m�todo serve apenas para "brincar" com os m�todos do DAO
	// public static void main(String[] args) throws SQLException,
	// ClassNotFoundException {
	// List<Departamento> departamentos = DepartamentoDAO.findAll();
	// System.out.println("Todos departamentos:");
	// for (Departamento departamento : departamentos) {
	// System.out.println(departamento);
	// }
	//
	// System.out.println("\n\nDepartamento com c�digo VND:");
	// Departamento departamento = DepartamentoDAO.findByCod("VND");
	// System.out.println(departamento);
	// }

}
