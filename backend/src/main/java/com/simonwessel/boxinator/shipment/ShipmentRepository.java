package com.simonwessel.boxinator.shipment;

import java.util.List;

public interface ShipmentRepository {
    List<Shipment> findAll();
    Shipment save(Shipment shipment);
}
