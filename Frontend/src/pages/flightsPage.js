import BaseClass from '../util/baseClass';
import DataStore from '../util/DataStore';
import FlightClient from "../api/flightClient";


class FlightsPage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onSubmit', 'onLoad', 'renderFlights'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch teh concert list.
     */
    mount() {
        document.getElementById('results').addEventListener('load', onLoad);
        document.getElementById('button').addEventListener('click', this.onSubmit);

        this.client = new FlightsClient();
        this.dataStore.addChangeListener(this.renderFlights)
        this.fetchFlights();
    }

    async fetchFlights() {
        const responses = this.dataStore.get("searchParam");
        let depart = responses[0];
        let arrive = responses[1];
        let date = responses[2];

        const flights = await this.client.searchFlights(date, depart, arrive, errorHandler);

        if (flights && flights.length > 0) {
            for (const flight of flights) {
                flight.reservations = await this.fetchReservations(flight.id);
                flight.purchases = await this.fetchPurchases(flight.id);
            }
        }
        this.dataStore.set("flights", flights);


//        const flights = await this.client.getFlights(this.errorHandler)
//
//        if (flights && flights.length > 0) {
//            for (const flight of flights) {
//                flight.reservations = await this.fetchReservations(flight.id);
//                flight.purchases = await this.fetchPurchases(flight.id);
//            }
//        }
//        this.dataStore.set("flights", flights);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    renderFlights() {
        let flightHtml = "";
        const flights = this.dataStore.get("flights");

        if (flights) {
            for (const flight of flights) {
                flightHtml += `
                    <div class="card">
                        <h2>${flight.name}</h2>
                        <div>Date: ${flight.date}</div>
                        <div>Base Price: ${this.formatCurrency(flight.ticketBasePrice)}</div>
                        <p>
                            <h3>Ticket Reservations</h3>
                            <ul>
                `;
                if (flight.reservations && flight.reservations.length > 0) {
                    for (const reservation of flight.reservations) {
                        flightHtml += `
                                <li>
                                    <div>Ticket ID: ${reservation.ticketId}</div>
                                    <div>Date Reserved: ${reservation.dateOfReservation}</div>
                                    <div>Reservation Closed: ${reservation.reservationClosed}</div>
                                    <div>Date Reservation Closed: ${reservation.dateReservationClosed}</div>
                                    <div>Ticket Purchased: ${reservation.purchasedTicket}</div>
                                </li>
                        `;
                    }
                } else {
                    flightHtml += `
                                <li>No Ticket Reservations.</li>
                    `;
                }
                flightHtml += `
                            </ul>
                        </p>
                        <p>
                            <h3>Ticket Purchases</h3>
                            <ul>
                `;
                if (flight.purchases && flight.purchases.length > 0) {
                    for (const purchase of flight.purchases) {
                        flightHtml += `
                                <li>
                                    <div>Ticket ID: ${purchase.ticketId}</div>
                                    <div>Date Purchased: ${purchase.dateOfPurchase}</div>
                                    <div>Price Paid: ${purchase.pricePaid}</div>
                                </li>
                        `;
                    }
                } else {
                    flightHtml += `
                                <li>No Ticket Purchases.</li>
                    `;
                }
                flightHtml += `
                            </ul>
                        </p>
                    </div>`;
            }
        } else {
            flightHtml = `<div>There are no flights...</div>`;
        }

        document.getElementById("flight-list").innerHTML = flightHtml;
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    onLoad() {
        this.fetchFlights();
    }

    /**
     * Method to run when the create playlist submit button is pressed. Call the MusicPlaylistService to create the
     * playlist.
     */
    async onSubmit(event) {
        // Prevent the form from refreshing the page
        event.preventDefault();

        // Set the loading flag
        let reserveButton = document.getElementById('button');
        reserveButton.innerText = 'Loading...';
        reserveButton.disabled = true;

        // Get the values from the form inputs
        const flightId = document.getElementById('flight_id').value;

        // Reserve the flight
        const reservedFlight = await this.client.reserveTicket(flightId);

        // Reset the form
        document.getElementById('results').reset();
        document.getElementById('flight_id').reset();

        // Re-enable the form
        reserveButton.innerText = 'Reserve Ticket';
        reserveButton.disabled = false;
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const flightsPage = new FlightsPage();
    flightsPage.mount();
};

window.addEventListener('DOMContentLoaded', main);