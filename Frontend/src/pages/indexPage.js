import BaseClass from '../util/baseClass';
import DataStore from '../util/DataStore';
import FlightClient from "../api/flightClient";

/**
 * Logic needed for the create flightList page of the website.
 */
class IndexPage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onSubmit', 'onRefresh', 'onClick', 'renderFlights'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the flight list.
     */
    mount() {
        document.getElementById('reserve-button').addEventListener('click', this.onClick);
        // collecting elements from the form
        document.getElementById('submit-button').addEventListener('click', this.onSubmit);

        this.client = new FlightClient();
        this.dataStore.addChangeListener(this.renderFlights)
//        this.fetchFlights();
    }

    async fetchFlights(departureLocation,arrivalLocation,date,errorHandler1) {
        console.log(departureLocation, arrivalLocation, date);
        const flights = await this.client.getFlights(departureLocation,arrivalLocation,date,errorHandler1);
        this.dataStore.set("flights", flights);
    }

    async reserveFlight(flightId, errorHandler1) {
        const reservedFlight = await this.client.reserveTicket(flightId, errorHandler1);

        // Cookies - how to - https://www.w3schools.com/js/js_cookies.asp
        document.cookie = flightId;
        let x = document.cookie;
        document.cookie = "ticket=; expires=Thu, 01 Jan 1970 00:00:00 UTC;";
        console.log(x);

        window.location='checkout.html';
    }

    // Render Methods --------------------------------------------------------------------------------------------------
    renderFlights() {
        let flightHtml = "";
        const flights = this.dataStore.get("flights");
        console.log(flights);
        if (flights) {
            for (const flight of flights) {
                flightHtml += `
                    <div class="card">

                        <div><b>FlightId:</b> ${flight.flightId}</div>
                        <div><b>Date:</b> ${flight.date}</div>
                        <div><b>DepartureLocation:</b> ${flight.departureLocation}</div>
                        <div><b>ArrivalLocation:</b> ${flight.arrivalLocation}</div>
                        <br/>
                    </div>`;

//                            <h3>Ticket Reservations</h3>
//                            <ul>
//                `;
//                if (flight.reservations && flight.reservations.length > 0) {
//                    for (const reservation of flight.reservations) {
//                        flightHtml += `
//                                <li>
//                                    <div>Ticket ID: ${reservation.ticketId}</div>
//                                    <div>Date Reserved: ${reservation.dateOfReservation}</div>
//                                    <div>Reservation Closed: ${reservation.reservationClosed}</div>
//                                    <div>Date Reservation Closed: ${reservation.dateReservationClosed}</div>
//                                    <div>Ticket Purchased: ${reservation.purchasedTicket}</div>
//                                </li>
//                        `;
//                    }
//                } else {
//                    flightHtml += `
//                                <li>No Ticket Reservations.</li>
//                    `;
//                }
//                flightHtml += `
//                            </ul>
//                        </p>
//                        <p>
//                            <h3>Ticket Purchases</h3>
//                            <ul>
//                `;
//                if (flight.purchases && flight.purchases.length > 0) {
//                    for (const purchase of flight.purchases) {
//                        flightHtml += `
//                                <li>
//                                    <div>Ticket ID: ${purchase.ticketId}</div>
//                                    <div>Date Purchased: ${purchase.dateOfPurchase}</div>
//                                    <div>Price Paid: ${purchase.pricePaid}</div>
//                                </li>
//                        `;
//                    }
//                } else {
//                    flightHtml += `
//                                <li>No Ticket Purchases.</li>
//                    `;
//                }


            }
        } else {
            flightHtml = `<div>There are no flights...</div>`;
        }

        document.getElementById("flights").innerHTML = flightHtml;
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    onRefresh() {
        this.fetchFlights();
    }

    async onClick(event) {
        // Prevent button from refreshing the page
        event.preventDefault();
        console.log("click");
        // Get the value from the flight-id input
        const flightId = document.getElementById('flight-id').value;
        console.log("click again");
        // Reserve the flight
        this.reserveFlight(flightId, this.errorHandler);

        document.getElementById("flight-id").value = "";
    }
    /**
     * Method to run when the search flights submit button is pressed.
     */
    async onSubmit(event) {
        // Prevent the form from refreshing the page
        event.preventDefault();

        // Get the values from the form inputs
        const flightName = "testFlight";
        const date = document.getElementById('depart_date').value;
        const  departureLocation = document.getElementById('from_location').value;
        const  arrivalLocation = document.getElementById('to_location').value

        // Create the flight
        const flight = await this.client.createFlight(flightName, date, departureLocation,arrivalLocation,this.errorHandler);

        this.fetchFlights(departureLocation,arrivalLocation,date, this.errorHandler);
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const indexPage = new IndexPage();
    indexPage.mount();
};

window.addEventListener('DOMContentLoaded', main);