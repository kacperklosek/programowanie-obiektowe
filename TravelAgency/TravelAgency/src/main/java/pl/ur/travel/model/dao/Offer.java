package pl.ur.travel.model.dao;

import java.util.UUID;

public record Offer(UUID id, String name, Boolean accepted) {

    @Override
    public String toString() {
        return this.name;
    }
}
