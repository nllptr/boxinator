import * as types from './actionTypes';

const baseUrl = `http://${process.env.REACT_APP_BACKEND_HOST}:${process.env.REACT_APP_BACKEND_PORT}`;

export const loadShipments = () => {
    return function (dispatch) {
        dispatch({ type: types.API_CALL_BEGIN });
        return fetch(baseUrl + '/shipments')
            .then(response => {
                if (response.ok) return response.json();
                if (response.status === 400) {
                    const error = response.text()
                    throw new Error(error)
                }
            })
            .catch(error => {
                console.error(error)
                dispatch({ type: types.API_CALL_ERROR });
                throw error;
            })
            .then(json => {
                dispatch({
                    type: types.LOAD_SHIPMENTS_SUCCESS,
                    shipments: json
                });
            });
    };
}

export const createShipment = shipment => {
    return function (dispatch) {
        dispatch({ type: types.API_CALL_BEGIN })
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(shipment)
        }
        fetch(baseUrl + '/shipment', requestOptions)
            .then(response => {
                if (response.ok) return response.json();
                if (response.status === 400) {
                    const error = response.text()
                    throw new Error(error)
                }
            })
            .catch(error => {
                console.error(error);
                dispatch({
                    type: types.API_CALL_ERROR
                });
                throw error;
            })
            .then(json => {
                dispatch({
                    type: types.CREATE_SHIPMENT_SUCCESS,
                    shipment: json
                });
            });
    }
}
