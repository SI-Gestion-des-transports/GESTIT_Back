package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.exceptions.CovoiturageNotFoundException;
import fr.diginamic.gestit_back.repository.CovoiturageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Data
public class CovoiturageService {

    @Autowired
    private CovoiturageRepository covoiturageRepository;

    public Covoiturage add(Covoiturage covoiturage) {
        return covoiturageRepository.save(covoiturage);
    }

    public Covoiturage update(Covoiturage covoiturage) throws EntityNotFoundException {
        /*
         * if (!covoiturageRepository.existsById(covoiturage.getId())) {
         * throw new CovoiturageNotFoundException();
         * }
         */
        return covoiturageRepository.save(covoiturage);
    }

    public Covoiturage get(Integer id) throws CovoiturageNotFoundException {
        Optional<Covoiturage> result = covoiturageRepository.findById(id);
        if (result.isEmpty())
            throw new CovoiturageNotFoundException();
        return result.get();
    }

    public List<Covoiturage> list() {
        return covoiturageRepository.findAll();

    }

    public void delete(Integer id) throws CovoiturageNotFoundException {
        if (covoiturageRepository.existsById(id)) {
            covoiturageRepository.deleteById(id);
        }
        throw new CovoiturageNotFoundException();
    }

}
