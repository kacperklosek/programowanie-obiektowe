package pl.ur.travel;

import pl.ur.travel.repository.CostsRepository;
import pl.ur.travel.repository.OfferCostsRepository;
import pl.ur.travel.repository.OfferRepository;
import pl.ur.travel.service.EmployeeService;

public class ServiceProvider {
    public static final OfferRepository or = new OfferRepository();
    public static final CostsRepository cr = new CostsRepository();
    public static final OfferCostsRepository oc = new OfferCostsRepository();
    public static final EmployeeService eS = new EmployeeService(or, cr, oc);

}
