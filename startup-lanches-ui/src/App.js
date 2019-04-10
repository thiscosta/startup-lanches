import React, { Component } from 'react';
import './App.css';
import Alert from 'react-s-alert';

import { Provider } from 'react-redux'

import store from './configurations/store'
import routes from './routes'

import Header from './components/Header'
import Rodape from './components/Rodape'

import { BrowserRouter as Router, Route, Redirect } from "react-router-dom"

// mandatory
import 'react-s-alert/dist/s-alert-default.css';
// optional - you can choose the effect you want
import 'react-s-alert/dist/s-alert-css-effects/flip.css';

class App extends Component {

  render() {
    return (
      <Provider store={store}>

        <Router>
            <Header>
              {routes.map((route, i) => {
                return (
                  <Route key={i} path={route.path} component={route.component} />
                )
              })}
              <Route exact path="/" render={() => (<Redirect to="/cardapio" />)} /> 
            </Header>
            <Alert stack={{limit: 3}} />
          <Rodape />
        </Router>

      </Provider>
    );
  }
}

export default App;
