import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class IndexClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded','getFlights', 'reserveTicket', 'createFlight'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    /**
     * Get all flight
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns an array of flight
     */
    async getFlights(departureLocation, arrivalLocation, date, errorCallback) {
    console.log(departureLocation, arrivalLocation, date)
        try {
            const response = await this.client.post(`/flight/search`,
            {
            "departureLocation": departureLocation ,
            "arrivalLocation": arrivalLocation,
            "date": date

            }
            );
            return response.data;
        } catch(error) {
            this.handleError("getFlights", error, errorCallback);
        }
    }

    /*
     * Creates a flight.
     */
    async createFlight(flightName, date, departureLocation, arrivalLocation, errorCallback) {
        try {
            const response = await this.client.post(`flight`, {
                  flightName: flightName,
                  departureLocation: departureLocation ,
                  arrivalLocation: arrivalLocation,
                  date: date

            });
            return response.data;
        } catch (error) {
            this.handleError("createFlight", error, errorCallback);
        }
    }

    /**
     * Reserves a flight
     */
    async reserveTicket(flightId, errorCallback) {
        try {
            const response = await this.client.post(`reservedtickets`, {
                flightId: flightId,
            });
            return response.data;
        } catch (error) {
            this.handleError("reserveTicket", error, errorCallback);
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