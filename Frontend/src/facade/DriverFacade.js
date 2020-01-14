import URLSettings from '../settings'
const URL = URLSettings.ServerUrl() + "/api/Driver/";

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

function DriverFacade() {
  //OBSERVE This returns a promise, NOT the actual data, you must handle asynchronicity by the client
  function getDriver() {
    return fetch(URL + "all").then(handleHttpErrors);
  }

  function addDriver(driver) {
    //Complete me. A smart version will handle both Add and Edit, but focus on Add (POST) only first
    //return (person.id === "") ? 
    fetch(URL + "add", makeOptions("POST", driver)).then(handleHttpErrors); //:
    //fetch(URL + person.id, makeOptions("PUT", person)).then(handleHttpErrors);
  }

  function editDriver(driver) {
    fetch(URL + driver.id, makeOptions("PUT", driver)).then(handleHttpErrors);
  }

  function deleteDriver(id) {
    //Complete me
    const options = makeOptions("DELETE")
    return fetch(URL + "delete/" + id, options).then(handleHttpErrors);
  }

  return {
    getDriver,
    addDriver,
    editDriver,
    deleteDriver
  };
}

export default DriverFacade();