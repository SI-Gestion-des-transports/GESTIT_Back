package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.entites.Marque;
import fr.diginamic.gestit_back.repository.MarqueRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class MarqueService {

    private MarqueRepository marqueRepository;

    public List<Marque> listerMarques(){
        return this.marqueRepository.findAll();
    }


    public Marque creerMarque(String nom){
        return this.marqueRepository.save(new Marque(nom, null));
    }
}
