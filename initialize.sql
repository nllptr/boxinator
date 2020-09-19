CREATE TABLE IF NOT EXISTS shipments (
    id INTEGER IDENTITY,
    name VARCHAR(128) NOT NULL,
    weightKg DOUBLE NOT NULL,
    colorRed INTEGER NOT NULL,
    colorGreen INTEGER NOT NULL,
    colorBlue INTEGER NOT NULL,
    destination VARCHAR(128) NOT NULL,
    shippingCostSEK DOUBLE NOT NULL,
    PRIMARY KEY (id)
);