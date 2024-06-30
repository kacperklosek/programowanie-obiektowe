package pl.ur.travel.service;

import pl.ur.travel.model.dao.Cost;
import pl.ur.travel.model.dao.Offer;
import pl.ur.travel.model.dao.OfferCost;
import pl.ur.travel.repository.CostsRepository;
import pl.ur.travel.repository.OfferCostsRepository;
import pl.ur.travel.repository.OfferRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AbstractService {

    protected final OfferRepository or;
    protected final CostsRepository cr;
    protected final OfferCostsRepository ocr;

    public List<Offer> getAllOffers() {
        return this.or.selectAll();
    }
    public AbstractService(final OfferRepository or, final CostsRepository cr, final OfferCostsRepository ocr) {
        this.or = or;
        this.cr = cr;
        this.ocr = ocr;
    }

    public List<Cost> getAllCostsForOffer(UUID offerId) {
        return getAllCostsForOfferCosts(getOfferCostsForOfferId(offerId));
    }

    public List<Cost> getAllCostsForOfferCosts(List<OfferCost> ocs) {
        return ocs.stream().map(oc -> cr.selectById(oc.id()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public List<OfferCost> getOfferCostsForOfferId(final UUID offerId) {
        return ocr.selectAll()
                .stream()
                .filter(it -> it.offerId().equals(offerId))
                .toList();
    }
}
