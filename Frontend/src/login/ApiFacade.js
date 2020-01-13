//const URL = "http://melif.dk/Exam"; //Deploy
const URL = "http://localhost:8080/exam"; //Local

function handleHttpErrors(res) {
    if (!res.ok) {
        return Promise.reject({ status: res.status, fullError: res.json() })
    }
    return res.json();
}

function ApiFacade() {
    //Insert utility-methods from a latter step (d) here
    const setToken = (token) => {
        localStorage.setItem('jwtToken', token)
    }
    const getToken = () => {
        return localStorage.getItem('jwtToken')
    }
    const loggedIn = () => {
        const loggedIn = getToken() != null;
        return loggedIn;
    }
    const logout = () => {
        localStorage.removeItem("jwtToken");
    }
    
    const makeOptions = (method, addToken, body) => {
        var opts = {
            method: method,
            headers: {
                "Content-type": "application/json",
                'Accept': 'application/json',
            }
        }
        if (addToken && loggedIn()) {
            opts.headers["x-access-token"] = getToken();
        }
        if (body) {
            opts.body = JSON.stringify(body);
        }
        return opts;
    }

    const login = (user, pass) => {
        const options = makeOptions("POST", true, { username: user, password: pass });
        return fetch(URL + "/api/login", options)
            .then(handleHttpErrors)
            .then(res => { setToken(res.token) })
    }

    const fetchUser = () => {
        const options = makeOptions("GET", true); //True add's the token
        return fetch(URL + "/api/info/user", options).then(handleHttpErrors);
    }

    const fetchData = () => {
        return fetch(URL + "/api/swapi/demo", makeOptions("GET")).then(handleHttpErrors);
    }

    const fetchTrucks = () => {
        return fetch(URL + "/api/Truck/all", makeOptions("GET")).then(handleHttpErrors);
    }

    const fetchDrivers = () => {
        return fetch(URL + "/api/Driver/all", makeOptions("GET")).then(handleHttpErrors);
    }

    const fetchDriverDelete = (id) => {
        return fetch(URL + "/api/Driver/delete/" + id, makeOptions("DELETE")).then(handleHttpErrors);
    }

    const fetchDriverDelete = (id) => {
        return fetch(URL + "/api/Driver/delete/" + id, makeOptions("DELETE")).then(handleHttpErrors);
    }

    const fetchDeliveries = () => {
        return fetch(URL + "/api/Delivery/all", makeOptions("GET")).then(handleHttpErrors);
    }
    
    const fetchCargo = () => {
        return fetch(URL + "/api/Cargo/all", makeOptions("GET")).then(handleHttpErrors);
    }

    const fetchCargoDelete = (id) => {
        return fetch(URL + "/api/Cargo/delete/" + id, makeOptions("DELETE")).then(handleHttpErrors);
    }

    return {
        login,
        logout,
        fetchUser,
        fetchData,
        fetchTrucks,
        fetchDrivers,
        fetchDriverDelete,
        fetchDeliveries,
        fetchCargo,
        fetchCargoDelete
    }

}

export default ApiFacade();