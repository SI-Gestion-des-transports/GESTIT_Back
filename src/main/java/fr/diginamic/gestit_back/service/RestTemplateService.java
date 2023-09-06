package fr.diginamic.gestit_back.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.MediaType;
import java.util.Arrays;

import fr.diginamic.gestit_back.entites.Covoiturage;

@Service
public class RestTemplateService {

    RestTemplate restTemplate = new RestTemplate();
    private static final String GET_ALL_COVOIT_URL = ("http://localhost:8080/covoiturages/getAllCovoiturages");
    private static final String CREATE_COVOIT_URL = ("http://localhost:8080/covoiturages/create");
    
    /* Méthode pour récupérer tous les covoiturages */
    public ResponseEntity<String> allCovoiturages() {
        HttpHeaders headers = new HttpHeaders();

        /*
         * Configuration du type de données attendues et contenues
         * dans la requête. Ici, un objet JSON)
         */
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        
        //Zone à remplir pour Spring Security
            //Passage du token
            //headers.add("Authorization", headerValue);


        //
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(GET_ALL_COVOIT_URL, HttpMethod.GET, entity,
                String.class);
        return response;
    }

    public ResponseEntity<Covoiturage> createCovoiturage(Covoiturage covoiturage){
        return restTemplate.postForEntity(CREATE_COVOIT_URL, covoiturage, Covoiturage.class);

    }
}
