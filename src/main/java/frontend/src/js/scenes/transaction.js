import React, { Component } from 'react';
import axios from 'axios';
import {BrowserRouter, Route, Link} from 'react-router-dom';
import '../../styles/scenes.css';

class Transaction extends Component {
    componentWillMount(){
        const pars = this.props.location.pathname.split('/');
        const id = pars[pars.length - 1];
        console.log(pars);

        axios.get('http://localhost:4000/loggedIn')
            .then((response) => this.setState({ loggedIn: response.data }));

        axios.get('http://localhost:4000/transactions/'+id+'/products')
            .then((response) => this.setState({ transactionDetails: response.data }))
            .catch(() => this.setState({ transactionDetails: [] }));

        axios.get('http://localhost:4000/transactions/'+id+'/cost')
            .then((response => this.setState({ cost: response.data })))
            .catch(() => this.setState({ cost: 0 }));
    }

    render(){
        if(this.state && this.state.loggedIn){
            if(this.state.cost === 0)
                return <div className="transaction-empty">Transaction is empty</div>;

            if(this.state.transactionDetails && this.state.cost != undefined){
                return <div className="transaction">
                    <p className="transaction-cost">Koszt calkowity: {this.state.cost} zlotych</p>
                    <ol className="transaction-products-list">
                        {
                            this.state.transactionDetails.map(td => {
                                return <li className="transaction-product">
                                    Nazwa: {td[0]}
                                    <ul className="transaction-product-details-list">
                                        <li className="transaction-product-details-list-item">Ilosc: {td[1]}</li>
                                        <li className="transaction-product-details-list-item">Koszt: {td[2]}</li>
                                    </ul>
                                </li>
                            })
                        }
                    </ol>
                </div>
            }
        }
        else if(this.state && !this.state.loggedIn){
            return <div className="transaction-not-logged-in">
                Log in to get access to your transaction
            </div>
        }
        return <div></div>
    }
}

export default Transaction;