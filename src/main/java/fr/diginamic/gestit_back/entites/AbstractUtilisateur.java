package fr.diginamic.gestit_back.entites;


import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractUtilisateur extends AbstractBaseEntity {


    private String nom;

    @Email
    private String email;

    private String motDePasse;

}
