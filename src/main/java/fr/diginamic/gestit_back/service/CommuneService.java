package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.entites.Commune;
import fr.diginamic.gestit_back.repository.CommuneRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Data
public class CommuneService {

    private CommuneRepository communeRepository;


    @Transactional
    public Commune nouvelleCommune(Commune commune){
        return this.communeRepository.save(commune);
    }


    public Commune verifierExistenceCommune(String commune, Integer codePostal){
        Optional <Commune> optionalCommune = this.communeRepository.findCommuneByNomAndCodePostal(commune, codePostal);
        return optionalCommune.orElse(null);
    }
}
