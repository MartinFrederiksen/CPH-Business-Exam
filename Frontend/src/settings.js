const URLs = {
    "Home": "/",
    "Info": "/info",
    "Login": "/login",
    "NoMatch": "*"
}

function URLSettings() {
    const getURL = (key) => { return URLs[key] }

    return {
        getURL
    }
}
export default URLSettings();


