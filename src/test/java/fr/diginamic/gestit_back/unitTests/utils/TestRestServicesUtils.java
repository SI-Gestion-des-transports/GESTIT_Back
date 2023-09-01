package fr.diginamic.gestit_back.unitTests.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.gestit_back.entites.Adresse;
import fr.diginamic.gestit_back.entites.Commune;
import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Marque;
import fr.diginamic.gestit_back.entites.Modele;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculePerso;

public class TestRestServicesUtils {
    public static Covoiturage Covoiturage_instanceExample() {
		Covoiturage covoiturage = new Covoiturage();
		Commune commune = new Commune("Paris", 75000);
		Adresse adresseDepart = new Adresse(26, "rue des Alouettes", commune);
		Adresse adresseArrivee = new Adresse(32, "Bvd des Aubépines", commune);

		Utilisateur conducteur = new Utilisateur();
		LocalDate dateConducteur = LocalDate.of(2022, 4, 6);
		List<Covoiturage> conducteurCovoituragesOrganises = new ArrayList<>();
		List<Covoiturage> conducteurCovoituragesPassagers = new ArrayList<>();
		List<String> roleConducteur = new ArrayList<>();
		roleConducteur.add("COLLABORATEUR");
		conducteur.setEmail("RonaldMerziner@gmail.com");
		conducteur.setMotDePasse("4321");
		conducteur.setNom("Merziner");
		conducteur.setCovoituragesOrganises(conducteurCovoituragesOrganises);
		conducteur.setCovoituragesPassagers(conducteurCovoituragesPassagers);
		conducteur.setDateNonValide(dateConducteur);
		conducteur.setRoles(roleConducteur);

		Utilisateur passager = new Utilisateur();
		LocalDate datePassager = LocalDate.of(2020, 1, 8);
		List<Covoiturage> covoituragesOrganises = new ArrayList<>();
		List<Covoiturage> covoituragesPassagers = new ArrayList<>();
		List<String> rolePassager = new ArrayList<>();
		rolePassager.add("COLLABORATEUR");
		passager.setEmail("RonaldMerziner@gmail.com");
		passager.setMotDePasse("4321");
		passager.setNom("Merziner");
		passager.setCovoituragesOrganises(covoituragesOrganises);
		passager.setCovoituragesPassagers(covoituragesPassagers);
		passager.setDateNonValide(datePassager);
		passager.setRoles(rolePassager);
		List<Utilisateur> passagersABord = new ArrayList<>();

		/* A CORRIGER : CET APPEL POSE PROBLEME */
		// passagersABord.add(conducteur);

		VehiculePerso vehiculeConducteur = new VehiculePerso();
		vehiculeConducteur.setImmatriculation("789-hu-78");
		Modele modele = new Modele();
		Marque marque = new Marque();
		marque.setNom("Fiat");
		modele.setNom("mini500");
		modele.setMarque(marque);
		vehiculeConducteur.setModele(modele);
		vehiculeConducteur.setNombreDePlaceDisponibles(4);
		vehiculeConducteur.setProprietaire(conducteur);

		covoiturage.setDistanceKm(15);
		covoiturage.setDureeTrajet(30);
		covoiturage.setNombrePlacesRestantes(4);
		covoiturage.setAdresseDepart(adresseDepart);
		covoiturage.setAdresseArrivee(adresseArrivee);
		covoiturage.setOrganisateur(conducteur);
		covoiturage.setId(23);
		covoiturage.setVehiculePerso(vehiculeConducteur);
		covoiturage.setPassagers(passagersABord);

		return covoiturage;
	}
    
}
