package fr.dta.bibliotheque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
	public static void main(String[] args) {

		String url = "jdbc:postgresql://localhost:5432/bibliotheque";

		try (Connection conn = DriverManager.getConnection(url, "postgres", "postgres");

				Statement stmt = conn.createStatement()) {
			
			//Création de la table Livre
			stmt.executeUpdate("CREATE TABLE livre(id bigserial PRIMARY KEY, " + "titre varchar(255) NOT NULL, " + "auteur varchar(255))");
			
			//Création de la table Client
			stmt.executeUpdate("CREATE TABLE client(id bigserial PRIMARY KEY, " + "nom varchar(255) NOT NULL, " + "prenom varchar(255) NOT NULL, " + "genre varchar(255) NOT NULL)");
			
			//Remplissage de la table livre avec 8 exemples
			stmt.executeUpdate("INSERT INTO livre(titre, auteur) VALUES "+ 
					"('Star Wars I : La menace fantome', 'Georges Lucas'),\r\n" + 
					"('Star Wars II : L''attaque des clones', 'Georges Lucas'),\r\n" + 
					"('Star Wars III : La revanche des sith', 'Georges Lucas'),\r\n" + 
					"('Star Wars IV : Un nouvel espoir', 'Georges Lucas'),\r\n" + 
					"('Star Wars V : L''empire contre attaque', 'Georges Lucas'),\r\n" + 
					"('Star Wars VI : Le retour du jedi', 'Georges Lucas'),\r\n" + 
					"('Star Wars VII : Le reveil de la force', 'J.J. Abrams'),\r\n" + 
					"('Star Wars VIII : Les derniers jedi', 'Rian Johnson');");
			
			//Remplissage de la table Client avec 4 clients
			stmt.executeUpdate("INSERT INTO client(nom, prenom, genre) VALUES "+
					"('Tisserand', 'Julien', 'Homme'),\r\n" +
					"('Joseph', 'Caleb', 'Homme'),\r\n" +
					"('Tan', 'Julie', 'Femme'),\r\n" +
					"('Guyard', 'Pierre', 'Homme');");
					
			//Fermetture des requettes et commit a la base de donnée
			stmt.close();
			conn.close();
						

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
