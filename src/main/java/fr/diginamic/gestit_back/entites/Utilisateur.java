package fr.diginamic.gestit_back.entites;

import fr.diginamic.gestit_back.enumerations.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
/*
 * Spécifie la stratégie d'héritage à utiliser pour une hiérarchie de classes
 * d'entités. Il est spécifié sur la classe d'entités qui est la racine de la
 * hiérarchie des classes d'entités. Si l'annotation Héritage n'est pas
 * spécifiée ou si aucun type d'héritage n'est spécifié pour une hiérarchie de
 * classes d'entités, la stratégie de mappage SINGLE_TABLE est utilisée.
 */
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur extends AbstractBaseEntity {

    private String nom;

    @Email // Ne faut-il pas un @Valid pour activer la validation?
    private String email;

    private String motDePasse;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING) // Lorsqu'une colonne utilise une énumération
    private Set<Role> roles;

    @OneToMany(mappedBy = "collaborateur")
    private Set<ReservationVehiculeService> reservationVehiculeServices = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "covoiturages_collaborateur", joinColumns = @JoinColumn(name = "collaborateur_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "covoiturage_id", referencedColumnName = "id"))
    private Set<Covoiturage> covoituragesPassagers = new HashSet<>();

    @OneToMany(mappedBy = "proprietaire")
    private Set<VehiculePerso> vehiculesPerso = new HashSet<>();

    @OneToMany(mappedBy = "organisateur")
    private Set<Covoiturage> covoituragesOrganises = new HashSet<>();

    public Utilisateur() {
    }

    public Utilisateur(String nom, String email, String s, String role) {
    }
}
