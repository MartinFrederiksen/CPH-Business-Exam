import React, { useState, useEffect } from 'react';
import Facade from './facade/CargoFacade'
import { Link } from "react-router-dom";
import settings from './settings'

export default function Cargo() {
  const [cargoes, setCargoes] = useState([]);
  const [cargo, setCargo] = useState({ name: "", weight: "", units: "" });

  useEffect(() => {
    Facade.getCargo().then(res => setCargoes(res));
  }, [cargoes])

  const onSubmit = (evt) => {
    evt.preventDefault();
    Facade.addCargo(cargo)

  }

  const onChange = event => {
    const target = event.target;
    const value = target.value
    const name = target.name;
    setCargo({ ...cargo, [name]: value });
  };

  const onDelete = (id) => {
    Facade.deleteCargo(id);
  }

  const onEdit = (delivery) => {
    Facade.editDelivery(delivery);
  }

  return (
    <div className="container">
      <h3>Cargoes</h3>
      <table className="table">
        <thead className="thead-dark">
          <tr>
            <th scope="col">Name</th>
            <th scope="col">Weight</th>
            <th scope="col">Units</th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          {cargoes.map((cargo, index) => <tr key={index}><td>{cargo.name}</td><td>{cargo.weight}</td><td>{cargo.units}</td>
            <td><button type="button" className="btn btn-dark" onClick={() => onDelete(cargo.id)}>Delete</button>
            <button type="button" className="btn btn-dark" onClick={() => onEdit(cargo)}>Edit</button></td></tr>)}
        </tbody>
      </table>

      <form onSubmit={onSubmit}>
        <input readOnly id="id" value={cargo.id} />
        <input
          name="name"
          value={cargo.name}
          onChange={onChange}
          placeholder="Cargo name"
        />
        <input
          name="weight"
          value={cargo.weight}
          onChange={onChange}
          placeholder="Cargo weight"
        />

        <input
          name="units"
          value={cargo.units}
          onChange={onChange}
          placeholder="Cargo units"
        />
        <input type="submit" className="btn btn-dark" value="Add Cargo" />
      </form>
      <p>{cargo.name}, {cargo.weight}, {cargo.units}</p>
      <Link to={settings.getURL("LoggedIn")} className="btn btn-dark">Back</Link>
    </div>
  )
}