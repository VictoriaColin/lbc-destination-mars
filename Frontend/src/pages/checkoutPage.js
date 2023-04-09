import BaseClass from '../util/baseClass';
import DataStore from '../util/DataStore';
import FlightClient from "../api/flightClient";

/**
 * Logic needed for the checkout page of the website.
 */
class CheckoutPage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['renderFlights'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the flight list.
     */
    mount() {
        // collecting elements from the form
        document.getElementById('suit_options').addEventListener('click', this.onClick);
        document.getElementById('baggage_options').addEventListener('click', this.onClick);
        document.getElementById('food_options').addEventListener('click', this.onClick);

        this.client = new FlightClient();
        this.dataStore.addChangeListener(this.renderFlights);
        this.fetchFlight();
    }

    async fetchFlight() {
        let flightInfo = document.cookie;
        console.log(flightInfo);

        const flight = await this.client.getFlight(flightInfo, this.errorHandler);
        this.dataStore.set("flights", flight);
    }

    // Render Methods --------------------------------------------------------------------------------------------------
    renderFlights() {
        let flightHtml = "";
        const flight = this.dataStore.get("flights");
        console.log(flight);
        if(flight){
            flightHtml += `
                <div class="card">
                    <div><b>FlightId:</b> ${flight.flightId}</div>
                    <div><b>Date:</b> ${flight.date}</div>
                    <div><b>DepartureLocation:</b> ${flight.departureLocation}</div>
                    <div><b>ArrivalLocation:</b> ${flight.arrivalLocation}</div>
                    <br/>
                </div>`;
        } else {
            flightHtml = `<div>There is no flight...</div>`;
        }

        document.getElementById("reserved-flight").innerHTML = flightHtml;
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    onRefresh() {
        console.log("checking");
        this.fetchFlight();
    }

    /*
     *Method to run when dropdown box items have changed.
     */
    async onClick(event) {
        // Prevent button from refreshing the page
        event.preventDefault();
        // Get the value from the flight-id input
        let suit = +document.getElementById('suit_options').value;
        let bag = +document.getElementById('baggage_options').value;
        let food = +document.getElementById('food_options').value;

        let total = 100000;
        total += suit;
        total += bag;
        total += food;

        let commas = total.toLocaleString("en-US", {style: 'currency', currency: 'USD'});

        document.getElementById("price").innerHTML = commas;
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const checkoutPage = new CheckoutPage();
    checkoutPage.mount();
};

window.addEventListener('DOMContentLoaded', main);