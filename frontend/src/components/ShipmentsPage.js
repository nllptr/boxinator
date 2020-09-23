import React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import ShipmentList from './ShipmentList';
import { loadShipments } from '../redux/actions/actions';

class ShipmentsPage extends React.Component {
    render() {
        return (
            <ShipmentList shipments={this.props.shipments} />
        )
    }

    componentDidMount() {
        const { actions } = this.props;
        actions.loadShipments().catch(error => console.error(error));
    }
}

ShipmentsPage.propTypes = {
    shipments: PropTypes.array,
    isLoading: PropTypes.bool
};

const mapStateToProps = state => {
    const { shipments = [], isLoading = false } = state;
    return {
        shipments,
        isLoading,
    };
}

const mapDispatchToProps = dispatch => {
    return {
        actions: {
            loadShipments: bindActionCreators(loadShipments, dispatch)
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(ShipmentsPage);