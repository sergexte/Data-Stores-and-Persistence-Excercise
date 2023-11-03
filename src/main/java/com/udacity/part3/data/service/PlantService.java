package com.udacity.part3.data.service;

import com.udacity.part3.data.inventory.Plant;
import com.udacity.part3.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PlantService {

    @Autowired
    PlantRepository plantRepository;

    public Long savePlant(Plant plant) {
        Plant savedPlant =  plantRepository.save(plant);
        return savedPlant.getId();
    }

    public Boolean isPlantDelivered(Long id) {
        // ALt method
        //return plantRepository.existsPlantByIdAndDeliveryCompleted(id, true);
        return plantRepository.isPlantDelivered(id);
    }

    public List<Plant> plantsCheaperThanPrice(BigDecimal price) {
        return plantRepository.findByPriceLessThan(price);
    }

    public Plant getPlantByName(String name) {
        return new Plant();
    }
}