package fr.dta.bibliotheque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
	public static void main(String[] args) {

		String url = "jdbc:postgresql://localhost:5432/bibliotheque";

		try (Connection conn = DriverManager.getConnection(url, "postgres", "postgres");

				Statement stmt = conn.createStatement()) {

			stmt.executeUpdate("CREATE TABLE livre(id bigserial PRIMARY KEY, " + "titre varchar(255) NOT NULL, " + "auteur varchar(255))");
			stmt.executeUpdate("CREATE TABLE client(id bigserial PRIMARY KEY, " + "nom varchar(255) NOT NULL, " + "prenom varchar(255) NOT NULL, " + "genre varchar(255))");
			stmt.close();
			conn.close();

			// stmt.executeUpdate("INSERT INTO book(title, author) VALUES('What''s New in
			// Java 8', 'Adam L. Davis')");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
