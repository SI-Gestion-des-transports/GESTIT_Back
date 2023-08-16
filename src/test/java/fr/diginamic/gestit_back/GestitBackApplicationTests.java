package fr.diginamic.gestit_back;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import fr.diginamic.gestit_back.controller.CovoiturageController;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

/* L'annotation @SpringBootTest indique à Spring Boot de rechercher une classe de configuration principale (une classe avec @SpringBootApplication, par exemple) et de l'utiliser pour démarrer un contexte d'application Spring.
Nous Vérifions ensuite wue le contexte crée bien un des composants décrits dans l'application, puis l'injecte bien avant l'exécution des méthodes de test*/
@SpringBootTest
class GestitBackApplicationTests {
	@Autowired
	private CovoiturageController covoiturageController;

	@Test
	void contextLoads() throws Exception {
		assertThat(covoiturageController).isNotNull();
	}

}
