package fr.diginamic.gestit_back;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import fr.diginamic.gestit_back.controller.CovoiturageController;
import fr.diginamic.gestit_back.repository.CovoiturageRepository;

@SpringBootTest
public class GestitBackApplicationTests {
	@Autowired
	private CovoiturageController covoiturageController;

	@Autowired
	private CovoiturageRepository covoiturageRepository;

	@Test
	public void contextLoads() throws Exception {
		assertThat(covoiturageController).isNotNull();
		assertThat(covoiturageRepository).isNotNull();
	}

}
