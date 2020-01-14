import React, { useState, useEffect } from 'react';
import Facade from './facade/DriverFacade';
import { Link } from "react-router-dom";
import settings from './settings';

export default function Driver() {
    const [drivers, setDrivers] = useState([]);
    const [driver, setDriver] = useState({name: ""});

    useEffect(() => {
        Facade.getDriver().then(res => setDrivers(res));
    }, [drivers])

    const onSubmit = (evt) => {
        evt.preventDefault();
        Facade.addDriver(driver)
        
    }

    const onChange = event => {
    const target = event.target;
    const value = target.value
    const name = target.name;
    setDriver({...driver,[name]: value});
    };

    const onDelete = (id) => {
        Facade.deleteDriver(id);
    }

    const onEdit = (driver) => {
      Facade.deleteDriver(driver);
  }

    return(
        <div className="container">
        <h3>Drivers</h3>
        <table className="table">
          <thead className="thead-dark">
            <tr>
              <th scope="col">Name</th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody>
            {drivers.map((driver, index)=> <tr key={index}><td>{driver.name}</td>
            <td><button type="button" className="btn btn-dark" onClick={() => onDelete(driver.id)}>Delete</button>
            <button type="button" className="btn btn-dark" onClick={() => onEdit(driver)}>Edit</button></td></tr> )}
          </tbody>
        </table>

        <form onSubmit={onSubmit}>
        <input readOnly id="id" value={driver.id} />
        <input
          name="name"
          value={driver.name}
          onChange={onChange}
          placeholder="Drivers name"
        />
        <input type="submit" className="btn btn-dark" value="Add Driver" />
        </form>
        <p>{driver.name}</p>
        <Link to={settings.getURL("LoggedIn")} className="btn btn-dark">Back</Link>
      </div>
    )
}