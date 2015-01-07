package com.cpsc310.sc2.client;

import com.cpsc310.sc2.server.models.Route;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FavoriteServiceAsync {
	void getFavoriteRoutes(AsyncCallback<Route[]> callback);
	void addFavoriteRoute(Route route, AsyncCallback<Void> callback);
}
