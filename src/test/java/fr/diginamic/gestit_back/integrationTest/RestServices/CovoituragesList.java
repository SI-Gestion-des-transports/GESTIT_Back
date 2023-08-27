package fr.diginamic.gestit_back.integrationTest.RestServices;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;

import fr.diginamic.gestit_back.entites.Covoiturage;


@AllArgsConstructor
@Data
public class CovoituragesList {
    private List<Covoiturage> covoiturages;

    public CovoituragesList() {
        covoiturages = new ArrayList<>();
    }

}
