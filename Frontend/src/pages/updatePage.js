import BaseClass from '../util/baseClass';
import DataStore from '../util/DataStore';
import UpdateClient from "../api/updateClient";

/**
 * Logic needed for the create flightList page of the website.
 */
class UpdatePage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onSubmit'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers.
     */
    mount() {
    // collecting elements from the form
        document.getElementById('submit').addEventListener('click', this.onSubmit);

        this.client = new UpdateClient();
    }

    /*
     * Method to get flight by entered id and update the contents.
     */
    async fetchFlights(flightId1, errorHandler1) {
        console.log(flightId1);
        const flight = await this.client.getFlight(flightId1,errorHandler1);
        console.log(flight);
        document.cookie = flightId1;
        console.log(document.cookie);

        const departLocation = flight.departureLocation;
        const arriveLocation = flight.arrivalLocation;
        const launchDate = flight.date;

        const updatedFlight = await this.client.updateFlight(flightId1, departLocation, arriveLocation, launchDate, errorHandler1);

        window.location = 'update_conf.html';
    }

    /**
     * Method to run when the confirm updates submit button is pressed.
     */
    async onSubmit(event) {
        // Prevent the form from refreshing the page
        event.preventDefault();

        // Get the values from the form input
        const flightId = document.getElementById('flight-id').value;

        this.fetchFlights(flightId, this.errorHandler);
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const updatePage = new UpdatePage();
    updatePage.mount();
};

window.addEventListener('DOMContentLoaded', main);