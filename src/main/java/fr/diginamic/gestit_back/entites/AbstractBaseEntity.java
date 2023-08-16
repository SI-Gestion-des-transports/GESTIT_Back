package fr.diginamic.gestit_back.entites;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class AbstractBaseEntity { // Quel intérêt?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
