package com.simonwessel.boxinator.shipment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@CrossOrigin(origins = "${frontend.host}")
@RestController
public class ShipmentController {

    Logger logger = LogManager.getLogger(ShipmentController.class);

    ShipmentService service;

    public ShipmentController(ShipmentService service) {
        this.service = service;
    }

    @GetMapping("/shipments")
    Iterable<Shipment> getAllShipments() {
        return service.findAll();
    }

    @PostMapping("/shipment")
    ResponseEntity<?> createShipment(@Valid @RequestBody Shipment shipment, BindingResult bindingResult) {
        // The Shipment type validates blue color just as red and green to avoid hard-found bugs if we decide
        // to allow blue colors later. Instead we make a special validation for blue here in the controller.
        if(shipment.getColorBlue() > 0) {
            logger.info("Blue colors not allowed");
            return ResponseEntity.badRequest().body("Blue colors not allowed");
        }

        // Check if provided destination is in available destinations.
        Optional<Shipment.Destination> destination = Arrays.stream(Shipment.Destination.values())
                .filter(d -> d.name().equals(shipment.getDestination()))
                .findFirst();
        if(destination.isEmpty()) {
            logger.info("Invalid destination");
            return ResponseEntity.badRequest().body("Invalid destination");
        }

        if(bindingResult.hasErrors()) {
            logger.info(bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Optional<Shipment> newShipment = service.save(shipment);
        if(newShipment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newShipment);
    }
}
