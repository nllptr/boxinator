import { combineReducers } from 'redux';

import * as types from '../actions/actionTypes';
import initialState from './initialState';

const shipments = (state = initialState.shipments, action) => {
    switch (action.type) {
        case types.LOAD_SHIPMENTS_SUCCESS:
            return action.shipments;
        case types.CREATE_SHIPMENT_SUCCESS:
            return [...state, { ...action.shipment }];
        default:
            return state;
    }
}

const isLoading = (state = initialState.isLoading, action) => {
    switch (action.type) {
        case types.API_CALL_BEGIN:
            return true;
        case types.API_CALL_ERROR:
            return false;
        case types.LOAD_SHIPMENTS_SUCCESS:
            return false;
        case types.CREATE_SHIPMENT_SUCCESS:
            return false;
        default:
            return state;
    }
}

export default combineReducers({
    shipments,
    isLoading,
});