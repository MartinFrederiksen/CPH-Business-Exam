import React, { useState, useEffect } from 'react';
import Facade from './login/ApiFacade';

export default function Info() {
    const [deliveries, setDeliveries] = useState([]);
    const [trucks, setTrucks] = useState([]);
    const [drivers, setDrivers] = useState([]);

    useEffect(() => {
      Facade.fetchDeliveries().then(res => setDeliveries(res));
      Facade.fetchTrucks().then(res => setTrucks(res));
      Facade.fetchDrivers().then(res => setDrivers(res));
    },[])
    return (
        <div className="container">
          <h3>Deliveries</h3>
          <table className="table">
            <thead className="thead-dark">
              <tr>
                <th scope="col">Shipping date</th>
                <th scope="col">From location</th>
                <th scope="col">Destination</th>
              </tr>
            </thead>
            <tbody>
              {deliveries.map((delivery, index)=> <tr key={index}><td>{delivery.shippingDate}</td><td>{delivery.fromLocation}</td><td>{delivery.destination}</td></tr> )}
            </tbody>
          </table>

          <h3>Trucks</h3>
          <table className="table">
            <thead className="thead-dark">
              <tr>
                <th scope="col">Name</th>
                <th scope="col">Capacity</th>
              </tr>
            </thead>
            <tbody>
              {trucks.map((truck, index)=> <tr key={index}><td>{truck.name}</td><td>{truck.capacity}</td></tr> )}
            </tbody>
          </table>

          <h3>Drivers</h3>
          <table className="table">
            <thead className="thead-dark">
              <tr>
                <th scope="col">Name</th>
              </tr>
            </thead>
            <tbody>
              {drivers.map((driver, index)=> <tr key={index}><td>{driver.name}</td></tr> )}
            </tbody>
          </table>

        </div>
      )
}
