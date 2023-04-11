import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class PayscreenClient extends BaseClass {

    constructor(props = {}) {
        super();
        const methodsToBind = ['clientLoaded', 'validateCreditCard', 'makePayment', 'purchaseTicket'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }


    /**     Brought over from unit4 concertClient... is it needed??
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    /*
     * Validates credit card
     */
    async validateCreditCard(ccNumber, errorCallback) {
        try {
        const response = await this.client.post('cardValidator',
            {ccNumber: ccNumber});
            return response.data;
        } catch (error) {
            this.handleError("cardValidator"), error, errorCallback;
        }

    }

    /*
     * Submit Payment
     */
    async makePayment(payment, errorCallback) {
         try {
         console.log(payment)
         const response = await this.client.post('/cardValidator',
             {'cardNumber': payment.cardNumber,
             "customerName": payment.customerName,
             "expirationDate": payment.expirationDate,
             "cvvNumber": payment.cvvNumber});
             return response.data;
         } catch (error) {
             this.handleError("cardValidator"), error, errorCallback;
         }


    }


    /*
     * Purchases ticket
     */
    async purchaseTicket(ticketId, numberOfSeatsReserved, errorCallback) {
        try{
            console.log("purchase start");
            const response = await this.client.post('purchasedtickets', {
                ticketId: ticketId,
                numberOfSeatsReserved: numberOfSeatsReserved
                });
            return response.data;
        } catch (error) {
            this.handleError("purchasedTicket"), error, errorCallback;
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }



}