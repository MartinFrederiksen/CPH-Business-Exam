const URLs = {
    "Home": "/",
    "Info": "/info",
    "Login": "/login",
    "LoggedIn": "/loggedIn",
    "Deliveries": "/deliveries",
    "DeliveryId": "/delivery",
    "Trucks": "/trucks",
    "Cargoes": "/cargoes",
    "Drivers": "/drivers",
    "NoMatch": "*"
}

function URLSettings() {
    const getURL = (key, param = null) => { return URLs[key] + (param !== null ? "/:" + param : "") }

    const ServerUrl = () => "https://melif.dk/Exam"
    //const ServerUrl = () =>  "localhost:8080/exam/api/"

    return {
        getURL,
        ServerUrl
    }
}
export default URLSettings();


