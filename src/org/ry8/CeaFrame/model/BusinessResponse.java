package org.ry8.CeaFrame.model;

import org.ry8.external.androidquery.callback.AjaxStatus;
import org.json.JSONException;
import org.json.JSONObject;

public interface BusinessResponse
{    
	public abstract void OnMessageResponse(String url, JSONObject jo, AjaxStatus status);
}
