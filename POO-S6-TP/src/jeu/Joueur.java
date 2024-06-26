package jeu;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

import cartes.Carte;
import cartes.Limite;
import cartes.Bataille;
import cartes.Borne;
import cartes.Botte;

public class Joueur {
	private String nom;
	private ZoneDeJeu zoneDeJeu;
	private IMain main;
	
	public Joueur(String nom) {
		this.nom = nom;
		this.zoneDeJeu = new ZoneDeJeu();
		this.main = new MainAsListe();
	}
	
	public String getNom() {
		return nom;
	}

	public String toString() {
		return nom;
	}
	
	public IMain getMain() {
		return main;
	}

	public boolean equals(Object obj) {
		if (obj != null && obj.getClass() == getClass()) {
			Joueur joueur = (Joueur) obj;
			return nom.equals(joueur.getNom());
		}
		return false;
	}
	
	public void donner(Carte carte) {
		main.prendre(carte);
		// Post-condition : La main contient la carte.
	}

	public Carte prendreCarte(List<Carte> sabot) {
		if (!sabot.isEmpty()) {
			Carte carte = sabot.remove(0);
			donner(carte);
			return carte;
		}
		return null;
	}

	public void deposer(Borne borne) {
		zoneDeJeu.deposer(borne);
	}

	public void deposer(Limite limite) {
		zoneDeJeu.deposer(limite);
	}

	public void deposer(Bataille bataille) {
		zoneDeJeu.deposer(bataille);
	}

	public void deposer(Botte botte) {
		zoneDeJeu.deposer(botte);
	}

	public int donnerKmParcourus() {
		return zoneDeJeu.donnerKmParcourus();
	}

	public int donnerLimitationVitesse() {
		return zoneDeJeu.donnerLimitationVitesse();
	}

	public boolean estBloque() {
		return zoneDeJeu.estBloque();
	}
	
	public Set<Coup> coupsPossibles(Set<Joueur> participants) {
		Set<Coup> coupsPossibles = new HashSet<>();
		Coup coup;
		
		for (Joueur participant : participants)
			for (Carte carte : main) {
				coup = new Coup(carte, participant);

				if (zoneDeJeu.estDepotAutorise(carte) && coup.estValide(this))
					coupsPossibles.add(coup);
			}
				
		return coupsPossibles;
	}

	public Set<Coup> coupsDefausse() {
		Set<Coup> coupsDefausse = new HashSet<>();

		for (Carte carte : main)
			coupsDefausse.add(new Coup(carte, null));

		return coupsDefausse;
	}

	public boolean deposer(Carte c) {
		return zoneDeJeu.deposer(c);
	}

	public void retirerDeLaMain(Carte carte) {
		main.jouer(carte);
	}

	public Coup choisirCoup(Set<Coup> coupsPossibles) {
		//Random temporairement ?
		Random random = new Random();
		Object[] coups = coupsPossibles.toArray();
		
		return (Coup) coups[random.nextInt(coupsPossibles.size())];
	}
}