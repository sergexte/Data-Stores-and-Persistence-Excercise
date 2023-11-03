package com.udacity.part3;

import com.udacity.part3.data.delivery.Delivery;
import com.udacity.part3.data.inventory.Plant;
import com.udacity.part3.repository.PlantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class Part3ApplicationTests {

	@Autowired
	TestEntityManager testEntityManager;

	@Autowired
	PlantRepository plantRepository;

	@Test
	void testPriceLessThan() {
		//Adds two new plants to your database with two different prices.

		Plant newPlant1 = new Plant();
		newPlant1.setPrice(BigDecimal.valueOf(4.99));
		newPlant1.setName("ficus");

		Plant p = testEntityManager.persist(newPlant1);
		testEntityManager.persist(new Plant("Bar Weed", BigDecimal.valueOf(6.00)));


		List<Plant> cheapPlants = plantRepository.findByPriceLessThan(BigDecimal.valueOf(5));
		assertEquals(1, cheapPlants.size());
		assertEquals(p.getId(), cheapPlants.get(0).getId(), "Id");
	}

	@Test
	void testDeliveryCompleted() {
		// Create a new plant and a new Delivery. Set both sides of their bi-directional
		// relationship. Verify that PlantRepository.deliveryCompleted return false for the plant you
		// just created. Then set the Delivery to true and verify that deliveryCompleted returns true.

		Plant p = testEntityManager.persist(new Plant("ficus", BigDecimal.valueOf(5)));
		Delivery d = testEntityManager.persist(new Delivery("name", "99 E 4", LocalDateTime.now()));

		d.setPlants(Collections.singletonList(p));
		p.setDelivery(d);

		assertFalse(plantRepository.deliveryCompletedBoolean(p.getId()));
		d.setCompleted(true);
		assertTrue(plantRepository.deliveryCompletedBoolean(p.getId()));
	}
}
