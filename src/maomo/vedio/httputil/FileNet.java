package maomo.vedio.httputil;

import maomo.vedio.http.AsyncHttpClient;
import maomo.vedio.http.RequestHandle;
import maomo.vedio.http.ResponseHandlerInterface;
import maomo.vedio.util.Canstact;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import android.content.Context;

public class FileNet extends BaseHttpRequest {

	ResponseHandlerInterface responseHandlerInterface;
	
	public FileNet(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	 public void setResponseHandlerInterface(
			ResponseHandlerInterface responseHandlerInterface) {
		this.responseHandlerInterface = responseHandlerInterface;
	}
	@Override
	public ResponseHandlerInterface getResponseHandler() {
		// TODO Auto-generated method stub
		if (responseHandlerInterface == null) {
			return null;
		}
		return responseHandlerInterface;
				
	}

	@Override
	public String getDefaultURL() {
		// TODO Auto-generated method stub
		return Canstact.VEDIO_URL;
	}

	@Override
	public boolean isRequestHeadersAllowed() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isRequestBodyAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RequestHandle executeSample(AsyncHttpClient client, String URL,
			Header[] headers, HttpEntity entity,
			ResponseHandlerInterface responseHandler) {
		// TODO Auto-generated method stub
		return client.get(context, URL, headers, null, responseHandler);

	}

}
