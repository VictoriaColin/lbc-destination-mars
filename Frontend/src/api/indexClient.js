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
     * Create a new flightList.
     * @param name The name of the playlist to create.
     * @param customerId The user who is the owner of the playlist.
     * @param tags Metadata tags to associate with a playlist.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The playlist that has been created.
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