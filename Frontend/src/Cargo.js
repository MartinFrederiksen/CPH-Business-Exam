import React, { useState, useEffect } from 'react';
import Facade from './login/ApiFacade' 

export default function Cargo() {
    const [cargoes, setCargoes] = useState([]);

    useEffect(() => {
        Facade.fetchCargo().then(res => setCargoes(res));
    }, [])

    const onDelete = (id) => {
        Facade.fetchCargoDelete(id);
    }

    return(
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
            {cargoes.map((cargo, index)=> <tr key={index}><td>{cargo.name}</td>
            <td>{cargo.weight}</td>
            <td>{cargo.units}</td>
            <td><button type="button" className="btn btn-dark" onClick={onDelete(cargo.id)}>Delete</button>
            <button type="button" className="btn btn-dark">Edit</button></td></tr> )}
          </tbody>
        </table>
      </div>
    )
}