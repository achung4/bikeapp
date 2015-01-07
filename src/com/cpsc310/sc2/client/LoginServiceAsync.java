package com.cpsc310.sc2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
  
public void login(String hostPageBaseURL, AsyncCallback<LoginInfo> async);
}