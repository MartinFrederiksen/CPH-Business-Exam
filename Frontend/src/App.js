import React from 'react';
import { BrowserRouter as Router, Switch, Route, NavLink } from "react-router-dom";
import './style/App.css';
import LoginForm from './login/LoginForm';
import LoggedIn from './login/LoggedIn'
import URLSettings from './settings'
import Info from './Info';
import Delivery from './Delivery'
import Cargo from './Cargo'
import Truck from './Truck'
import Driver from './Driver'
import DeliveryDetails from './DeliveryDetails'

function App() {


  return (
    <div className="App">
      <Router>
        <Header />
        <Switch>
          <Route exact path={URLSettings.getURL("Home")}> <Welcome /> </Route>
          <Route path={URLSettings.getURL("Login")}> <LoginForm /> </Route>
          <Route path={URLSettings.getURL("LoggedIn")}> <LoggedIn /> </Route>
          <Route path={URLSettings.getURL("Info")}> <Info /> </Route>
          <Route path={URLSettings.getURL("Deliveries")}> <Delivery /> </Route>
          <Route path={URLSettings.getURL("DeliveryId", "id")}> <DeliveryDetails /> </Route>
          <Route path={URLSettings.getURL("Trucks")}> <Truck /> </Route>
          <Route path={URLSettings.getURL("Drivers")}> <Driver /> </Route>
          <Route path={URLSettings.getURL("Cargoes")}> <Cargo /> </Route>
          <Route path={URLSettings.getURL("NoMatch")}> <NoMatch /> </Route>
        </Switch>
        <Footer />
      </Router>
    </div>
  )
}

const Header = () => {
  return (
    <ul className="header">
      <li><NavLink activeClassName="active" exact to={URLSettings.getURL("Home")}>Home</NavLink></li>
      <li><NavLink activeClassName="active" to={URLSettings.getURL("Info")}>Info</NavLink></li>
      <li><NavLink activeClassName="active" to={URLSettings.getURL("Login")}>Login</NavLink></li>
    </ul>
  )
}

const Footer = () => {
  return (
    <footer>
      <div className="d-flex justify-content-center align-items-center">
        <span> © Copyright 2019 - Martin Frederiksen.</span>
      </div>
    </footer>
  )
}

const NoMatch = () => <div>No match!</div>

//If Welcome function reaches about 10 lines of code place the function in separate file.
function Welcome() {
  return (
    <div className="d-flex justify-content-center align-items-center link">
      <a href="https://github.com/asgerhs/CA-3/blob/master/README.md">Press me for quick start guide!!</a>
    </div>
  )
}

export default App;