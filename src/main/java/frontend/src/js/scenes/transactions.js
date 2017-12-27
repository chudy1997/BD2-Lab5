import React, { Component } from 'react';
import axios from 'axios';
import {BrowserRouter, Route, Link} from 'react-router-dom';
import '../../styles/scenes.css';
import transaction from './transaction';

class Transactions extends Component {
    componentWillMount() {
        axios.get('http://localhost:4000/loggedIn')
            .then((response) => this.setState({ loggedIn: response.data }));

        axios.get('http://localhost:4000/transactions')
            .then((response) => this.setState({ transactions: response.data }))
            .catch(() => this.setState({ transactions: null }));
    }

    render(){
        if(this.state && this.state.loggedIn && this.state.transactions){
            if(this.state.transactions.length > 0)
                return(<BrowserRouter>
                    <div className="transactions">
                        <ul className="transactions-list">
                            {
                                this.state.transactions.map(t => {
                                    const link = "/transactions/"+t;
                                    return <li className="transactions-list-item">
                                        <Link to={link}>Transaction number {t}
                                            <Route path={link} component={transaction}/>
                                        </Link>
                                    </li>;
                                })
                            }
                        </ul>
                    </div>
                </BrowserRouter>);
            return <div className="transactions-no-transactions">You don't have any transaction</div>;

        }
        else if(this.state && !this.state.loggedIn && this.state.transactions){
            return <div className="transactions-not-logged-in">
                Log in to get access to your transactions
            </div>
        }
        else if(this.state && this.state.loggedIn && this.state.transactions === null)
            return <div className="error">Transactions are not available</div>;

        return <div></div>
    }
}

export default Transactions;