package com.cpsc310.sc2.client;

import com.cpsc310.sc2.server.models.Route;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("favorites")
public interface FavoriteService extends RemoteService{

	/**
	 * get favorite routes for the user
	 * @param user 
	 * @return an array of routes
	 */
	Route[] getFavoriteRoutes();
	
	void addFavoriteRoute(Route route);
	
}
