package com.simonwessel.boxinator.shipment;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository repository;

    public ShipmentServiceImpl(ShipmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Shipment> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Shipment> save(Shipment shipment) {
        try {
            Shipment.Destination destination = Shipment.Destination.valueOf(shipment.getDestination());
            shipment.setShippingCostSEK(shipment.getWeightKg() * destination.getMultiplier());
            return Optional.of(repository.save(shipment));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
