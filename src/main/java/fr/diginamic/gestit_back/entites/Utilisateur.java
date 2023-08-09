package fr.diginamic.gestit_back.entites;


import io.jsonwebtoken.Claims;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @Email
    private String email;

    private String motDePasse;

    @ElementCollection(fetch = FetchType.EAGER)
    //@Enumerated(EnumType.STRING)
    private List<String > roles;

    @OneToMany(mappedBy = "collaborateur")
    private Set<ReservationVehiculeService> reservationVehiculeServices = new HashSet<>();

    @ManyToMany
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
        this.roles =(ArrayList<String>) body.get("roles");
    }

    public Utilisateur(String nom, String email, String s, String role) {
    }

    public Utilisateur() {

    }
}
