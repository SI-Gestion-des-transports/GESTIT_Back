package fr.diginamic.gestit_back.entites;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Commune extends AbstractBaseEntity {

    private String nom;

    private Integer codePostal;

    public Commune() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        Commune commune = (Commune) o;

        if (!nom.equals(commune.nom))
            return false;
        return codePostal.equals(commune.codePostal);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + nom.hashCode();
        result = 31 * result + codePostal.hashCode();
        return result;
    }
}
