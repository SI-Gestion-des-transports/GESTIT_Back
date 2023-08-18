package fr.diginamic.gestit_back.entites;


import io.jsonwebtoken.Claims;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur extends AbstractBaseEntity {

    private String nom;

    private String email;

    private String motDePasse;

    private LocalDate dateNonValide;

    @ElementCollection(fetch = FetchType.EAGER)
    //@Enumerated(EnumType.STRING)
    private List<String> roles;

    @OneToMany(mappedBy = "collaborateur")
    private Set<ReservationVehiculeService> reservationVehiculeServices = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "covoiturages_collaborateur",
            joinColumns = @JoinColumn(name = "collaborateur_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "covoiturage_id", referencedColumnName = "id"))
    private Set<Covoiturage> covoituragesPassagers = new HashSet<>();

    @OneToMany(mappedBy = "proprietaire")
    private Set<VehiculePerso> vehiculesPerso = new HashSet<>();

    @OneToMany(mappedBy = "organisateur")
    private Set<Covoiturage> covoituragesOrganises = new HashSet<>();

    public Utilisateur(Claims body) {
        //this.setId((Integer) body.get("id")) ;
        this.nom = (String) body.get("username");
        this.motDePasse = (String) body.get("password");
        this.email = (String) body.get("email");
        //this.setId(Integer.valueOf(body.getSubject()));
        this.roles =(ArrayList<String>) body.get("roles");
    }

    public Utilisateur(String nom, String email, String motDePasse, List<String> roles) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.roles = roles;
    }

    public Utilisateur(String nom, String email, String motDePasse, List<String> roles, LocalDate dateNonValide) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.roles = roles;
        this.dateNonValide = dateNonValide;
    }

    public Utilisateur() {
    }
}
