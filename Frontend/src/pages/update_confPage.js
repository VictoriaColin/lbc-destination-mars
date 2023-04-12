import BaseClass from '../util/baseClass';
import DataStore from '../util/DataStore';
import Update_ConfClient from "../api/update_confClient";

/**
 * Logic needed for the checkout page of the website.
 */
class Update_ConfPage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['renderFlights'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the flight list.
     */
    mount() {
        this.client = new Update_ConfClient();
        this.dataStore.addChangeListener(this.renderFlights);
        this.fetchFlight();
        this.getUUID();
    }

    /*
     * Method runs once page is loaded. Brings in flight information depending on which
     * flightId was selected on search screen.
     */
    async fetchFlight() {
        // Retrieve cookie.
        let flightInfo = localStorage.getItem("flightId");

        // Retrieve the flight by flightId
        const flight = await this.client.getFlight(flightInfo, this.errorHandler);
        this.dataStore.set("flights", flight);
    }

    async getUUID() {
        console.log("entered")
        // https://www.educative.io/answers/how-to-create-a-random-uuid-in-javascript
        const uuid = crypto.randomUUID();

        this.dataStore.set("tickets", uuid);
    }

    // Render Methods --------------------------------------------------------------------------------------------------
    renderFlights() {
        let flightHtml = "";
        const flight = this.dataStore.get("flights");
        console.log(flight);
        if(flight){
            flightHtml += `
                <div class="card">
                    <br/>
                    <div><b>FlightId:</b> ${flight.flightId}</div>
                    <div><b>Date:</b> ${flight.date}</div>
                    <div><b>DepartureLocation:</b> ${flight.departureLocation}</div>
                    <div><b>ArrivalLocation:</b> ${flight.arrivalLocation}</div>
                    <br/>
                </div>`;
        } else {
            flightHtml = `<div>There is no flight...</div>`;
        }


        const ticketNum = this.dataStore.get("tickets");
        let ticketHtml = "";
        if(ticketNum) {
            ticketHtml += `
                <div class ="card2">
                    <div><b>TicketId:</b> ${ticketNum}</div>
                </div>`;
        }

        console.log(ticketHtml);


        document.getElementById("flight-info").innerHTML = flightHtml;
        document.getElementById("ticket-number").innerHTML = ticketHtml;
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const update_ConfPage = new Update_ConfPage();
    update_ConfPage.mount();
};

window.addEventListener('DOMContentLoaded', main);