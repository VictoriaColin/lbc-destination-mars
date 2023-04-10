import BaseClass from '../util/baseClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the checkout page of the website.
 */
class Cancel_ConfirmationPage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['renderFlights'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the flight list.
     */
    mount() {
//        this.client = new Cancel_ConfirmationClient();
        this.dataStore.addChangeListener(this.renderFlights);
        this.getUUID();
    }

    async getUUID() {
        console.log("entered")
        // https://www.educative.io/answers/how-to-create-a-random-uuid-in-javascript
        const uuid = crypto.randomUUID();

        this.dataStore.set("cancelled", uuid);
    }

    // Render Methods --------------------------------------------------------------------------------------------------
    renderFlights() {
        const cancelled = this.dataStore.get("cancelled");
        let cancelHtml = "";
        if(cancelled) {
            cancelHtml += `
                <div class ="card">
                    <br/>
                    <div><b>Confirmation#:</b> ${cancelled}</div>
                    <br/>
                </div>`;
        }

        console.log(cancelled);

        document.getElementById("cancel-num").innerHTML = cancelHtml;
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const cancel_ConfirmationPage = new Cancel_ConfirmationPage();
    cancel_ConfirmationPage.mount();
};

window.addEventListener('DOMContentLoaded', main);