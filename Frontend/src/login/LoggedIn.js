import React, { useState, useEffect } from 'react';
import facade from './ApiFacade'
import Delivery from '../Delivery'
import Cargo from '../Cargo'
import Truck from '../Truck'
import Driver from '../Driver'

export default function LoggedIn() {
  const [username, setUsername] = useState();
  const [role, setRole] = useState();
  const [name, setName] = useState();

  useEffect(() => {
    facade.fetchUser().then(res => { setUsername(res.userName); setRole(res.roleList) }).catch(e => console.log(e));
  }, [])

  const onClick = (evt) => {
    const name = evt.target.name;
    setName(name);
  }

  return (
    <div>
      <button type="button" className="btn btn-dark" name="delivery" onClick={onClick}>Delivery</button>
      <button type="button" className="btn btn-dark" name="cargo" onClick={onClick}>Cargo</button>
      <button type="button" className="btn btn-dark" name="truck" onClick={onClick}>Truck</button>
      <button type="button" className="btn btn-dark" name="driver" onClick={onClick}>Driver</button>
      {goTo(name)}

    </div>
  )
}

function goTo(name) {
  switch (name) {
    case "delivery":
      return (<Delivery />)
    case "cargo":
      return (<Cargo />)
    case "driver":
      return (<Driver />)
    case "truck":
      return (<Truck />)
  }
}