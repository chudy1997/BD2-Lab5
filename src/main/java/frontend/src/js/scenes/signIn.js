import React, { Component } from 'react';
import axios from 'axios';
import {BrowserRouter, Route, Link, Redirect} from 'react-router-dom';
import '../../styles/scenes.css';

class SignIn extends Component {
    componentWillMount(){
        this.setState({ login: '', password: '', invalid: false, loggedIn: false });
        axios.get('http://localhost:4000/loggedIn')
            .then((response) => this.setState({ loggedIn: response.data }));
        this.updateLogin = function(e){this.setState({ login: e.target.value, invalid: false });}.bind(this);
        this.updatePassword = function(e){this.setState({ password: e.target.value, invalid: false });}.bind(this);
        this.validate = function () {
            if(this.state.login.length === 0 || this.state.password.length === 0){
                this.setState({ login: '', password: '', invalid: true});}
            else{
                axios.post('http://localhost:4000/logIn/'+this.state.login+'/'+this.state.password)
                    .then((response) => this.setState({ login: '', password: '',loggedIn: true}))
                    .catch((err) => this.setState({ login: '', password: '', invalid: true}));}
        }.bind(this);

        this.logout = function () {
            axios.post('http://localhost:4000/logOut')
                .then((response) => this.setState({loggedIn: false}));}.bind(this);
    }

    render(){
        if(this.state && this.state.loggedIn){
            return <div className="sign-in-logged-in">
                <p className="sign-in-logged-in-info">You are logged in!</p><br/>
                <button className="sign-in-logout" onClick={this.logout}>Log out</button>
            </div>;
        }
        return <BrowserRouter>
            <div className="sign-in-not-logged-in">
                <div className="sign-in-login">
                    Login: <input className="sign-in-login-input" type="text" value={this.state.login} onChange={this.updateLogin}/><br/>
                </div>
                <div className="sign-in-password">
                    Password: <input className="sign-in-password-input" type="text" value={this.state.password} onChange={this.updatePassword}/><br/>
                </div>
                <button className="sign-in-confirm" onClick={this.validate}>Confirm</button>
                <p className="sign-in-warning" hidden={!this.state.invalid}>Invalid login or password, try again</p>
            </div>
        </BrowserRouter>;
    }
}

export default SignIn;