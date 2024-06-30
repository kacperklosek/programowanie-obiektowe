import pl.ur.travel.model.dao.Cost;
import pl.ur.travel.model.dao.CostType;
import pl.ur.travel.model.dao.Offer;
import pl.ur.travel.model.dao.OfferCost;
import pl.ur.travel.repository.CostsRepository;
import pl.ur.travel.repository.OfferCostsRepository;
import pl.ur.travel.repository.OfferRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;

public class Test {

    public static void main(String[] args) throws SQLException {
        // COST
        Cost ticket = new Cost(randomUUID(), CostType.TICKET, 4000.0, "Bilet do Honolulu");
        Cost ticket2 = new Cost(randomUUID(), CostType.TICKET, 4001.0, "Bilet do Honolulu 2");
        CostsRepository costsRepository = new CostsRepository();
        costsRepository.insert(ticket);
        costsRepository.insert(ticket2);
        List<Cost> costs = costsRepository.selectAll();

        if (costs.size() == 2) {
            System.out.println("Select all + insert OK.");
        }

        Cost cost = costsRepository.selectById(ticket.id()).get();
        costsRepository.updateById(cost.id(), new Cost(
                cost.id(),
                cost.type(),
                777.0,
                "Nowy description"
        ));
        Cost cost2 = costsRepository.selectById(ticket.id()).get();

        if (cost2.description().equals("Nowy description")) {
            System.out.println("Update OK");
        }

        costsRepository.deleteById(ticket.id());
        costsRepository.deleteById(ticket2.id());
        if (costsRepository.selectById(ticket.id()).isEmpty()) {
            System.out.println("Delete OK.");
        }

        // OFFER
        var or = new OfferRepository();
        Offer offer = new Offer(randomUUID(), "Wycieczka do Honolulu", false);
        or.insert(offer);

        List<Offer> offers = or.selectAll();

        if (offers.size() == 1) {
            System.out.println("OFFER: Insert and selectAll OK");
        }

//        Optional<Offer> offerResult = or.selectById(offer.id());
//        if (offerResult.isPresent()) {
//            System.out.println("OFFER: select by ID ok");
//        }
//
//        or.updateById(offer.id(), new Offer(
//                offer.id(),
//                "Wyprawa do Honolulu",
//                true
//        ));
//
//        Optional<Offer> offerNext = or.selectById(offer.id());
//        offerNext.filter(it -> it.name().equals("Wyprawa do Honolulu"))
//                .ifPresent(ig -> System.out.println("OFFER: Update OK"));
//
//        or.deleteById(offer.id());
//
//        if (or.selectAll().size() == 0) {
//            System.out.println("OFFER: DELETE OK");
//        }
//
//
//        // OFFER_COST
//        var ofr = new OfferCostsRepository();
//        OfferCost offerCost = new OfferCost(randomUUID(), randomUUID(), randomUUID());
//        ofr.insert(offerCost);
//
//        List<OfferCost> offerCosts = ofr.selectAll();
//
//        if (offerCosts.size() == 1) {
//            System.out.println("OC: insert/selectALL OK");
//        }
//
//        Optional<OfferCost> offerCost1 = ofr.selectById(offerCost.id());
//        offerCost1.ifPresent(
//                ig -> System.out.println("OC: Select by ID OK")
//        );
//
//        ofr.deleteById(offerCost1.get().id());
//
//        Optional<OfferCost> offerCost2 = ofr.selectById(offerCost.id());
//        offerCost2.ifPresentOrElse(
//                ig -> System.out.println("OC: źle"),
//                () -> System.out.println("OC: DELETE BY ID OK")
//        );
//
    }
}
