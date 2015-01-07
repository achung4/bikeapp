package com.cpsc310.sc2.client;

import com.cpsc310.sc2.server.models.Coordinate;
import com.cpsc310.sc2.server.models.Route;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author peter9207
 *
 */
@RemoteServiceRelativePath("route")
public interface RouteService extends RemoteService {

	/**
	 * this will be used to print out all the routes
	 * @param i is the nth route
	 * @return a single route with the given nth route from the arraylist of routes
	 */
	public Route getRoute(int i);
	
	/**
	 * this will be used to print out all the routes
	 * @param none
	 * @return a single route with the given nth route from the arraylist of routes
	 */
	public Route[] getRouteTest();
	
	
	/**
	 * get the Route with the given name
	 * returns null if the route is not found
	 * @param placemark placemark of the route object
	 * @return the Route object with the given name from the datastore
	 */
	public Route getRoutePM(String name);
	
	
	/**
	 * add route to the datastore
	 * since data should not be added on the client side,
	 * AVOID USING  created for testing purposes
	 * @param route 
	 */
	public void addRoute(Route route);
	
	public Route[] getRoutes();
	public void storeParsedData();
	public Coordinate[] getCoordinates();
}
