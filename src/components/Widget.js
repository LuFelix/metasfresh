import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import { patch } from '../actions/WindowActions';

import Datetime from 'react-datetime';
import LookupDropdown from './app/LookupDropdown';
import Dropdown from './app/Dropdown';

class Widget extends Component {
    constructor(props) {
        super(props);
    }
    handlePatch = (property, value) => {
        const {data,windowType, dispatch} = this.props;

        // Check if patch is really needed (and it is not an init)
        if(this.findRowByPropName(data,property).value !== value ){
            dispatch(patch(windowType, data[0].value, property, value));
        }
    }
    renderWidget = (widgetType, fields, windowType, dataId, type, data, mandatory) => {
        switch(widgetType){
            case "Date":
                return (
                    <div className={"input-icon-container input-block " +
                        (data.mandatory ? "input-mandatory " : "") +
                        (type === "primary" ? "input-primary " : "input-secondary ")
                    }>
                        <Datetime
                            timeFormat={false}
                            dateFormat={true}
                            locale="de"
                            inputProps={{placeholder: "(none)", disabled: data.readonly}}
                            defaultValue={new Date(data.value)}
                            onChange={(date) => this.handlePatch(fields[0].field, date)}
                        />
                        <i className="meta-icon-calendar input-icon-right"></i>
                    </div>
                )
            case "DateTime":
                return (
                    <div className={"input-icon-container input-block " +
                        (data.mandatory ? "input-mandatory " : "") +
                        (type === "primary" ? "input-primary " : "input-secondary ")
                    }>
                        <Datetime
                            timeFormat={true}
                            dateFormat={true}
                            locale="de"
                            inputProps={{placeholder: "(none)", disabled: data.readonly}}
                            defaultValue={new Date(data.value)}
                            onChange={(date) => this.handlePatch(fields[0].field, date)}
                        />
                        <i className="meta-icon-calendar input-icon-right"></i>
                    </div>
                )
            case "Time":
                return (
                    <div className={"input-icon-container input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.mandatory ? "input-mandatory " : "")
                    }>
                        <Datetime
                            timeFormat={true}
                            dateFormat={false}
                            locale="de"
                            inputProps={{placeholder: "(none)", disabled: data.readonly}}
                            defaultValue={new Date(data.value)}
                            onChange={(date) => this.handlePatch(fields[0].field, date)}
                        />
                        <i className="meta-icon-calendar input-icon-right"></i>
                    </div>
                )
            case "Lookup":
                return (
                    <LookupDropdown
                        recent={[]}
                        dataId={dataId}
                        properties={fields}
                        windowType={windowType}
                        defaultValue={data.value}
                        readonly={data.readonly}
                        mandatory={data.mandatory}
                        rank={type}
                        onChange={(field, option) => this.handlePatch(field, option)}
                    />
                )
            case "List":
                return (
                    <Dropdown
                        dataId={dataId}
                        defaultValue="(none)"
                        selected={data.value}
                        properties={fields}
                        readonly={data.readonly}
                        mandatory={data.mandatory}
                        onChange={(option) => this.handlePatch(fields[0].field, option)}
                    />
                )
            case "Text":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.mandatory ? "input-mandatory " : "")
                    }>
                        <input
                            type="text"
                            className="input-field"
                            defaultValue={data.value}
                            disabled={data.readonly}
                            onBlur={(event) => this.handlePatch(fields[0].field, event.nativeEvent.target.value)}
                        />
                    </div>
                )
            case "LongText":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.mandatory ? "input-mandatory " : "")
                    }>
                        <textarea
                            className="input-field"
                            defaultValue={data.value}
                            disabled={data.readonly}
                            onBlur={(event) => this.handlePatch(fields[0].field, event.nativeEvent.target.value)}
                        />
                    </div>
                )
            case "Integer":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.mandatory ? "input-mandatory " : "")
                    }>
                        <input
                            type="number"
                            className="input-field"
                            min="0"
                            step="1"
                            defaultValue={data.value}
                            disabled={data.readonly}
                            onBlur={(event) => this.handlePatch(fields[0].field, event.nativeEvent.target.value)}
                        />
                    </div>
                )
            case "Number":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.mandatory ? "input-mandatory " : "")
                    }>
                        <input
                            type="number"
                            className="input-field"
                            defaultValue={data.value}
                            disabled={data.readonly}
                            onBlur={(event) => this.handlePatch(fields[0].field, event.nativeEvent.target.value)}
                        />
                    </div>
                )
            case "Amount" :
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.mandatory ? "input-mandatory " : "")
                    }>
                        <input
                            type="number"
                            className="input-field"
                            min="0"
                            step="1"
                            defaultValue={data.value}
                            disabled={data.readonly}
                            onBlur={(event) => this.handlePatch(fields[0].field, event.nativeEvent.target.value)}
                        />
                    </div>
                )
            case "Quantity":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.mandatory ? "input-mandatory " : "")
                    }>
                        <input
                            type="number"
                            className="input-field"
                            min="0"
                            step="1"
                            defaultValue={data.value}
                            disabled={data.readonly}
                            onBlur={(event) => this.handlePatch(fields[0].field, event.nativeEvent.target.value)}
                        />
                    </div>
                )
            case "CostPrice":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.mandatory ? "input-mandatory " : "")
                    }>
                        <input
                            type="number"
                            className="input-field"
                            defaultValue={data.value}
                            disabled={data.readonly}
                            onBlur={(event) => this.handlePatch(fields[0].field, event.nativeEvent.target.value)}
                        />
                    </div>
                )
            case "YesNo":
                return (
                    <label className="input-checkbox">
                        <input
                            type="checkbox"
                            defaultValue={data.value}
                            disabled={data.readonly}
                            onClick={(event) => this.handlePatch(fields[0].field, event.nativeEvent.target.value)}
                        />
                        <div className="input-checkbox-tick"/>
                    </label>
                )
            case "Switch":
                return (
                    <label className="input-switch">
                        <input
                            type="checkbox"
                            checked={data.value}
                            disabled={data.readonly}
                            onChange={(event) => this.handlePatch(fields[0].field, event.nativeEvent.target.value)}
                        />
                        <div className="input-slider" />
                    </label>
                )
            case "Label":
                return (
                    <div className="tag tag-warning">{data.value}</div>
                )
            case "Button":
                return (
                    <button className="btn btn-sm btn-meta-primary">{data.value}</button>
                )
            default:
                return (
                    <div>{widgetType}</div>
                )
        }
    }
    findRowByPropName = (arr, name) => {
        let ret = -1;
        for(let i = 0; i < arr.length; i++){
            if(arr[i].field === name){
                ret = arr[i];
                break;
            }
        }

        return ret;
    }
    render() {
        const {caption, widgetType, description, fields, windowType, data, type} = this.props;
        const dataId = data[0].value;
        const widgetData = this.findRowByPropName(data, fields[0].field);
        if(widgetData.displayed){
            return (
                <div className="form-group row">
                    <div className="col-xs-12">
                        <div className={"form-group row " + (type === "primary" ? "" : "")}>
                            <div key="title" className={"form-control-label " + ((type === "primary") ? "col-sm-12 panel-title" : "col-sm-3")}>{caption}</div>
                            <div className={(type === "primary") ? "col-sm-12 " : "col-sm-9 "}>
                                {this.renderWidget(widgetType, fields, windowType, dataId, type, widgetData)}
                            </div>
                        </div>
                    </div>
                </div>
            )
        }else{
            return false;
        }
    }
}

Widget.propTypes = {
    dispatch: PropTypes.func.isRequired,
    data: PropTypes.array.isRequired,
};

function mapStateToProps(state) {
    const {windowHandler} = state;
    const {
        data
    } = windowHandler || {
        data: []
    }

    return {
        data
    }
}

Widget = connect(mapStateToProps)(Widget)

export default Widget
