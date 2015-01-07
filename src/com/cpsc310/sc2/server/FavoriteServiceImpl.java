package com.cpsc310.sc2.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.cpsc310.sc2.client.FavoriteService;
import com.cpsc310.sc2.server.models.Favorite;
import com.cpsc310.sc2.server.models.Route;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class FavoriteServiceImpl extends RemoteServiceServlet implements FavoriteService {
	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	@SuppressWarnings("unchecked")
	@Override
	public Route[] getFavoriteRoutes() {
		PersistenceManager pm = PMF.getPersistenceManager();
		
		UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    
	    ArrayList<Route> routes = new ArrayList<Route>();
		List<Favorite> result;
		
		try{
			Query q = pm.newQuery(Favorite.class);
			q.declareImports("import com.google.appengine.api.users.User");
			q.setFilter("user == userParam");
			q.declareParameters("User userParam");
			
			
			//assuming email is the identifying id
			result = (List<Favorite>)q.execute(user);
			
			

			for(Favorite f: result){
				
				routes.add(f.getRoute().copy());
				
			}
			
			
			
		}finally{
			pm.close();
		}
		
		
		System.out.println("getFavorite Routes returns "+ routes.size() + 
				" elements \n     route: "+ routes.get(0) + 
				" coordinates: "+ routes.get(0).getLineStrings());
		
		return routes.toArray(new Route[0]);
	}

	@Override
	public void addFavoriteRoute(Route route) {
		
		
		UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
		
	    Favorite fav = new Favorite(user, route);
//		fav.setUser(user);
//		fav.setRoute(route);
		
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			pm.makePersistent(fav);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			pm.close();
		}
		
	}
	
	
}
