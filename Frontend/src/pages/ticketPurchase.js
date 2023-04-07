import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import FlightClient from "../api/flightClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class TicketPurchase extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onStateChange', 'onSelectFlight', 'onReserveTicket', 'onPurchaseTicket'], this);
        this.dataStore = new DataStore();

        // Possible States of the page
        this.LOADING = 0;
        this.CHOOSE_FLIGHT = 1
        this.RESERVE_TICKET = 2
        this.PURCHASE_TICKET = 3
        this.DONE = 4
        this.NO_FLIGHTS = 5;
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('choose-flight-form').addEventListener('submit', this.onSelectFlight);
        document.getElementById('reserve-ticket-button').addEventListener('click', this.onReserveTicket);
        document.getElementById('purchase-ticket-form').addEventListener('submit', this.onPurchaseTicket);
        this.dataStore.addChangeListener(this.onStateChange);
        document.getElementById('find-flight').addEventListener('click', (e)=>{this.renderOptions(e)});
        // Start on the loading page
        this.dataStore.set("state", this.LOADING);

        this.client = new FlightClient();
        // Get the concerts
        //const flights = await this.client.getFlights();
        let flights;
        //this.renderOptions();
        if(flights){
        if (flights && flights.length > 0) {
            this.dataStore.set('flights', flights);
            this.dataStore.set("state", this.CHOOSE_FLIGHT);
        } else if (flights.length === 0) {
            this.dataStore.set("state", this.NO_FLIGHTS);
            this.errorHandler("There are no flights listed!");
        } else {
            this.errorHandler("Could not retrieve flights!");
        }
        }
    }

    /**
     * onStateChange - This gets called anytime the state changes in the dataStore.  This method hides/shows the
     * appropriate page for each state and calls the corresponding render method.
     */
    onStateChange() {
        const state = this.dataStore.get("state");

        const loadingSection = document.getElementById("loading")
        const chooseFlightSection = document.getElementById("choose-flight")
        const reserveTicketSection = document.getElementById("reserve-tickets")
        const purchaseTicketSection = document.getElementById("purchase-tickets")
        const doneSection = document.getElementById("done")
        const noFlightSection = document.getElementById("no-flights")

        if (state === this.LOADING) {
            loadingSection.classList.add("active")
            chooseFlightSection.classList.remove("active")
            reserveTicketSection.classList.remove("active")
            purchaseTicketSection.classList.remove("active")
            doneSection.classList.remove("active")
            noFlightSection.classList.remove("active")
        } else if (state === this.CHOOSE_FLIGHT) {
            loadingSection.classList.remove("active")
            chooseFlightSection.classList.add("active")
            reserveTicketSection.classList.remove("active")
            purchaseTicketSection.classList.remove("active")
            doneSection.classList.remove("active")
            noFlightSection.classList.remove("active")
            this.renderFlightSelector();
        } else if (state === this.RESERVE_TICKET) {
            loadingSection.classList.remove("active")
            chooseFlightSection.classList.remove("active")
            reserveTicketSection.classList.add("active")
            purchaseTicketSection.classList.remove("active")
            doneSection.classList.remove("active")
            noFlightSection.classList.remove("active")
            this.renderReservationPage();
        } else if (state === this.PURCHASE_TICKET) {
            loadingSection.classList.remove("active")
            chooseFlightSection.classList.remove("active")
            reserveTicketSection.classList.remove("active")
            purchaseTicketSection.classList.add("active")
            doneSection.classList.remove("active")
            noFlightSection.classList.remove("active")
            this.renderPurchasePage();
        } else if (state === this.DONE) {
            loadingSection.classList.remove("active")
            chooseFlightSection.classList.remove("active")
            reserveTicketSection.classList.remove("active")
            purchaseTicketSection.classList.remove("active")
            doneSection.classList.add("active")
            noFlightSection.classList.remove("active")
            this.renderDonePage();
        } else if (state === this.NO_FLIGHTS) {
            loadingSection.classList.remove("active")
            chooseFlightSection.classList.remove("active")
            reserveTicketSection.classList.remove("active")
            purchaseTicketSection.classList.remove("active")
            doneSection.classList.remove("active")
            noFlightSection.classList.add("active")
        }
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderFlightSelector() {
        let flightSelect = document.getElementById("choose-flight-input");

        const flights = this.dataStore.get("flights");


        let options = "";
        for (const flight of flights) {
            options += `<option value="${flight.id}">${flight.name}</option>`
        }
        flightSelect.innerHTML = options;
    }

    async renderReservationPage() {
        const flight = this.dataStore.get("selectedFlight");

        document.getElementById("reserve-ticket-info").innerHTML = `
            <h3>${flight.name}</h3>
            <div>Date: ${flight.date}</div>
            <div>Asking Price: ${this.formatCurrency(flight.ticketBasePrice)}</div>
            <div>Would you like to reserve a ticket to purchase?</div>
        `;
    }

    async renderPurchasePage() {
        const flight = this.dataStore.get("selectedFlight");
        const ticketReservation = this.dataStore.get("ticketReservation");
        document.getElementById("purchase-price").min = flight.ticketBasePrice;
        document.getElementById("purchase-ticket-info").innerHTML = `
            <div>You have reserved a ticket to <strong>${flight.name}</strong>!</div>
            <div>Your reservation number is ${ticketReservation.ticketId}</div>
            <div>You have <strong>5 minutes</strong> to purchase the ticket before your reservation is released.</div>
            <div>The minimum ticket price is ${this.formatCurrency(flight.ticketBasePrice)}</div>
        `;
    }

    async renderDonePage() {
        const flight = this.dataStore.get("selectedFlight");
        const ticketReceipt = this.dataStore.get("ticketReceipt");
        document.getElementById("done-info").innerHTML = `
            <div>Thank you for your purchase!</div>
            <div>You have purchased one ticket to see <strong>${flight.name}</strong> for <strong>${this.formatCurrency(ticketReceipt.pricePaid)}</strong></div>
            <div>Your confirmation number is ${ticketReceipt.ticketId}</div>
        `;
    }

    async renderOptions(e){
    e.preventDefault();
    console.log("render options");
    const options = document.getElementById("choose-flight-input");

    const optionFrom = document.getElementById("flying-from").value;
    const optionTo = document.getElementById("flying-to").value;
    const optionDate = document.getElementById("date").value;
    // get request here
        const flights =await this.client.getFlights(optionFrom,optionTo,optionDate,this.errorHandler);//this.dataStore.get("flights");
        console.log(flights);
        if(flights){
        for(const flight of flights){
        options.innerHTML += `<option >${flight.flightId}</option>`
    }}
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onSelectFlight(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let flightId = document.getElementById("choose-flight-input").value;

        // Get the object of the selected concert so we can store it.
        const flights = this.dataStore.get("flights");
        let selectedFlight = null;
        for (const flight of flights) {
            if (flight.id === flightId) {
                selectedFlight = flight;
            }
        }

        if (selectedFlight) {
            this.dataStore.set("selectedFlight", selectedFlight);
            this.dataStore.set("state", this.RESERVE_TICKET)
        }
    }

    async onReserveTicket() {
        const flight = this.dataStore.get("selectedFlight");
        const ticketReservation = await this.client.reserveTicket(flight.id, this.errorHandler);

        if (ticketReservation && ticketReservation.ticketId) {
            this.dataStore.set("ticketReservation", ticketReservation);
            this.dataStore.set("state", this.PURCHASE_TICKET);
        } else {
            this.errorHandler("Error reserving ticket!  Try again...");
        }
    }

    async onPurchaseTicket(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        const ticketReservation = this.dataStore.get("ticketReservation");
        const pricePaid = parseInt(document.getElementById("purchase-price").value);
        const ticketReceipt = await this.client.purchaseTicket(ticketReservation.ticketId, pricePaid, this.errorHandler);

        if (ticketReceipt && ticketReceipt.ticketId) {
            this.dataStore.set("ticketReceipt", ticketReceipt);
            this.dataStore.set("state", this.DONE);
        } else {
            this.errorHandler("Error purchasing ticket! Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const ticketPurchase = new TicketPurchase();
    ticketPurchase.mount();
};

window.addEventListener('DOMContentLoaded', main);
