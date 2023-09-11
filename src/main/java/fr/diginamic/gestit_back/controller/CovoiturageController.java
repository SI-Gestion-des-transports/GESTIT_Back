package fr.diginamic.gestit_back.controller;
import fr.diginamic.gestit_back.dto.CovoiturageDto;
import fr.diginamic.gestit_back.exceptions.CovoiturageNotFoundException;
import fr.diginamic.gestit_back.service.CovoiturageService;
import fr.diginamic.gestit_back.dto.CovoiturageDto;
import fr.diginamic.gestit_back.dto.TestCovoiturageDto;
import fr.diginamic.gestit_back.dto.TestDto;
import fr.diginamic.gestit_back.exceptions.CovoiturageNotFoundException;
import fr.diginamic.gestit_back.repository.UtilisateurRepository;
import fr.diginamic.gestit_back.service.CovoiturageService;
import fr.diginamic.gestit_back.service.UtilisateurService;
import fr.diginamic.gestit_back.utils.JWTUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Adresse;
import fr.diginamic.gestit_back.entites.Commune;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculePerso;
import fr.diginamic.gestit_back.entites.Modele;
import fr.diginamic.gestit_back.entites.Marque;
import java.time.LocalDate;
import java.util.ArrayList;
import java.net.URI;
import java.util.List;

import java.util.stream.Collectors;

@RestController
@Secured("COLLABORATEUR")
@Data
@RequestMapping("/covoiturages")
public class CovoiturageController {

    private CovoiturageService covoiturageService;

    @Autowired
    private JWTUtils jwtUtils;
    private ModelMapper modelMapper;

    UtilisateurRepository utilisateurRepository;

    protected CovoiturageController(CovoiturageService covoiturageService, ModelMapper mapper) {
        this.covoiturageService = covoiturageService;
        this.modelMapper = mapper;
    }

    /**
     * Liste tous les covoiturages organisés par l'utilisateur connecté.
     *
     * @param httpHeaders Les entêtes HTTP, contenant notamment le JWT pour l'authentification.
     * @return Une réponse contenant la liste des covoiturages organisés par l'utilisateur.
     */
    @GetMapping("/listerorganises")
    public ResponseEntity<List<Covoiturage>> listerCovoiturageOrganises(@RequestHeader HttpHeaders httpHeaders) {
        Integer utilisateurConnecteId = Integer.decode(jwtUtils.parseJWT(httpHeaders.get("JWT-TOKEN").get(0)).getSubject());
        URI uri = URI.create("/covoiturages/listerorganises");
        return ResponseEntity.created(uri).body(this.covoiturageService.listerCovoiturageOrganises(utilisateurConnecteId));
    }

    /**
     * Crée un nouveau covoiturage basé sur les données fournies par le DTO.
     *
     * @param covoiturageDto Le DTO contenant les informations sur le covoiturage à créer.
     * @param httpHeaders    Les entêtes HTTP, contenant notamment le JWT pour l'authentification.
     * @return Une réponse contenant le covoiturage créé.
     * @throws CovoiturageNotFoundException Si le covoiturage n'a pas été trouvé ou ne peut être créé pour une raison quelconque.
     */
    @PostMapping("/create")
    public ResponseEntity<Covoiturage> creerCovoiturage(
            @RequestBody @Valid TestCovoiturageDto covoiturageDto,
            @RequestHeader HttpHeaders httpHeaders
    ) throws CovoiturageNotFoundException {
        Integer utilisateurConnecteId = Integer.decode(jwtUtils.parseJWT(httpHeaders.get("JWT-TOKEN").get(0)).getSubject());

        URI uri = URI.create("/covoiturages/create");
        return ResponseEntity.created(uri).body(this.covoiturageService.creerCovoiturage(covoiturageDto, utilisateurConnecteId));
    }



    /* Fonctions utilitaires */
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

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid Covoiturage covoiturage) {
        Covoiturage persistedCovoiturage = covoiturageService.add(covoiturage);
        CovoiturageDto covoiturageDto = entity2Dto(persistedCovoiturage);

        URI uri = URI.create("/covoiturages/" + covoiturageDto.getId());

        return ResponseEntity.created(uri).body(covoiturageDto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer id) {
        try {
            Covoiturage covoiturage = covoiturageService.get(id);
            return ResponseEntity.ok(entity2Dto(covoiturage));

        } catch (CovoiturageNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Liste tous les covoiturages enregistrés dans le système
     *
     * @return Une liste de tous les covoiturages enregistrés dans le système
     * @author AtsuhikoMochizuki
     */
    @GetMapping
    public ResponseEntity<?> list() {
        List<Covoiturage> listCovoiturages = covoiturageService.list();
        if (listCovoiturages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list2Dto(listCovoiturages));
    }
      
      
      
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody @Valid Covoiturage covoiturage) {
        try {
            covoiturage.setId(id);
            Covoiturage updatedCovoiturage = covoiturageService.update(covoiturage);
            return ResponseEntity.ok(entity2Dto(updatedCovoiturage));
        } catch (CovoiturageNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

      
          @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            covoiturageService.delete(id);
            return ResponseEntity.noContent().build();

        } catch (CovoiturageNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    private CovoiturageDto entity2Dto(Covoiturage entity) {
        return modelMapper.map(entity, CovoiturageDto.class);
    }

    private List<CovoiturageDto> list2Dto(List<Covoiturage> listCovoiturages) {
        return listCovoiturages.stream().map(
                        entity -> entity2Dto(entity))
                .collect(Collectors.toList());
    }
      

    @PostMapping("/testCreatePassageur")
    public ResponseEntity testCreatePassageur(@RequestBody TestDto testDto) {
        return covoiturageService.testCreatePassageur(testDto.userId, testDto.conId);
    }

/*    @PostMapping("/create")
    public void testCreate(@RequestBody TestCovoiturageDto tcd) throws CovoiturageNotFoundException {
        Integer covoitId = covoiturageService.testCreate(tcd);

        covoiturageService.testFindPassager(covoitId);
    }*/
}