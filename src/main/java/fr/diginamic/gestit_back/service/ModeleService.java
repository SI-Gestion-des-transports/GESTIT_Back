package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.entites.Marque;
import fr.diginamic.gestit_back.entites.Modele;
import fr.diginamic.gestit_back.repository.MarqueRepository;
import fr.diginamic.gestit_back.repository.ModeleRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class ModeleService {

    private ModeleRepository modeleRepository;
    private MarqueRepository marqueRepository;

    public void creerModele(String nom, Marque marque){
        Modele modele = new Modele(nom, marqueRepository.findMarqueByNom(marque.getNom()));
        modeleRepository.save(modele);
    }


}
