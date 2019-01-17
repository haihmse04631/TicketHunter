package com.example.macbookpro.ticketapp.helper.apiservice;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HongSonPham on 17/1/19.
 */

public class ApiFacebook implements ApiService {
    private CallbackManager callbackManager;
    private String fbId;

    public ApiFacebook() {
        callbackManager = CallbackManager.Factory.create();
    }

    public String getFbId() {
        return fbId;
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public boolean isLoginAlready() {
        return com.facebook.AccessToken.getCurrentAccessToken() != null;
    }

    public void loadInformation() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        //Declare variables
                        String fbName = "";
                        String fbAvatar = "";
                        String fbCover = "";
                        String email = "";

                        if (object != null) {
                            try {
//                                Log.e("object", object.toString());

                                if (object.has("id")) {
                                    fbId = object.getString("id");
                                }

                                if (object.has("name")) {
                                    fbName = object.getString("name");
                                }

                                if (object.has("picture")) {
                                    fbAvatar = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                }

                                if (object.has("cover")) {
                                    fbCover = object.getJSONObject("cover").getString("source");
                                }

                                if (object.has("email")) {
                                    email = object.getString("email");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("test: ", "jsonObject is Null");
                        }
                    }
                });
        Bundle parammeters = new Bundle();
        parammeters.putString("fields", "id,name,picture.width(400).heigh(400),cover,email");
        request.setParameters(parammeters);
        request.executeAsync();
    }

    public void processLogin() {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e("test: ", "Logined");
                        loadInformation();
                    }

                    @Override
                    public void onCancel() {
                        Log.e("test: ", "Login failed");

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("test: ", "Login Error");
                    }
                });
    }
}
