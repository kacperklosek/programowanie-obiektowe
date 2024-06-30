package pl.ur.travel.service;

import pl.ur.travel.model.dao.Cost;
import pl.ur.travel.model.dao.Offer;
import pl.ur.travel.repository.CostsRepository;
import pl.ur.travel.repository.OfferCostsRepository;
import pl.ur.travel.repository.OfferRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService extends AbstractService {

    public UserService(final OfferRepository or, final CostsRepository cr, final OfferCostsRepository ocr) {
        super(or, cr, ocr);
    }

    public void selectOffer(UUID id) {
        or.selectById(id)
                .ifPresent(offer ->
                        or.updateById(
                                id,
                                new Offer(
                                        offer.id(),
                                        offer.name(),
                                        true
                                )
                        )
                );
        // TODO error handling, what if the offer is not present?
    }

    public Double calculateCost(UUID offerId) {
        return getAllCostsForOffer(offerId).stream()
                .map(Cost::cost)
                .reduce(Double::sum)
                .orElse(0.0);
    }
}
