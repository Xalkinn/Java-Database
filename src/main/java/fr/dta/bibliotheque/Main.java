package fr.dta.bibliotheque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	static Connection conn = null;

	public static void main(String[] args) {

		String url = "jdbc:postgresql://localhost:5432/bibliotheque";

		try {
			conn = DriverManager.getConnection(url, "postgres", "postgres");

			Statement stmt = conn.createStatement();

			/*
			 * La création du DROP ici est pour évitez de remplir plein de fois la BDD. En
			 * effet, avant de lancer le programme il va clean la BDD avant afin d'avoir une
			 * BDD propre
			 */

			// Création de DROP
			stmt.executeUpdate("DROP TABLE IF EXISTS livre CASCADE;");
			stmt.executeUpdate("DROP TABLE IF EXISTS client CASCADE;");
			stmt.executeUpdate("DROP TABLE IF EXISTS achat;");

			// Création de la table Livre
			stmt.executeUpdate("CREATE TABLE livre(id BIGSERIAL PRIMARY KEY, " + "titre varchar(255) NOT NULL, "
					+ "auteur varchar(255))");

			// Création de la table Client
			stmt.executeUpdate("CREATE TABLE client(id BIGSERIAL PRIMARY KEY, " + "nom varchar(255) NOT NULL, "
					+ "prenom varchar(255) NOT NULL, " + "genre varchar(255) NOT NULL, "
					+ "livre_prefere BIGINT, FOREIGN KEY(livre_prefere) REFERENCES livre(id));");

			// Création de la table Achat
			stmt.executeUpdate("CREATE TABLE achat(id_client BIGINT, FOREIGN KEY (id_client) REFERENCES client (id), "
					+ "id_livre BIGINT, FOREIGN KEY (id_livre) REFERENCES livre (id));");

			/*
			 * Explication de la commande : livre_prefere bigint, FOREIGN KEY
			 * (livre_prefere) REFERENCES livre(id)
			 * 
			 * livre prefere bigint =/= bigserial
			 * FOREIGN KEY (livre_prefere) => Clé étrangère pour la table client
			 * REFERENCES livre(id) => Reference a l'id de la
			 * table livre afin de faire la liaison entre id livre et client
			 * 
			 */

			// Remplissage de la table livre avec 8 exemples
			stmt.executeUpdate("INSERT INTO livre(titre, auteur) VALUES "
					+ "('Star Wars I : La menace fantome', 'Georges Lucas'),\r\n"
					+ "('Star Wars II : L''attaque des clones', 'Georges Lucas'),\r\n"
					+ "('Star Wars III : La revanche des sith', 'Georges Lucas'),\r\n"
					+ "('Star Wars IV : Un nouvel espoir', 'Georges Lucas'),\r\n"
					+ "('Star Wars V : L''empire contre attaque', 'Georges Lucas'),\r\n"
					+ "('Star Wars VI : Le retour du jedi', 'Georges Lucas'),\r\n"
					+ "('Star Wars VII : Le reveil de la force', 'J.J. Abrams'),\r\n"
					+ "('Star Wars VIII : Les derniers jedi', 'Rian Johnson');");

			// Remplissage de la table Client avec clients
			stmt.executeUpdate("INSERT INTO client(nom, prenom, genre) VALUES "
					+ "('Tisserand', 'Julien', 'Homme'),\r\n" + "('Joseph', 'Caleb', 'Homme'),\r\n"
					+ "('Tan', 'Julie', 'Femme'),\r\n" + "('Allemand', 'Guillaume', 'Homme'),\r\n"
					+ "('Aupetit', 'Sapho', 'Femme'),\r\n" + "('Bonnet', 'Arnaud', 'Homme'),\r\n"
					+ "('Ho Chuis', 'Jérome', 'Homme'),\r\n" + "('Juzan', 'Samuel', 'Homme'),\r\n"
					+ "('Nedjari', 'Moussa', 'Homme'),\r\n" + "('Sidi Said Omar', 'Hamida', 'Femme'),\r\n"
					+ "('Thual', 'Jérémy', 'Homme'),\r\n" + "('Guyard', 'Pierre', 'Homme');");

			// Remplissage de la table achat qui nous fera le lien entre client et livre
			stmt.executeUpdate("INSERT INTO achat(id_client, id_livre) VALUES "
					+ "((SELECT id FROM client WHERE nom LIKE 'Tisserand'), (SELECT id FROM livre where titre LIKE 'Star Wars I %' ));");

			Livre livre = new Livre("Star Wars I : La menace fantome", "Georges Lucas");
			livre.setId(1);
			getClient(livre);

			// Fermetture des requettes et commit a la base de donnée
			stmt.close();
			conn.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void getClient(Livre titre) throws SQLException {

		PreparedStatement pstmt = conn.prepareStatement(
				"SELECT * FROM client\r\n" + "JOIN achat ON client.id = achat.id_client\r\n" + "WHERE id_livre = ?;");

		pstmt.setInt(1, titre.getId());

		ResultSet resultat = pstmt.executeQuery();

		while (resultat.next()) {
			System.out.println(resultat.getString("prenom") + ", " + resultat.getString("nom"));
		}
	}
}
