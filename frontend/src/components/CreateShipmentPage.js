import React, { useState } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import { createShipment } from '../redux/actions/actions';

import './CreateShipmentPage.css';

const CLICK_TO_SELECT_COLOR = 'Click to select colour';
const CLICK_TO_CONFIRM = 'Click button to confirm';

const CreateShipmentPage = ({ isLoading, actions }) => {

    const [form, setForm] = useState({});
    const [colorPickerVisible, setColorPickerVisible] = useState(false);
    const [colorPickerText, setColorPickerText] = useState(CLICK_TO_SELECT_COLOR);
    const [colorPreview, setColorPreview] = useState({ backgroundColor: "rgb(0,0,0)" });
    const [colorRed, setColorRed] = useState(0);
    const [colorGreen, setColorGreen] = useState(0);
    const [errors, setErrors] = useState({});

    const destinations = [
        'Sweden',
        'China',
        'Brazil',
        'Australia'];

    const handleSave = (event) => {
        event.preventDefault();
        const shipment = validateForm();
        if (shipment) {
            actions.createShipment(shipment);
            setForm({});
            resetForm();
        }
    }

    const handleChange = (event) => {
        const { name, value } = event.target;
        setForm(previous => {
            return {
                ...previous,
                [name]: value
            };
        });
    }

    const showColorPicker = (event) => {
        setColorPickerVisible(true);
        setColorPickerText(CLICK_TO_CONFIRM);
    }

    const setColor = (event) => {
        setForm({ ...form, color: colorPreview.backgroundColor });
        setColorPickerVisible(false);
        setColorPickerText(colorPreview.backgroundColor);
    }

    const updateColorPreview = (event) => {
        const { name, value } = event.target;
        if (name === "colorRed") { setColorRed(value) }
        if (name === "colorGreen") { setColorGreen(value) }
        setColorPreview({
            backgroundColor: `rgb(${colorRed},${colorGreen},0)`
        });
    }


    const resetForm = () => {
        setForm({});
        setColorPickerText(CLICK_TO_SELECT_COLOR);
    }

    const validateForm = () => {
        const { name, weight, color, country } = form;
        const err = {};
        if (!name) err.name = "Name is required";
        if (isNaN(weight)) err.weight = "Weight must be a number";
        if (!(weight > 0)) {
            err.weight = "Weight must be positive";
            setForm({ ...form, weight: 0 });
        }
        if (!color) err.color = "Colour is required";
        const validColor = validateColor(color);
        if (!validColor) err.color = "Colour is not valid";
        if (!country) err.country = "Country is required";

        setErrors({ ...err });

        if (Object.keys(err).length !== 0) return null;

        return {
            name: form.name,
            weightKg: form.weight,
            colorRed: validColor.colorRed,
            colorGreen: validColor.colorGreen,
            colorBlue: validColor.colorBlue,
            destination: form.country,
        }
    }

    const validateColor = color => {
        const re = /rgb\((\d+),(\d+),(\d+)\)/
        const match = re.exec(color);
        if (!match) return null
        const rgb = {
            colorRed: parseInt(match[1], 10),
            colorGreen: parseInt(match[2], 10),
            colorBlue: parseInt(match[3], 10)
        }
        if (rgb.colorRed < 0 || rgb.colorRed > 255) return null;
        if (rgb.colorGreen < 0 || rgb.colorGreen > 255) return null;
        if (rgb.colorBlue !== 0) return null;
        return rgb;
    }

    return (
        <div className="formContainer">
            <form onSubmit={handleSave}>
                <label htmlFor="name">Name</label>
                <input type="text" name="name" onChange={handleChange} value={form.name || ""} />
                {errors.name && <div className="error">{errors.name}</div>}
                <label htmlFor="weight">Weight</label>
                <input type="text" name="weight" onChange={handleChange} value={form.weight || ""} />
                {errors.weight && <div className="error">{errors.weight}</div>}
                <label htmlFor="color">Box colour</label>
                <input type="text" name="color" readOnly="readOnly" onChange={handleChange} value={colorPickerText} onClick={showColorPicker} />
                {colorPickerVisible &&
                    <div className="colorPicker">
                        <label htmlFor="colorRed">Red</label>
                        <input type="range" name="colorRed" onChange={updateColorPreview} min="0" max="255" value={colorRed} />
                        <label htmlFor="colorGreen">Green</label>
                        <input type="range" name="colorGreen" onChange={updateColorPreview} min="0" max="255" value={colorGreen} />
                        <div className="colorPreview" style={colorPreview} />
                        <button type="button" onClick={setColor}>Set colour</button>
                    </div>}
                {errors.color && <div className="error">{errors.color}</div>}
                <label htmlFor="country">Country</label>
                <select name="country" onChange={handleChange} value={form.country || ""}>
                    <option value="">Select country</option>
                    {destinations.map((destination, index) => <option key={index} value={destination.toUpperCase()}>{destination}</option>)}
                </select>
                {errors.country && <div className="error">{errors.country}</div>}
                <button type="submit">Save</button>
            </form>
        </div>
    )



}

const mapStateToProps = state => {
    const { isLoading = false } = state;
    return {
        isLoading
    };
}

const mapDispatchToProps = dispatch => {
    return {
        actions: {
            createShipment: bindActionCreators(createShipment, dispatch)
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(CreateShipmentPage);
