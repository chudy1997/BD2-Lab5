import React, { Component } from 'react';
import axios from 'axios';
import {BrowserRouter, Route, Link} from 'react-router-dom';
import '../../styles/scenes.css';
import { Button } from 'react';

class Product extends Component {
    componentWillMount(){
        const pars = this.props.location.pathname;
        const id = pars[pars.length - 1];
        axios.get('http://localhost:4000/loggedIn')
            .then((response) => this.setState({ amount: 0, loggedIn: response.data, id: id }));
        axios.get('http://localhost:4000/products/'+id)
            .then((response) => this.setState({ details: response.data.obj }))
            .catch(() => this.setState({ details: [] }));

        this.addToBasket = function(){
            axios.post('http://localhost:4000/products/'+this.state.id+'/'+this.state.amount); //catch
            const newDetails = this.state.details;
            newDetails[2] -= this.state.amount;
            this.setState({ details: newDetails, amount: 0 });
        }.bind(this);

        this.updateValue = function(e){ this.setState({ amount: parseInt(e.target.value) }); }.bind(this);
    }

    render(){
        if(this.state && this.state.details && this.state.id){
            let purchase = this.state.loggedIn
                ? <div className="product-purchase">
                    <p>Koszt: { parseFloat(this.state.details[3])*this.state.amount } zlotych</p>
                    <input className="product-purchase-input" type="number" value={this.state.amount} onChange={this.updateValue} min="0" max={this.state.details[2]}/><br/>
                    <button className="product-purchase-confirm" onClick={this.addToBasket}>Zamow</button></div>

                : <div className="product-purchase-not-logged-in">
                    <p className="product-purchase-log-in">Log in to get access to buying section</p>
                </div>;

            return <div className="product">
                <div className="product-info">
                    <p className="product-info-item">Opis: {this.state.details[1]}</p>
                    <p className="product-info-item">Ilość: {this.state.details[2]}</p>
                    <p className="product-info-item">Cena za sztuke: {this.state.details[3]} zlotych</p>
                    <p className="product-info-item">Kategoria: {this.state.details[4]}</p>
                    <p className="product-info-item">Producent: {this.state.details[5]}</p>
                </div>
                {purchase}
            </div>;
        }
        else if(this.state && this.state.details && this.state.details.length === 0)
            return <div className="error">No details available</div>;

        return <div></div>
    }
}

export default Product;