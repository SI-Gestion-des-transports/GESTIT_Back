package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.repository.ModeleRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class ModeleService {

    private ModeleRepository modeleRepository;
}
