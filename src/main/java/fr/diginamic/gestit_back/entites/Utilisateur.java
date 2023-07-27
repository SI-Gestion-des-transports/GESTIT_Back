package fr.diginamic.gestit_back.entites;


import fr.diginamic.gestit_back.enumerations.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.access.annotation.Secured;

import java.util.HashSet;
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

    private boolean isAdmin;

    @OneToMany(mappedBy = "collaborateur")
    private Set<ReservationVehiculeService> reservationVehiculeServices;

    @OneToMany(mappedBy = "organisateur")
    private Set<Covoiturage> covoituragesOrganises;

    @ManyToMany
    @JoinTable(name="covoiturages_collaborateur",
            joinColumns = @JoinColumn(name="collaborateur_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "covoiturage_id", referencedColumnName = "id"))
    private Set<Covoiturage> covoituragesPassagers;

    @OneToMany(mappedBy = "proprietaire")
    private Set<VehiculePerso> vehiculesPerso = new HashSet<>();

    public Utilisateur() {
    }
}
