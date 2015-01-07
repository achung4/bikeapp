package com.cpsc310.sc2.client;

import com.cpsc310.sc2.server.models.Coordinate;
import com.cpsc310.sc2.server.models.Route;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RouteServiceAsync {

	public void getRoute(int i, AsyncCallback<Route> callback);
	public void getRouteTest(AsyncCallback<Route[]> callback);
	public void getRoutePM(String name, AsyncCallback<Route> callback);
	public void addRoute(Route route, AsyncCallback<Void> callback);
	public void getRoutes(AsyncCallback<Route[]> callback);
	public void storeParsedData(AsyncCallback<Void> callback);
	public void getCoordinates(AsyncCallback<Coordinate[]> callback);
}
