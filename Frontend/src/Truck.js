import React, { useState, useEffect } from 'react';
import Facade from './facade/TruckFacade';
import { Link } from "react-router-dom";
import settings from './settings';

export default function Truck() {
    const [trucks, setTrucks] = useState([]);
    const [truck, setTruck] = useState({name: "", capacity: ""});

    useEffect(() => {
        Facade.getTruck().then(res => setTrucks(res));
    }, [trucks])

    const onSubmit = (evt) => {
        evt.preventDefault();
        Facade.addTruck(truck)
    }

    const onChange = event => {
    const target = event.target;
    const value = target.value
    const name = target.name;
    setTruck({...truck,[name]: value});
    };

    const onDelete = (id) => {
        Facade.deleteTruck(id);
    }

    const onEdit = (truck) => {
      Facade.editTruck(truck);
  }

    return(
        <div className="container">
        <h3>Trucks</h3>
        <table className="table">
          <thead className="thead-dark">
            <tr>
              <th scope="col">Name</th>
              <th scope="col">Capacity</th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody>
            {trucks.map((truck, index)=> <tr key={index}><td>{truck.name}</td><td>{truck.capacity}</td>
            <td><button type="button" className="btn btn-dark" onClick={() => onDelete(truck.id)}>Delete</button>
            <button type="button" className="btn btn-dark" onClick={() => onEdit(truck)}>Edit</button></td></tr> )}
          </tbody>
        </table>

        <form onSubmit={onSubmit}>
        <input readOnly id="id" value={truck.id} />
        <input
          name="name"
          value={truck.name}
          onChange={onChange}
          placeholder="Trucks name"
        />
        <input
          name="capacity"
          value={truck.capacity}
          onChange={onChange}
          placeholder="Trucks capacity"
        />
        <input type="submit" className="btn btn-dark" value="Add Truck" />
        </form>
        <p>{truck.name}, {truck.capacity}</p>
        <Link to={settings.getURL("LoggedIn")} className="btn btn-dark">Back</Link>
      </div>
    )
}