import BaseClass from "../util/baseClass";
import axios from 'axios'

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
 */
export default class FlightsClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'searchFlights', 'getFlight', 'reserveTicket'];
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
     * Searches for flights based on previous page search params.
     */
    async searchFlights(date, departureLocation, arrivalLocation, errorCallback) {
            try {
                const response = await this.client.post(`/search`, {
                      date: date,
                      departureLocation: departureLocation,
                      arrivalLocation: arrivalLocation
                });
                return response.data;
            } catch (error) {
                this.handleError("searchFlights", error, errorCallback);
            }
        }

    /**
     * Reserve ticket based on FlightId entered by User
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
