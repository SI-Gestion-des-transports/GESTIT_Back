package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.repository.CovoiturageRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Data
public class CovoiturageService {

    private CovoiturageRepository covoiturageRepository;

    public List<Covoiturage> listerCovoiturages() {
        return this.covoiturageRepository.findAll();
    }
}
