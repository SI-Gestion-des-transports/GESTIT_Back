package fr.diginamic.gestit_back;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import fr.diginamic.gestit_back.controller.CovoiturageController;

@SpringBootTest
public class GestitBackApplicationTests {
	@Autowired
	private CovoiturageController covoiturageController;

	@Test
	public void contextLoads() throws Exception {
		assertThat(covoiturageController).isNotNull();
	}

}
