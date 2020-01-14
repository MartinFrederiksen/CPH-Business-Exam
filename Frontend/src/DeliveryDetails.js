import React, { useState, useEffect } from 'react';
import { useRouteMatch, Link } from 'react-router-dom';
import Facade from './facade/DeliveryFacade'
import settings from './settings' 

export default function ProductDetails() {
    let match = useRouteMatch();
    const [delivery, setDelivery] = useState({destination: "", fromLocation: ""});

    useEffect(() => {
        Facade.getDeliveryById(match.params.id).then(res => setDelivery(res));
    }, [match.params.id])

    return(
        <div className="container">
            <h1>Details for delivery with id: {match.params.id}</h1>
            <table className="table">
              <tr><td>Destination:</td><td>{delivery.destination}</td></tr>
              <tr><td>From Location:</td><td>{delivery.fromLocation}</td></tr>
              <tr><td>Shipping Date:</td><td>{delivery.shippingDate}</td></tr>
            </table>
            <Link to={settings.getURL("Deliveries")} className="btn btn-dark">Back</Link>
        </div>
    )
}