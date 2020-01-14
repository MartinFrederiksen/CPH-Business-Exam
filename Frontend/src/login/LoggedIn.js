import React, { useState, useEffect } from 'react';
import facade from './ApiFacade'
import { Link } from "react-router-dom";
import settings from '../settings'

export default function LoggedIn() {
  const [username, setUsername] = useState();
  const [role, setRole] = useState();

  useEffect(() => {
    facade.fetchUser().then(res => { setUsername(res.userName); setRole(res.roleList) }).catch(e => console.log(e));
  }, [])

  return (
    <div>
      <p>Username: {username}</p>
      <p>Role: {role}</p>
      <Link to={settings.getURL("Deliveries")} className="btn btn-dark">Delivery</Link>
      <Link to={settings.getURL("Cargoes")} className="btn btn-dark">Cargo</Link>
      <Link to={settings.getURL("Trucks")} className="btn btn-dark">Truck</Link>
      <Link to={settings.getURL("Drivers")} className="btn btn-dark">Driver</Link>
    </div>
  )
}