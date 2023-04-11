import BaseClass from '../util/baseClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create flightList page of the website.
 */

class Customer_DashboardPage extends BaseClass {
    constructor() {
            super();
            this.bindClassMethods(['onClick'], this);
        }

    /**
     * Once the page has loaded, set up the event handlers and fetch the flight list.
     */
    mount() {
        // collecting elements from the form
        document.getElementById('logout').addEventListener('click', this.onClick);
    }

    // Event Handlers --------------------------------------------------------------------------------------------------
    /*
     * Method to clear client Local Storage
     */
    async onClick(event) {
        // Prevent button from refreshing the page
        event.preventDefault();
        // Remove all stored items for previous customer
        localStorage.removeItem("email");
        localStorage.removeItem("flightId");
        localStorage.removeItem("purchasedTicketId");
        localStorage.removeItem("reservedTicketId");
        window.location='index.html';
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const customer_DashboardPage = new Customer_DashboardPage();
    customer_DashboardPage.mount();
};

window.addEventListener('DOMContentLoaded', main);