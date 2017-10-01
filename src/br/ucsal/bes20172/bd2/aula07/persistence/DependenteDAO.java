package br.ucsal.bes20172.bd2.aula07.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ucsal.bes20172.bd2.aula07.domain.Dependente;
import br.ucsal.bes20172.bd2.aula07.domain.Funcionario;
import br.ucsal.bes20172.bd2.aula07.enums.GrauParentescoEnum;

public class DependenteDAO extends AbstractDAO {

	private static final String INSERT_SQL = "insert into dependente (id_funcionario,seq,nome,grau_parentesco) values (?,?,?,?) returning seq";
	private static final String UPDATE_SQL = "update dependente set id_funcionario = ?, seq = ?, nome = ?, grau_parentesco = ? where seq = ?";
	private static final String DELETE_SQL = "delete from dependente where seq = ?";
	private static final String SELECT_BASE_SQL = "select id_funcionario,seq,nome,grau_parentesco from dependente";

	public static Dependente insert(Dependente dependente) throws ClassNotFoundException, SQLException {
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_SQL)) {
			preparedStatement.setInt(1, dependente.getFuncionario().getId());
			preparedStatement.setInt(2, dependente.getSeq());
			preparedStatement.setString(3, dependente.getNome());
			preparedStatement.setString(4, dependente.getGrauParentesco().getGrau());
			preparedStatement.executeQuery();
		}
		return dependente;
	}

	public static Dependente update(Dependente dependente, Integer seqAtual)
			throws ClassNotFoundException, SQLException {
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE_SQL)) {
			preparedStatement.setInt(1, dependente.getFuncionario().getId());
			preparedStatement.setInt(2, dependente.getSeq());
			preparedStatement.setString(3, dependente.getNome());
			preparedStatement.setString(4, dependente.getGrauParentesco().getGrau());
			preparedStatement.setInt(5, seqAtual);
			preparedStatement.executeUpdate();
		}
		return dependente;
	}

	public static Dependente update(Dependente dependente) throws ClassNotFoundException, SQLException {
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE_SQL)) {
			preparedStatement.setInt(1, dependente.getFuncionario().getId());
			preparedStatement.setInt(2, dependente.getSeq());
			preparedStatement.setString(3, dependente.getNome());
			preparedStatement.setString(4, dependente.getGrauParentesco().getGrau());
			preparedStatement.setInt(5, dependente.getSeq());
			preparedStatement.executeUpdate();
		}
		return dependente;
	}

	public static Boolean delete(Dependente dependente) throws ClassNotFoundException, SQLException {
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_SQL)) {
			preparedStatement.setInt(1, dependente.getSeq());
			return preparedStatement.execute();
		}
	}

	public static Boolean delete(Integer id) throws ClassNotFoundException, SQLException {
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_SQL)) {
			preparedStatement.setInt(1, id);
			return preparedStatement.execute();
		}
	}

	public static List<Dependente> findAll() throws SQLException, ClassNotFoundException {
		List<Dependente> dependentes = new ArrayList<>();

		PreparedStatement preparedStatement = getConnection().prepareStatement(SELECT_BASE_SQL);
		ResultSet resultSet = preparedStatement.executeQuery();

		// Sobre cada registro do conjunto resultado, eu posso tratar os
		// respectivos dados de cada tupla
		while (resultSet.next()) {
			Dependente dependente = convertResultSetToDepentente(resultSet);
			dependentes.add(dependente);
		}
		return dependentes;
	}

	public static Dependente findById(Integer seq) throws SQLException, ClassNotFoundException {
		String query = SELECT_BASE_SQL + " where seq = ?";

		PreparedStatement preparedstatement = getConnection().prepareStatement(query);
		preparedstatement.setInt(1, seq);
		ResultSet resultSet = preparedstatement.executeQuery();

		Dependente dependente = null;
		if (resultSet.next()) {
			dependente = convertResultSetToDepentente(resultSet);
		}

		return dependente;
	}

	private static Dependente convertResultSetToDepentente(ResultSet resultSet)
			throws SQLException, ClassNotFoundException {
		Integer idFuncinario = resultSet.getInt("id_funcionario");
		Integer seq = resultSet.getInt("seq");
		String nome = resultSet.getString("nome");
		String grauParentesco = resultSet.getString("grau_parentesco");

		GrauParentescoEnum grau;
		switch (grauParentesco) {
		case "F":
			grau = GrauParentescoEnum.FILHO;
			break;
		case "C":
			grau = GrauParentescoEnum.CONJUGE;
			break;

		default:
			grau = GrauParentescoEnum.OUTROS;
			break;
		}
		Funcionario funcionario = FuncionarioDAO.findById(idFuncinario);
		Dependente dependente = new Dependente(funcionario, seq, nome, grau);
		return dependente;
	}
}
