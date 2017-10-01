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

	// -- 5 - O nome e endereço dos funcionários do departamento de Informática, por
	// ordem de nome.
	public static List<Funcionario> findFuncionarioOnInformatica() throws ClassNotFoundException, SQLException {
		List<Funcionario> funcionarios = new ArrayList<>();
		String sql = "SELECT funcionario.nome, funcionario.endereco FROM funcionario \n"
				+ "INNER JOIN departamento ON departamento.cod = funcionario.id_departamento\n"
				+ "WHERE departamento.nome = 'Informatica' ORDER BY funcionario.nome";

		PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			String endereco = resultSet.getString("endereco");
			String nome = resultSet.getString("nome");
			Funcionario func = new Funcionario();
			func.setEndereco(endereco);
			func.setNome(nome);
			funcionarios.add(func);
		}
		return funcionarios;

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

	// -- 8 - Os nomes dos departamentos e dos funcionários. Suas listagem devem
	// incluir departamentos sem funcionários e funcionários sem departamento.
	public static List<String> findDepartamentoAndFuncionario() throws SQLException, ClassNotFoundException {
		List<String> lista = new ArrayList<>();
		String sql = "	SELECT d.nome NOME_DEPARTAMENTO, f.nome FUNCIONARIO_DEPARTAMENTO\n"
				+ "  FROM funcionario f LEFT JOIN departamento d ON f.id_departamento = d.cod\n"
				+ "   	UNION SELECT  d.nome NOME_DEPARTAMENTO, f.nome FUNCIONARIO_DEPARTAMENTO\n"
				+ "	FROM funcionario f RIGHT JOIN departamento d ON f.id_departamento = d.cod";

		PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			String departamento = resultSet.getString("NOME_DEPARTAMENTO");
			String funcionario = resultSet.getString("FUNCIONARIO_DEPARTAMENTO");
			String text = "Departamento: " + departamento + ", Funcionario: " + funcionario;
			lista.add(text);
		}
		return lista;
	}

	// -- 13- v_funcionarios_ativos: código e nome do departamento, id, nome,
	// endereço, cpf e salário dos funcionários ativos.
	public static List<String> viewFuncionariosAtivos() throws SQLException, ClassNotFoundException {
		List<String> lista = new ArrayList<>();
		String sql = "select * from funcionarios_ativos";

		PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			String cod = resultSet.getString("cod");
			String departamento = resultSet.getString("nome_departamento");
			Integer id = resultSet.getInt("funcionario_id");
			String funcionario = resultSet.getString("funcionario_nome");
			String cpf = resultSet.getString("funcionario_cpf");
			String endereco = resultSet.getString("funcionario_endereco");
			Double salario = resultSet.getDouble("funcionario_salario");
			String text = "Cod Departamento:" + cod + ", Nome Departamento:" + departamento + ", Id Funcionario:" + id
					+ ", Nome:" + funcionario + ", CPF:" + cpf + ", Endereço:" + endereco + ", Salario:" + salario;
			lista.add(text);
		}
		return lista;
	}

}
