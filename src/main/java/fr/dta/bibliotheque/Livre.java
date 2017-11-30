package fr.dta.bibliotheque;

public class Livre{
	
	//Id de table livre
	private int id;
	
	//Titre de table livre
	private String titre;
	
	//Auteur de table livre
	private String auteur;

	public Livre(String titre, String auteur) {
		this.titre = titre;
		this.auteur = auteur;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public String getAuteur() {
		return auteur;
	}
	
}
