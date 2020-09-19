package com.simonwessel.boxinator.shipment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShipmentController {

    ShipmentService service;

    @GetMapping("/shipments")
    Iterable<Shipment> getAllShipments() {
        return service.findAll();
    }
}
