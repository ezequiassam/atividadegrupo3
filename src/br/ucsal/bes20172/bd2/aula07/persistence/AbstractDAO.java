package br.ucsal.bes20172.bd2.aula07.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractDAO {

	private static final String URL = "jdbc:postgresql://localhost:5432/aula3";
	private static final String USER = "postgres";
	private static final String PASSWORD = "postgres";

	protected static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
		return connection;
	}
}
