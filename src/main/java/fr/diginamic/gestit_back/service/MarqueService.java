package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.MarqueDto;
import fr.diginamic.gestit_back.entites.Marque;
import fr.diginamic.gestit_back.repository.MarqueRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
@Transactional
public class MarqueService {

    private MarqueRepository marqueRepository;

    public Marque getMarqueByName(String name){
        return marqueRepository.findMarqueByNom(name);
    }

    public List<Marque> listerMarques() {
        return this.marqueRepository.findAll();
    }

    public boolean verifyMarque(String nom) {
        Marque marque = marqueRepository.findMarqueByNom(nom);
        if (marque == null) return false;
        else return true;
    }

    public void deleteMarqueByNom(String nom) {
        marqueRepository.deleteByNom(nom);
    }

    public void updateMarque(MarqueDto marqueDto) {
        Optional<Marque> marque = marqueRepository.findById(marqueDto.id());
        Marque marquefinded = marque.get();
        marquefinded.setNom(marqueDto.nom());
        marqueRepository.save(marquefinded);

    }


    public Marque creerMarque(String nom) {
        return this.marqueRepository.save(new Marque(nom, null));
    }
}
