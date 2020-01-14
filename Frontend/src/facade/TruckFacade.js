import URLSettings from '../settings'
const URL = URLSettings.ServerUrl() + "/api/Truck/";

//The two methods below, are the utility-methods introduced here (use them if you like):
//https://docs.google.com/document/d/1hF9P65v_AJKCjol_gFkm3oZ1eVTuOKc15V6pcb3iFa8/edit?usp=sharing
function makeOptions(method, body) {
  var opts = {
    method: method,
    headers: {
      "Content-type": "application/json"
    }
  };
  if (body) {
    opts.body = JSON.stringify(body);
  }
  return opts;
}

function handleHttpErrors(res) {
  if (!res.ok) {
    return Promise.reject({ status: res.status, fullError: res.json() });
  }
  return res.json();
}

function TruckFacade() {
  //OBSERVE This returns a promise, NOT the actual data, you must handle asynchronicity by the client
  function getTruck() {
    return fetch(URL + "all").then(handleHttpErrors);
  }

  function addTruck(truck) {
    //Complete me. A smart version will handle both Add and Edit, but focus on Add (POST) only first
    //return (truck.id === "") ? 
    fetch(URL + "add", makeOptions("POST", truck)).then(handleHttpErrors) //:
    //fetch(URL + truck.id, makeOptions("PUT", truck)).then(handleHttpErrors);
  }

  function editTruck(truck) {
    fetch(URL + truck.id, makeOptions("PUT", truck)).then(handleHttpErrors);
  }

  function deleteTruck(id) {
    //Complete me
    const options = makeOptions("DELETE")
    return fetch(URL + "delete/" + id, options).then(handleHttpErrors);
  }

  return {
    getTruck,
    addTruck,
    editTruck,
    deleteTruck
  };
}

export default TruckFacade();