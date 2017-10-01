package br.ucsal.bes20172.bd2.aula07.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ucsal.bes20172.bd2.aula07.domain.Departamento;
import br.ucsal.bes20172.bd2.aula07.domain.Funcionario;
import br.ucsal.bes20172.bd2.aula07.enums.SituacaoFuncionarioEnum;

public class FuncionarioDAO extends AbstractDAO {

	private static final String INSERT_SQL = "insert into funcionario (nome, cpf, endereco, situacao, salario, id_departamento) values (?,?,?,?,?,?) returning id";
	private static final String UPDATE_SQL = "update funcionario set id = ?, nome = ?, cpf = ?, endereco = ?, situacao = ?, salario = ?, id_departamento = ? where id = ?";
	private static final String DELETE_SQL = "delete from funcionario where id = ?";
	private static final String SELECT_BASE_SQL = "select id, nome, cpf, endereco, situacao, salario, id_departamento from funcionario";

	public static Funcionario insert(Funcionario funcionario) throws ClassNotFoundException, SQLException {
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_SQL)) {
			preparedStatement.setString(1, funcionario.getNome());
			preparedStatement.setString(2, funcionario.getCpf());
			preparedStatement.setString(3, funcionario.getEndereco());
			preparedStatement.setString(4, funcionario.getSituacao().getCodigo());
			preparedStatement.setDouble(5, funcionario.getSalario());
			preparedStatement.setString(6, funcionario.getDepartamento().getCod());
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			Integer id = resultSet.getInt(1);
			funcionario.setId(id);
		}
		return funcionario;
	}

	public static Funcionario update(Funcionario funcionario, Integer idAtual)
			throws ClassNotFoundException, SQLException {
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE_SQL)) {
			preparedStatement.setInt(1, funcionario.getId());
			preparedStatement.setString(2, funcionario.getNome());
			preparedStatement.setString(3, funcionario.getCpf());
			preparedStatement.setString(4, funcionario.getEndereco());
			preparedStatement.setString(5, funcionario.getSituacao().getCodigo());
			preparedStatement.setDouble(6, funcionario.getSalario());
			preparedStatement.setString(7, funcionario.getDepartamento().getCod());
			preparedStatement.setInt(8, idAtual);
			preparedStatement.executeUpdate();
			funcionario.setId(idAtual);
		}
		return funcionario;
	}

	public static Funcionario update(Funcionario funcionario) throws ClassNotFoundException, SQLException {
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE_SQL)) {
			preparedStatement.setInt(1, funcionario.getId());
			preparedStatement.setString(2, funcionario.getNome());
			preparedStatement.setString(3, funcionario.getCpf());
			preparedStatement.setString(4, funcionario.getEndereco());
			preparedStatement.setString(5, funcionario.getSituacao().getCodigo());
			preparedStatement.setDouble(6, funcionario.getSalario());
			preparedStatement.setString(7, funcionario.getDepartamento().getCod());
			preparedStatement.setInt(8, funcionario.getId());
			preparedStatement.executeUpdate();
		}
		return funcionario;
	}

	public static Boolean delete(Funcionario funcionario) throws ClassNotFoundException, SQLException {
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_SQL)) {
			preparedStatement.setInt(1, funcionario.getId());
			return preparedStatement.execute();
		}
	}

	public static Boolean delete(Integer id) throws ClassNotFoundException, SQLException {
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_SQL)) {
			preparedStatement.setInt(1, id);
			return preparedStatement.execute();
		}
	}

	public static List<Funcionario> findAll() throws SQLException, ClassNotFoundException {
		List<Funcionario> funcionarios = new ArrayList<>();

		PreparedStatement preparedStatement = getConnection().prepareStatement(SELECT_BASE_SQL);
		ResultSet resultSet = preparedStatement.executeQuery();

		// Sobre cada registro do conjunto resultado, eu posso tratar os
		// respectivos dados de cada tupla
		while (resultSet.next()) {
			Funcionario func = convertResultSetToFuncionario(resultSet);
			funcionarios.add(func);
		}
		return funcionarios;
	}

	public static Funcionario findById(Integer id) throws SQLException, ClassNotFoundException {
		String query = SELECT_BASE_SQL + " where id = ?";

		PreparedStatement preparedstatement = getConnection().prepareStatement(query);
		preparedstatement.setInt(1, id);
		ResultSet resultSet = preparedstatement.executeQuery();

		Funcionario funcionario = null;
		if (resultSet.next()) {
			funcionario = convertResultSetToFuncionario(resultSet);
		}

		return funcionario;
	}

	private static Funcionario convertResultSetToFuncionario(ResultSet resultSet)
			throws SQLException, ClassNotFoundException {
		Integer id = resultSet.getInt("id");
		String nome = resultSet.getString("nome");
		String cpf = resultSet.getString("cpf");
		String endereco = resultSet.getString("endereco");
		String situacao = resultSet.getString("situacao");
		Double salario = resultSet.getDouble("salario");
		String cod = resultSet.getString("id_departamento");

		SituacaoFuncionarioEnum enumeracao = (situacao == "A") ? SituacaoFuncionarioEnum.ATIVO
				: SituacaoFuncionarioEnum.DESLIGADO;
		Departamento departamento = DepartamentoDAO.findByCod(cod);
		Funcionario funcionario = new Funcionario(id, nome, cpf, endereco, enumeracao, salario, departamento);
		return funcionario;
	}

	// Deste m�todo serve apenas para "brincar" com os m�todos do DAO
//	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		Funcionario funcionario = new Funcionario(null, "manuela neiva", "abcd1235", "rua x",
//				SituacaoFuncionarioEnum.ATIVO, 35000d, new Departamento("CTB", "Inform�tica", null));
//		FuncionarioDAO.insert(funcionario);
//		System.out.println(funcionario);
//	}

}