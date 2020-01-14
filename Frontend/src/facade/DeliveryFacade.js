import URLSettings from '../settings'
const URL = URLSettings.ServerUrl() + "/api/Delivery/";

//The two methods below, are the utility-methods introduced here (use them if you like):
//https://docs.google.com/document/d/1hF9P65v_AJKCjol_gFkm3oZ1eVTuOKc15V6pcb3iFa8/edit?usp=sharing
function makeOptions(method, body) {
  var opts = {
    method: method,
    headers: {
      "Content-type": "application/json",
      'Accept': 'application/json',
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

function DeliveryFacade() {
  //OBSERVE This returns a promise, NOT the actual data, you must handle asynchronicity by the client
  function getDelivery() {
    return fetch(URL + "all").then(handleHttpErrors);
  }

  function getDeliveryById(id) {
    return fetch(URL + id).then(handleHttpErrors);
  }

  function addDelivery(delivery) {
    //Complete me. A smart version will handle both Add and Edit, but focus on Add (POST) only first
    //return (person.id === "") ?
      fetch(URL + "add", makeOptions("POST", delivery)).then(handleHttpErrors) //:
      //fetch(URL + person.id, makeOptions("PUT", person)).then(handleHttpErrors);
  }

  function editDelivery(delivery) {
    fetch(URL + delivery.id, makeOptions("PUT", delivery)).then(handleHttpErrors);
  }

  function deleteDelivery(id) {
    //Complete me
    const options = makeOptions("DELETE")
    return fetch(URL + "delete/" + id, options).then(handleHttpErrors);
  }

  return {
    getDelivery,
    getDeliveryById,
    addDelivery,
    editDelivery,
    deleteDelivery
  };
}

export default DeliveryFacade();