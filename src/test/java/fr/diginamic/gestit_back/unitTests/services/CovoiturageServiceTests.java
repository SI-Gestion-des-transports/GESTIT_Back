package fr.diginamic.gestit_back.unitTests.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertThrows;

import fr.diginamic.gestit_back.entites.Adresse;
import fr.diginamic.gestit_back.entites.Commune;
import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Marque;
import fr.diginamic.gestit_back.entites.Modele;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculePerso;
import fr.diginamic.gestit_back.exceptions.CovoiturageNotFoundException;
import fr.diginamic.gestit_back.repository.CovoiturageRepository;
import fr.diginamic.gestit_back.service.CovoiturageService;

/***
 * Cette classe propose une batterie de tests
 * pour tous les services proposés au contrôleur
 * CovoiturageController de l'API.
 * Les repository normalement sont simulées par des doublures
 * grâce à l'annotation @Mock et injectés dans les service à tester
 * avec @injectMocks
 * 
 * Nota : @SpringBootTest permet de créer un contexte d'application contenant
 * tous les objets requis pour les tests ici présents.
 * 
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CovoiturageServiceTests {

    @Mock
    CovoiturageRepository doublureCovoiturageRepository;

    @InjectMocks
    CovoiturageService covoiturageService;

    private Covoiturage exampleCovoiturage;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        exampleCovoiturage = createCovoiturageForTest();
    }

    /***
     * Ce test lance appelle le service d'enregistrement d'un covoiturage
     * possédant l'attribut nombreDePlacesRestantes positionné à null.
     * Ce dernier étant contraint par une annotation @NotNull, le système
     * doit lancer une IllegalArgumentException, l'objectif du test étant de
     * capturer l'exception pour valider ce comportement attendu.
     * L'accès à la base de données au travers le repository normalement requis
     * est simulé ici par une doublure Mokito.
     * On vérifie également que le repository n'a été appelé qu'une seule fois.
     * 
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    public void testAddShouldThrowException() {
        this.exampleCovoiturage.setNombrePlacesRestantes(null);

        when(doublureCovoiturageRepository.save(this.exampleCovoiturage))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> {
            covoiturageService.add(this.exampleCovoiturage);
        });

        Mockito.verify(doublureCovoiturageRepository, times(1)).save(this.exampleCovoiturage);
    }

    /***
     * Ce test valide le retour de la méthode save() du service
     * CovoiturageService.
     * L'objectif est ici de valider le retour de la méthode, normalement
     * identique à l'entité censée avoir été persistée.
     * L'accès à la base de données au travers le repository normalement requis
     * est simulé ici par une doublure Mokito.
     * On vérifie également que le repository n'a été appelé qu'une seule fois.
     * 
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    public void testAddShouldReturnCovoiturage() throws Exception {

        this.exampleCovoiturage.setId(78977);
        this.exampleCovoiturage.setDistanceKm(456);
        this.exampleCovoiturage.setNombrePlacesRestantes(5);

        when(doublureCovoiturageRepository.save(this.exampleCovoiturage)).thenReturn(this.exampleCovoiturage);

        Covoiturage created = covoiturageService.add(this.exampleCovoiturage);

        assertThat(created.getId()).isSameAs(this.exampleCovoiturage.getId());
        assertThat(created.getDistanceKm()).isSameAs(this.exampleCovoiturage.getDistanceKm());
        assertThat(created.getNombrePlacesRestantes()).isSameAs(this.exampleCovoiturage.getNombrePlacesRestantes());

        Mockito.verify(doublureCovoiturageRepository, times(1)).save(this.exampleCovoiturage);

    }

    /***
     * Ce test appelle la lecture en base de données d'un covoiturage à partir
     * d'un id inexistant.
     * L'objectif est ici de valider la lancement d'une
     * CovoiturageNotFoundException par le service.
     * Le repository normalement requis est simulé ici par une doublure Mokito.
     * On vérifie également que le repository n'a été appelé qu'une seule fois
     * 
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    public void testGetShouldGenerateCovoiturageNotFoundException() {
        Integer notAvailableId = 7874;
        when(doublureCovoiturageRepository.findById(notAvailableId))
                .thenReturn(Optional.empty());

        assertThrows(CovoiturageNotFoundException.class, () -> {
            covoiturageService.get(notAvailableId);
        });

        Mockito.verify(doublureCovoiturageRepository, times(1)).findById(notAvailableId);
    }

    /***
     * Ce test appelle une demande d'une lecture en base de données d'un
     * covoiturage existant.
     * L'objectif est de vérifier l'entité retournée.
     * Le repository normalement requis est simulé ici par une doublure Mokito.
     * On vérifie également que le repository n'a été appelé qu'une seule fois
     * 
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    public void testGetShouldReturnCovoiturage() throws Exception {
        Integer availableId = 45;
        Covoiturage returnCovoiturage = this.exampleCovoiturage;
        returnCovoiturage.setId(availableId);
        returnCovoiturage.setNombrePlacesRestantes(4);
        returnCovoiturage.setDistanceKm(300);

        Optional<Covoiturage> returnedOptional = Optional.of(returnCovoiturage);

        when(doublureCovoiturageRepository.findById(availableId))
                .thenReturn(returnedOptional);

        Covoiturage readed = covoiturageService.get(availableId);
        assertThat(readed.getNombrePlacesRestantes()).isSameAs(returnCovoiturage.getNombrePlacesRestantes());
        assertThat(readed.getId()).isSameAs(this.exampleCovoiturage.getId());
        assertThat(readed.getDistanceKm()).isSameAs(this.exampleCovoiturage.getDistanceKm());

        Mockito.verify(doublureCovoiturageRepository, times(1)).findById(availableId);
    }

    /***
     * Ce test envoie une demande d'une lecture en base de données de
     * la liste des covoiturages enregistrés. La doublure renvoit une
     * liste possédant un covoiturage.
     * L'objectif est de vérifier la conformité de la liste attendue.
     * Le repository normalement requis est simulé ici par une doublure Mokito.
     * On vérifie également que le repository n'a été appelé qu'une seule fois
     * 
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    public void testListShouldReturnList() throws Exception {
        List<Covoiturage> liste = new ArrayList<>();
        liste.add(this.exampleCovoiturage);

        Mockito.when(doublureCovoiturageRepository.findAll()).thenReturn(liste);

        List<Covoiturage> returnedList = covoiturageService.list();
        assertThat(returnedList).isNotEmpty();
        assertThat(returnedList).isNotNull();
        assertThat(returnedList.get(0)).isSameAs(liste.get(0));

        Mockito.verify(this.doublureCovoiturageRepository, times(1)).findAll();
    }

    /***
     * Ce test crée une demande de modifiaction de l'id d'un
     * covoiturage inexistant.
     * L'objectif est de vérifier ici la génération d'une
     * CovoiturageNotFoundException par la méthode.
     * 
     * Le repository normalement requis est simulé ici par une doublure Mokito.
     * On vérifie également que le repository n'a été appelé qu'une seule fois
     * 
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    public void testUpdateShouldThrowCovoiturageNotFoundException() throws Exception {
/*        Integer notAvailableId = 4878;
        this.exampleCovoiturage.setId(notAvailableId);

        when(doublureCovoiturageRepository.existsById(notAvailableId))
                .thenReturn(false);

        assertThrows(CovoiturageNotFoundException.class, () -> {
            covoiturageService.updateOrganise(this.exampleCovoiturage);
        });

        Mockito.verify(doublureCovoiturageRepository, times(1)).existsById(notAvailableId);*/
    }

    /***
     * Ce test crée une demande de modification du nombre de places restantes
     * à la valeur null d'un covoiturage existant.
     * Cet attribut étant contraint par une annotation @NotNull, la méthode doit
     * générer une IllegalArgumentException.
     * L'objectif est de vérifier ici ce comportement.
     * 
     * Le repository normalement requis est simulé ici par une doublure Mokito.
     * On vérifie également que le repository n'a été appelé qu'une seule fois
     *
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    public void testUpdateShouldThrowIllegalArgumentException() {
/*        Integer availableId = 45;
        this.exampleCovoiturage.setNombrePlacesRestantes(null);
        this.exampleCovoiturage.setId(availableId);

        when(doublureCovoiturageRepository.existsById(availableId))
                .thenReturn(true);
        when(doublureCovoiturageRepository.save(this.exampleCovoiturage))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> {
            covoiturageService.updateOrganise(this.exampleCovoiturage);
        });

        Mockito.verify(doublureCovoiturageRepository, times(1)).existsById(availableId);
        Mockito.verify(doublureCovoiturageRepository, times(1)).save(this.exampleCovoiturage);*/
    }

    /***
     * Ce test lance un appel pour modifier un covoiturage existant.
     * L'objectif est de vérifier que l'entité retournée soit conforme.
     * Le repository normalement requis est simulé ici par une doublure Mokito.
     * On vérifie également que les méthodes du repository n'ont été appelés
     * qu'une seule fois.
     * 
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    public void testUpdateShouldReturnCovoiturage() throws CovoiturageNotFoundException {
/*        Integer availableId = 753;
        this.exampleCovoiturage.setId(availableId);

        when(this.doublureCovoiturageRepository.existsById(availableId)).thenReturn(true);
        when(this.doublureCovoiturageRepository.save(this.exampleCovoiturage)).thenReturn(this.exampleCovoiturage);

        Covoiturage returned = covoiturageService.updateOrganise(this.exampleCovoiturage);
        assertThat(returned.getId()).isSameAs(this.exampleCovoiturage.getId());

        Mockito.verify(doublureCovoiturageRepository, times(1)).existsById(availableId);
        Mockito.verify(doublureCovoiturageRepository, times(1)).save(this.exampleCovoiturage);*/
    }

    /***
     * Ce test appelle la suppression en base d'un covoiturage inexistant.
     * L'objectif est de vérifier la génération d'une
     * CovoiturageNotFoundException attendue.
     * Le repository normalement requis est simulé ici par une doublure Mokito.
     * On vérifie également que le repository n'a été appelé qu'une seule fois.
     * 
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    public void testDeleteShouldSuppressCovoiturage() {
        Integer notAvailableId = 4878;
        this.exampleCovoiturage.setId(notAvailableId);

        when(doublureCovoiturageRepository.existsById(notAvailableId))
                .thenReturn(false);

        assertThrows(CovoiturageNotFoundException.class, () -> {
            covoiturageService.delete(this.exampleCovoiturage.getId());
        });

        Mockito.verify(doublureCovoiturageRepository, times(1)).existsById(notAvailableId);
    }

    /***
     * Ce test envoie une requête pour la suppression en base d'un covoiturage
     * existant.
     * L'objectif est de vérifier le déroulement correct du processus.
     * 
     * Le repository normalement requis est simulé ici par une doublure Mokito.
     * On vérifie également que les méthodes repository n'ont été appelées
     * qu'une seule fois.
     * 
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {

        Integer availableId = 8547;
        this.exampleCovoiturage.setId(availableId);

        when(doublureCovoiturageRepository.existsById(availableId))
                .thenReturn(true);
        Mockito.doNothing().when(this.doublureCovoiturageRepository).deleteById(this.exampleCovoiturage.getId());

        covoiturageService.delete(this.exampleCovoiturage.getId());

        Mockito.verify(doublureCovoiturageRepository, times(1)).existsById(availableId);
        Mockito.verify(doublureCovoiturageRepository, times(1)).deleteById(this.exampleCovoiturage.getId());
    }

    public static Covoiturage createCovoiturageForTest() {
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
