import BaseClass from '../util/baseClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create flightList page of the website.
 */

class Create_AccountPage extends BaseClass {
    constructor() {
            super();
            this.bindClassMethods(['onClick'], this);
        }

    /**
     * Once the page has loaded, set up the event handlers and fetch the flight list.
     */
    mount() {
        // collecting elements from the form
        document.getElementById('button').addEventListener('click', this.onClick);
    }


    // Event Handlers --------------------------------------------------------------------------------------------------
    /*
     * Method to grab email address and save it as a cookie
     */
    async onClick(event) {
        // Prevent button from refreshing the page
        event.preventDefault();
        // Get the value from the flight-id input
        const email = document.getElementById('email').value;

        localStorage.setItem("email", email);
        window.location='index.html';
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const create_AccountPage = new Create_AccountPage();
    create_AccountPage.mount();
};

window.addEventListener('DOMContentLoaded', main);