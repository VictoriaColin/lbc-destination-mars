import BaseClass from '../util/baseClass';
import DataStore from '../util/DataStore';
import ConcertClient from "../api/searchClient";

/**
 * Logic needed for the search flights page of the website.
 */
class SearchAdmin extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onSubmit'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the flight list.
     */
    mount() {
        document.getElementById('button').addEventListener('click', this.onSubmit);

        this.client = new SearchClient();
        this.dataStore.addChangeListener(this.renderFlights)
        this.fetchFlights();
    }

    async fetchFlights() {
        const flights = await this.client.getFlights(this.errorHandler)

        if (flights && flights.length > 0) {
            for (const flight of flights) {
                flight.reservations = await this.fetchReservations(flight.id);
                flight.purchases = await this.fetchPurchases(flight.id);
            }
        }
        this.dataStore.set("flights", flights);
    }
    // Event Handlers --------------------------------------------------------------------------------------------------

    /**
     * Method to run when the submit button is clicked. Send user to the next page with results.
     */
    async onSubmit(event) {
        // Prevent the form from refreshing the page
        event.preventDefault();

        // Set the loading flag
        let createButton = document.getElementById('create-concert');
        createButton.innerText = 'Loading...';
        createButton.disabled = true;

        // Get the values from the form inputs
        var getFromLocation = document.getElementById('from_location').selectedOptions[0].value;
        alert(getFromLocation);

        var getToLocation = document.getElementById('to_location').selectedOptions[0].value;
        alert(getToLocation);

        const date = document.getElementById('date').value;

        // Save data from form to pass to next page
        const flightInformation = [getFromLocation, getToLocation, date];
        this.dataStore.setItem("searchParam", flightInformation);

//
//        // Reset the form
//        document.getElementById("from_location").reset();
//        document.getElementById("to_location").reset();
//        document.getElementById("date").reset();
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const searchAdmin = new SearchAdmin();
    searchAdmin.mount();
};

window.addEventListener('DOMContentLoaded', main);