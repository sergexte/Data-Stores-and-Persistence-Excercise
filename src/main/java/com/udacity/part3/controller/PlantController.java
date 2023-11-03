package com.udacity.part3.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.part3.Views;
import com.udacity.part3.data.PlantDTO;
import com.udacity.part3.data.inventory.Plant;
import com.udacity.part3.data.service.PlantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/plant")
public class PlantController {

    @Autowired
    PlantService plantService;

    @GetMapping("/delivered/{id}")
    public Boolean delivered(@PathVariable Long id) {
        return plantService.isPlantDelivered(id);
    }

    @GetMapping("/under-price/{price}")
    @JsonView(Views.Public.class)
    public List<Plant> plantsCheaperThan(@PathVariable BigDecimal price) {
        return plantService.plantsCheaperThanPrice(price);
    }

    public PlantDTO getPlantDTO(String name) {
        final Plant plant = plantService.getPlantByName(name);
        return convertEntityToPlantDTO(plant);
    }

    @JsonView(Views.Public.class)
    public Plant getFilteredPlant(String name) {
        return plantService.getPlantByName(name);
    }

    private static PlantDTO convertEntityToPlantDTO(Plant plant) {
        PlantDTO plantDTO = new PlantDTO();
        BeanUtils.copyProperties(plant, plantDTO);
        return plantDTO;
    }
}
