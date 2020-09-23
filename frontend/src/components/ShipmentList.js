import React from 'react';

import './ShipmentList.css';

const ShipmentList = ({ shipments }) => (
    <table>
        <thead>
            <tr>
                <th>Receiver</th>
                <th>Weight</th>
                <th>Box colour</th>
                <th>Shipping cost</th>
            </tr>
        </thead>
        <tbody>
            {shipments && shipments.map(shipment => {
                const colorBoxStyle = {
                    backgroundColor: `rgb(${shipment.colorRed},${shipment.colorGreen},${shipment.colorBlue})`,
                }
                return (
                    <tr key={shipment.id}>
                        <td>{shipment.name}</td>
                        <td>{shipment.weightKg}</td>
                        <td style={colorBoxStyle}></td>
                        <td>{shipment.shippingCostSEK} SEK</td>
                    </tr>
                );
            })}
        </tbody>
    </table>
)

export default ShipmentList;