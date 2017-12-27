import React, { Component } from 'react';
import axios from 'axios';
import {BrowserRouter, Route, Link} from 'react-router-dom';
import '../../styles/scenes.css';
import product from './product';

class Products extends Component {
    componentWillMount(){
        axios.post('http://localhost:4000/transactions/new');
        axios.get('http://localhost:4000/products')
            .then((response) => this.setState({ products: response.data}))
            .catch(() => this.setState({ products: [] }));
    }

    render(){
        if(this.state && this.state.products.length > 0){
            return <BrowserRouter>
                <div className="products">
                    <ul className="products-list">
                        {
                            this.state.products.map(p => {
                                const link = "/products/"+p.id;
                                const prodName = p.obj;

                                return <li className="products-list-item">
                                    <Link to={link}>{prodName}
                                        <Route path={link} component={product}/>
                                    </Link>
                                </li>;
                            })
                        }
                    </ul>
                </div>
            </BrowserRouter>
        }
        else if(this.state && this.state.products.length === 0)
            return <div className="error">Products are not available</div>;

        return <div></div>
    }
}

export default Products;