package pl.ur.travel.model.dao;


import java.util.UUID;

public record Cost(UUID id, CostType type, Double cost, String description) {

}

