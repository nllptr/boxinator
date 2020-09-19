package com.simonwessel.boxinator.shipment;

import java.util.List;
import java.util.Optional;

public interface ShipmentService {
    List<Shipment> findAll();
    Optional<Shipment> save(Shipment shipment);
}
