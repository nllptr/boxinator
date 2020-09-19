package com.simonwessel.boxinator.shipment;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ShipmentRepositoryImpl implements ShipmentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ShipmentRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("shipments")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Shipment> findAll() {
        return jdbcTemplate.query("SELECT * FROM shipments",
                (rs, rowNumber) -> {
                    Shipment s = new Shipment();
                    s.setId(rs.getInt("id"));
                    s.setName(rs.getString("name"));
                    s.setWeightKg(rs.getDouble("weightKg"));
                    s.setColorRed(rs.getInt("colorRed"));
                    s.setColorGreen(rs.getInt("colorGreen"));
                    s.setColorBlue(rs.getInt("colorBlue"));
                    s.setDestination(rs.getString("destination"));
                    s.setShippingCostSEK(rs.getDouble("shippingCostSEK"));
                    return s;
                });
    }

    @Override
    public Shipment save(Shipment shipment) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", shipment.getName());
        params.put("weightKg", shipment.getWeightKg());
        params.put("colorRed", shipment.getColorRed());
        params.put("colorGreen", shipment.getColorGreen());
        params.put("colorBlue", shipment.getColorBlue());
        params.put("destination", shipment.getDestination());
        params.put("shippingCostSEK", shipment.getShippingCostSEK());
        Number newId = simpleJdbcInsert.executeAndReturnKey(params);
        shipment.setId(newId.intValue());
        return shipment;
    }
}
