package com.simonwessel.boxinator.shipment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestShipmentController {

    @MockBean
    ShipmentService service;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("GET /shipments - Success")
    void testFindAllSuccess() throws Exception {
        Shipment mockShipment1 = new Shipment(1, "Name 1", 1.5, 255, 0, 0, Shipment.Destination.SWEDEN.toString(), 33.0);
        Shipment mockShipment2 = new Shipment(2, "Name 2", 2.5, 0, 255, 0, Shipment.Destination.BRAZIL.toString(), 299.99);
        doReturn(Arrays.asList(mockShipment1, mockShipment2)).when(service).findAll();

        mockMvc.perform(get("/shipments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[1].name", is("Name 2")));
    }

    @Test
    @DisplayName("POST /shipment - Success")
    void testCreateSuccess() throws Exception {
        Shipment.Destination destination = Shipment.Destination.BRAZIL;
        Shipment postShipment = new Shipment("Shipment", 22.0, 255, 0, 0, destination.toString());
        Shipment mockShipment = new Shipment(1, "Shipment", 22.0, 255, 0, 0, destination.toString(), 22.0 * destination.getMultiplier());
        doReturn(Optional.of(mockShipment)).when(service).save(any());

        mockMvc.perform(post("/shipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postShipment)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Shipment")))
                .andExpect(jsonPath("$.weightKg", is(22.0)))
                .andExpect(jsonPath("$.colorRed", is(255)))
                .andExpect(jsonPath("$.colorGreen", is(0)))
                .andExpect(jsonPath("$.colorBlue", is(0)))
                .andExpect(jsonPath("$.destination", is("BRAZIL")))
                .andExpect(jsonPath("$.shippingCostSEK", is(189.2)));
    }

    @Test
    @DisplayName("POST /shipment - Validation failed: empty name")
    void testCreateValidationFailedName() throws Exception {
        Shipment.Destination destination = Shipment.Destination.BRAZIL;
        Shipment postShipment = new Shipment("", 22.0, 255, 0, 0, destination.toString());

        mockMvc.perform(post("/shipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postShipment)))

                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /shipment - Validation failed: negative weight")
    void testCreateValidationFailedWeight() throws Exception {
        Shipment.Destination destination = Shipment.Destination.BRAZIL;
        Shipment postShipment = new Shipment("Shipment", -10.0, 255, 0, 0, destination.toString());

        mockMvc.perform(post("/shipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postShipment)))

                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /shipment - Validation failed: Too high red value")
    void testCreateValidationFailedRedValue() throws Exception {
        Shipment.Destination destination = Shipment.Destination.BRAZIL;
        Shipment postShipment = new Shipment("Shipment", 22.0, 256, 0, 0, destination.toString());

        mockMvc.perform(post("/shipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postShipment)))

                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /shipment - Validation failed: Invalid destination")
    void testCreateValidationFailedInvalidDestination() throws Exception {
        Shipment postShipment = new Shipment("Shipment", 22.0, 255, 0, 0, "Ankeborg");

        mockMvc.perform(post("/shipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postShipment)))

                .andExpect(status().isBadRequest());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
