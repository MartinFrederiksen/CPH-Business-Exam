import React, { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import Facade from './facade/DeliveryFacade'
import settings from './settings'

export default function Truck() {
  const [deliveries, setDeliveries] = useState([]);
  const [delivery, setDelivery] = useState({ destination: "", fromLocation: "" });

  useEffect(() => {
    Facade.getDelivery().then(res => setDeliveries(res));
  }, [deliveries])

  const onSubmit = (evt) => {
    evt.preventDefault();
    Facade.addDelivery(delivery)

  }

  const onChange = event => {
    const target = event.target;
    const value = target.value
    const name = target.name;
    setDelivery({ ...delivery, [name]: value });
  };

  const onDelete = (id) => {
    Facade.deleteDelivery(id);
  }

  const onEdit = (delivery) => {
    Facade.editDelivery(delivery);
  }

  const sortFunc = () => {
      setDeliveries(deliveries.sort((a, b) => (a.destination > b.destination) ? 1 : -1));
  }

  return (
    <div className="container">
      <h3>Deliveries</h3>
      <table className="table">
        <thead className="thead-dark">
          <tr>
            <th scope="col"></th>
            <th scope="col">Destination</th>
            <th scope="col">From Location</th>
            <th scope="col">Shipping Date</th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          {deliveries.map((delivery, index) => <tr key={index}>
            <Link to={settings.getURL("DeliveryId") + "/" + (delivery.id)} className="btn btn-dark">Show details</Link>
            <td>{delivery.destination}</td><td>{delivery.fromLocation}</td><td>{delivery.shippingDate}</td>
            <td><button type="button" className="btn btn-dark" onClick={() => onDelete(delivery.id)}>Delete</button>
              <button type="button" className="btn btn-dark" onClick={() => onEdit(delivery)}>Edit</button></td></tr>)}
        </tbody>
      </table>

      <form onSubmit={onSubmit}>
        <input readOnly id="id" value={delivery.id} />
        <input
          name="destination"
          value={delivery.destination}
          onChange={onChange}
          placeholder="Delivery destination"
        />
        <input
          name="fromLocation"
          value={delivery.fromLocation}
          onChange={onChange}
          placeholder="Delivery from location"
        />
        <input type="submit" className="btn btn-dark" value="Add Delivery" />
      </form>
      <p>{delivery.destination}, {delivery.fromLocation}</p>
      <button type="button" className="btn btn-dark" onClick={() => sortFunc()}>SortShippinDate</button>
      <button type="button" className="btn btn-dark">SortFromLocation</button>
      <button type="button" className="btn btn-dark">SortDestination</button>
      <Link to={settings.getURL("LoggedIn")} className="btn btn-dark">Back</Link>
    </div>
  )
}