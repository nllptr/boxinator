package com.simonwessel.boxinator.shipment;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class Shipment {

    public enum Destination {
        SWEDEN( 1.3),
        CHINA(4.0),
        BRAZIL(8.6),
        AUSTRALIA(7.2);

        private final Double multiplier;

        Destination(Double multiplier) {
            this.multiplier = multiplier;
        }

        public Double getMultiplier() {
            return this.multiplier;
        }
    }

    private Integer id;
    @NotBlank
    private String name;
    @NotNull
    @Positive
    private Double weightKg;
    @NotNull
    @Min(0)
    @Max(255)
    private Integer colorRed;
    @NotNull
    @Min(0)
    @Max(255)
    private Integer colorGreen;
    @NotNull
    @Min(0)
    @Max(255)
    private Integer colorBlue;
    @NotBlank
    private String destination;
    private Double shippingCostSEK;

    public Shipment(Integer id, String name, Double weightKg, Integer colorRed, Integer colorGreen, Integer colorBlue, String destination, Double shippingCostSEK) {
        this.id = id;
        this.name = name;
        this.weightKg = weightKg;
        this.colorRed = colorRed;
        this.colorGreen = colorGreen;
        this.colorBlue = colorBlue;
        this.destination = destination;
        this.shippingCostSEK = shippingCostSEK;
    }

    public Shipment(String name, Double weightKg, Integer colorRed, Integer colorGreen, Integer colorBlue, String destination) {
        this.name = name;
        this.weightKg = weightKg;
        this.colorRed = colorRed;
        this.colorGreen = colorGreen;
        this.colorBlue = colorBlue;
        this.destination = destination;
    }

    public Shipment() {
        this.id = 0;
        this.name = "";
        this.weightKg = 0.0;
        this.colorRed = 0;
        this.colorGreen = 0;
        this.colorBlue = 0;
        this.destination = "";
        this.shippingCostSEK = 0.0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    public Integer getColorRed() { return colorRed; }

    public void setColorRed(Integer colorRed) { this.colorRed = colorRed; }

    public Integer getColorGreen() { return colorGreen; }

    public void setColorGreen(Integer colorGreen) { this.colorGreen = colorGreen; }

    public Integer getColorBlue() { return colorBlue; }

    public void setColorBlue(Integer colorBlue) { this.colorBlue = colorBlue; }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getShippingCostSEK() {
        return shippingCostSEK;
    }

    public void setShippingCostSEK(Double shippingCostSEK) {
        this.shippingCostSEK = shippingCostSEK;
    }
}
