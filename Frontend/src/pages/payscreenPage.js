import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import PayscreenClient from "../api/payscreenClient";

class PayscreenPage extends BaseClass {


       constructor() {
            super();
            this.bindClassMethods(['onPayment', 'renderPayment'], this);
            this.dataStore = new DataStore();
       }

      /** Once the page has loaded, set up the event handlers and create the payment client.
       */
       async mount() {
            console.log("mounted")
            document.querySelector('button').addEventListener('click', this.onPayment);
            this.client = new PayscreenClient();
            localStorage.setItem("Testkey", "testvalue")
//            console.let(results)

            //WHEN anything changes in the datastore, THEN renderPayment runs
            this.dataStore.addChangeListener(this.renderPayment)
       }

       // Render Methods --------------------------------------------------------------------------------------------------

       async renderPayment() {
       let resultArea = document.getElementById("results");

        const payment = this.dataStore.get("pay_details");
        console.log(payment)

        if (payment) {

            resultArea.innerHTML = `
                Success
                <a href="payment_conf.html"> To Next Page</a>
            `

        } else {
            resultArea.innerHTML = "Please enter all fields";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onPayment(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        console.log("onPayment")

         const name = document.querySelector('#cust_name').value;
         const cardNumber = document.querySelector('#cc').value;
         const expDate = document.querySelector('#exp').value;
         const cvv = document.querySelector('#cvv').value;

         const payment = { name, cardNumber, expDate, cvv };

         this.dataStore.set("pay_details", null);

         const result = await this.client.makePayment(payment, this.errorHandler);

         this.dataStore.set("pay_details", result);

         if (result) {
             this.showMessage(`Payment Successful!`);
             this.renderPayment;
         } else {
             this.errorHandler("Error making payment!  Try again...");
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

//
//    constructor() {
//    super();
//    this.bindClassMethods(['renderPayscreen', 'onPayNow',], this);
//    this.dataStore = new DataStore;
//    }
//
//    //mount() method - This method is used to initialize the page after it has loaded.
//    async mount() {
//        document.getElementById('pay_details').addEventListener('click', this.onPayNow);
//        this.client = new payscreenClient();
//        this.dataStore.addChangeListner(this.renderPayscreen);
//
//
//
//    }
//
//
//    //Render Methods-----------------
//    // - These methods are used to render some content to the page.
//    //  for consistency, keep all render methods starting with 'render'
//
//    async renderPayscreen() {
//        const custName = document.getElementsByName("cust_name")
//
//    }
//
//    //Event Handler Methods ----------
//    // - These methods are intended to perform actions
//    //    whenever an event happens on the page.
//    //  for consistency, keep all render methods starting with 'on'
//    // followed by a name describing the event happening
//
//    async onPayNow() {
//
//    //Get the values from the div classes inside div class "pay_details"
//    const custName = document.getElementById('cust_name').value;
//    const ccNum = document.getElementById('cc').value;
//    const ccExp = document.getElementById('exp').value;
//    const ccCvv = document.getElementById('cvv').value;
//
//    //Do I need to create a purchased ticket here?  How do I link the two?
//    //use an await command from the last page, that waits for valid
//    //cc return?
//
//
//    }
//
///**
// * Main method to run when the page contents have loaded.
// */
// const main = async () => {
//    const payscreenPage = new PayscreenPage();
//    payscreenPage.mount();
// };
//
// window.addEventListener('DOMContentLoaded', main);



