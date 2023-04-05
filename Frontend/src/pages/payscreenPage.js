import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import payscreenClient from "../api/payscreenClient";

class PayscreenPage extends BaseClass {

    constructor() {
    super();
    this.bindClassMethods();
    this.dataStore = new DataStore;
    }

    async mount() {
        document.getElementById('pay_details').addEventListener('click', this.onSubmit);
        this.client = new payscreenClient();



    }


    //Render Methods-----------------

    async renderPayscreen() {
        const custName = document.getElementsByName("cust_name")

    }



}