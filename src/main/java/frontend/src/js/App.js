import React, { Component } from 'react';
import '../styles/App.css';
import {BrowserRouter, Route, Link} from 'react-router-dom';
import {home, transactions, products, logIn} from './scenes/index'

class App extends Component {
  render() {
    return (
        <BrowserRouter>
            <div className="App">
                <Link className="App-nav-item-home" to="/home">
                    <header className="App-header">
                        <h1 className="App-title">The best shop online</h1>
                    </header>
                    <div className="App-nav">
                        <Link className="App-nav-item" to="/transactions">Transactions</Link>
                        <Link className="App-nav-item" to="/products">Products</Link>
                        <Link className="App-nav-item" to="/logIn">Log in</Link>
                    </div>
                </Link>
                <div className="App-main">
                    <Route path="/home" component={home}/>
                    <Route path="/transactions" component={transactions}/>
                    <Route path="/products" component={products}/>
                    <Route path="/logIn" component={logIn}/>
                </div>
                <footer className="App-footer">&copy; Cracow 2017</footer>
            </div>
        </BrowserRouter>
    );
  }
}

export default App;
