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
        const methodsToBind = ['clientLoaded', 'searchFlights', 'getFlights', 'getFlight', 'getReservedTicketsForFlight', 'getPurchasedTicketsForFlight', 'reserveTicket', 'purchaseTicket'];
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
    async searchFlights(date, departureLocation, arrivalLocation, errorCallback) {
        try {
            const response = await this.client.post(`/flight/search`);
            return response.data;
        } catch(error) {
            this.handleError("getFlights", error, errorCallback);
        }
    }

    async getFlights(errorCallback) {
            try {
                const response = await this.client.get(`/flight`);
                return response.data;
            } catch(error) {
                this.handleError("getFlights", error, errorCallback);
            }
        }

    /**
     * Gets the flight for the given ID.
     * @param id Unique identifier for a flight
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The flight
     */
    async getFlight(flightId, errorCallback) {
        try {
            const response = await this.client.get(`/flight/${flightId}`);
            return response.data.flight;
        } catch (error) {
            this.handleError("getFlight", error, errorCallback)
        }
    }

    async createFlight(flightName, date, departureLocation, arrivalLocation, errorCallback) {
        try {
            const response = await this.client.post(`flight`, {
                  flightName: flightName,
                  //flightId: flightId,
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
     *
     * @param concertId
     * @param errorCallback
     * @returns {Promise<*>}
     */
    async getReservedTicketsForFlight(flightId, errorCallback) {
        try {
            const response = await this.client.get(`reservedtickets/flight/${flightId}`);
            return response.data;
        } catch (error) {
            this.handleError("getReservedTicketForFlight", error, errorCallback);
        }
    }

    /**
     *
     * @param concertId
     * @param errorCallback
     * @returns {Promise<*>}
     */
    async getPurchasedTicketsForFlight(flightId, errorCallback) {
        try {
            const response = await this.client.get(`purchasedtickets/flight/${flightId}`);
            return response.data;
        } catch (error) {
            this.handleError("getPurchasedTicketsForFlight", error, errorCallback);
        }
    }

    /**
     * Create a new playlist.
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
     * Add a song to a playlist.
     * @param id The id of the playlist to add a song to.
     * @param asin The asin that uniquely identifies the album.
     * @param trackNumber The track number of the song on the album.
     * @returns The list of songs on a playlist.
     */
    async purchaseTicket(ticketId, pricePaid, errorCallback) {
        try {
            const response = await this.client.post(`purchasedtickets`, {
                ticketId: ticketId,
                pricePaid: pricePaid
            });
            return response.data;
        } catch (error) {
            this.handleError("purchaseTicket", error, errorCallback);
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