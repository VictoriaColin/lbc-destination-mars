import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import PayscreenClient from "../api/payscreenClient";

class PayscreenPage extends BaseClass {


       constructor() {
            super();
            this.bindClassMethods(['onPayment', 'onPayment', 'invalidCard', 'validCard', 'isClosed', 'submit'], this);
            this.dataStore = new DataStore();
       }

      /** Once the page has loaded, set up the event handlers and create the payment client.
       */
       async mount() {
            console.log("mounted")
            // Register button click
            document.getElementById('button').addEventListener('click', this.onPayment);
            this.client = new PayscreenClient();

            //WHEN anything changes in the datastore, THEN renderPayment runs
            this.client = new PayscreenClient();
            this.dataStore.addChangeListener(this.renderPayment)
       }

   // Render Methods --------------------------------------------------------------------------------------------------

    async isClosed() {
        window.alert("The time has expired. Please select a new flight.");
        window.location ='index.html';
    }

    async submit() {
        localStorage.removeItem("reservedTicketId");
        window.location ='payment_conf.html';
    }


    // Throw alert on screen
    async invalidCard() {
        window.alert("Please enter a valid card number");
    }

    // Purchase ticket.
    async validCard() {
        let ticketId = localStorage.getItem("reservedTicketId");

        if(ticketId == null) {
            this.isClosed();
        } else {
            const purchased = await this.client.purchaseTicket(ticketId, 1, this.errorHandler);
            localStorage.setItem("purchasedTicketId", purchased.ticketId);
            this.submit();
        }

    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    /*
     * Method to run when Pay Now button is clicked. Validates card, and begins purchase process
     */
    async onPayment(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        console.log("onPayment")

        // Get values from input boxes
         const name = document.getElementById('cust_name').value;
         const cardNumber = document.getElementById('cc').value;
         const expDate = document.getElementById('exp').value;
         const cvv = document.getElementById('cvv').value;

        // Put values into an array
         const payment = { name, cardNumber, expDate, cvv };

        // Checks if card number is present, if not, throw alert. Is is present, check if valid
        if(cardNumber == ""){
            this.invalidCard();
        } else {
            const result = await this.client.makePayment(payment, this.errorHandler);
            const validation = result.cardNumberValidationResultCode;
            // If card is valid, store card number and purchase ticket
            if(validation == '2000') {
//                this.dataStore.set("pay_details", result);
                this.validCard();
            } else {
                this.invalidCard();
            }
        }
    }
}
/**

Main method to run when the page contents have loaded.
*/
const main = async () => {
    console.log("PayscreenPage")
    const payscreenPage = new PayscreenPage();
    payscreenPage.mount();
};
window.addEventListener('DOMContentLoaded', main);