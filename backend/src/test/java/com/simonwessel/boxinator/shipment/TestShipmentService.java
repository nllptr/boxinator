package com.simonwessel.boxinator.shipment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestShipmentService {

    @Autowired
    ShipmentService service;

    @MockBean
    ShipmentRepository repository;

    @Test
    @DisplayName("Test findAll")
    void testFindAllSuccess() {
        Shipment mockShipment1 = new Shipment(1, "Name 1", 1.5, 255, 0, 0, Shipment.Destination.SWEDEN.toString(), 33.0);
        Shipment mockShipment2 = new Shipment(2, "Name 2", 2.5, 0, 255, 0, Shipment.Destination.BRAZIL.toString(), 299.99);
        doReturn(Arrays.asList(mockShipment1, mockShipment2)).when(repository).findAll();

        List<Shipment> shipments = service.findAll();

        Assertions.assertEquals(2, shipments.size(), "Expected 2 elements in shipment list");
    }

    @Test
    @DisplayName("Test save")
    void testSaveSuccess() {
        Shipment mockShipment = new Shipment("Name 1", 1.0, 255, 0, 0, Shipment.Destination.CHINA.name());
        doReturn(mockShipment).when(repository).save(any());

        Optional<Shipment> savedShipment = service.save(mockShipment);

        Assertions.assertTrue(savedShipment.isPresent(), "The saved shipment should not be null");
        Assertions.assertEquals(4.0, savedShipment.get().getShippingCostSEK(), "The saved shipment should have a shipping cost of 4.0");
    }
}
