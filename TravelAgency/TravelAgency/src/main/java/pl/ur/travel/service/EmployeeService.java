package pl.ur.travel.service;

import pl.ur.travel.model.dao.Cost;
import pl.ur.travel.model.dao.Offer;
import pl.ur.travel.model.dao.OfferCost;
import pl.ur.travel.repository.CostsRepository;
import pl.ur.travel.repository.OfferCostsRepository;
import pl.ur.travel.repository.OfferRepository;

import java.util.List;
import java.util.UUID;

public class EmployeeService extends AbstractService {

    public EmployeeService(final OfferRepository or,
                           final CostsRepository cr,
                           final OfferCostsRepository ocr) {
        super(or, cr, ocr);
    }

    public void markOfferAsPaid(UUID id) {
        or.selectById(id)
                .ifPresent(offer ->
                        or.updateById(
                                id,
                                new Offer(
                                        offer.id(),
                                        offer.name(),
                                        false
                                )
                        )
                );
    }

    public void updateOffer(UUID offerId, Offer offer) {
        or.updateById(offerId, offer);
    }

    public void deleteOffer(UUID offerId) {
        List<OfferCost> offerCosts = getOfferCostsForOfferId(offerId);
        offerCosts.forEach(oc -> ocr.deleteById(oc.id()));
        or.deleteById(offerId);
    }

    public void addOffer(Offer offer) {
        or.insert(offer);
    }

    public void addCostToOffer(UUID offerId, UUID costId) {
        ocr.insert(new OfferCost(UUID.randomUUID(), offerId, costId));
    }

    public void addCost(Cost cost) {
        cr.insert(cost);
    }

    public void deleteCost(UUID costId) {
        cr.deleteById(costId);
    }

    public void updateCost(Cost newCost) {
        cr.updateById(newCost.id(), newCost);
    }

    public void deleteCostFromOffer(UUID offerId, UUID costId) {
        ocr.selectAll()
                .stream()
                .filter(it -> it.offerId().equals(offerId))
                .filter(it -> it.costId().equals(costId))
                .map(OfferCost::id)
                .forEach(ocr::deleteById);
    }
}
