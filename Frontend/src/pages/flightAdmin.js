import BaseClass from '../util/baseClass';
import DataStore from '../util/DataStore';
import FlightClient from "../api/flightClient";

/**
 * Logic needed for the create playlist page of the website.
 */
class FlightAdmin extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onSubmit', 'onRefresh', 'renderFlights'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch teh concert list.
     */
    mount() {
        document.getElementById('refresh').addEventListener('click', this.onRefresh);
        // collecting elements from the form
        document.getElementById('create-flightlist-form').addEventListener('submit', this.onSubmit);

        this.client = new FlightClient();
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

    async fetchReservations(flightId) {
        return await this.client.getReservedTicketsForFlight(flightId, this.errorHandler);
    }

    async fetchPurchases(flightId) {
        return await this.client.getPurchasedTicketsForFlight(flightId, this.errorHandler);
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

    onRefresh() {
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
        let createButton = document.getElementById('create-flight');
        createButton.innerText = 'Loading...';
        createButton.disabled = true;

        // Get the values from the form inputs
        const flightName = document.getElementById('flight-name').value;
        const date = document.getElementById('date').value;
        const  departureLocation = document.getElementById('flying-from').value;
        const  arrivalLocation = document.getElementById('flying-to').value;

        //const baseTicketPrice = document.getElementById('ticket-price').value;

        // Create the concert
        const flight = await this.client.createFlight(flightName, date, departureLocation,arrivalLocation,this.errorHandler);

        // Reset the form
        document.getElementById("create-flightlist-form").reset();

        // Re-enable the form
        createButton.innerText = 'Create';
        createButton.disabled = false;
       // this.onRefresh();
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const flightAdmin = new FlightAdmin();
    flightAdmin.mount();
};

window.addEventListener('DOMContentLoaded', main);
