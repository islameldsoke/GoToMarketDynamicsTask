package com.example.islam.gotomarketdynamicstask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookActivity extends AppCompatActivity {

    private Button facebookLoginBtn;
    private LoginButton loginFacebookBtn;
    private TextView userName ;
    private RecyclerView friendsList;
    private ProfilePictureView userImageView;
    private CallbackManager callbackManager;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        callbackManager = CallbackManager.Factory.create();

        facebookLoginBtn = findViewById(R.id.facebook_login_btn);
        loginFacebookBtn = findViewById(R.id.login_button);
        userName = findViewById(R.id.username_tv);
        friendsList = findViewById(R.id.friends_rv);
        userImageView = findViewById(R.id.profile_iv);

        loginFacebookBtn.setReadPermissions("email", "public_profile" , "user_friends" , "read_custom_friendlists" );
        loginFacebookBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

//                        try {
//                            JSONArray jsonArrayFriends = object.getJSONObject("friendlist").getJSONArray("data");
//                            Log.e("list" , jsonArrayFriends.toString());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                        displayUserInfo(object);
                        facebookLoginBtn.setText("Logout");
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields" , "first_name , last_name , email , id , picture.type(large)");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("er",error.toString());
            }
        });

        facebookLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFacebookBtn.performClick();
            }
        });
    }


    private void displayUserInfo(JSONObject object){

        String first_name , last_name , email , picture , f;
        first_name = "";
        last_name = "";
        email = "";
        id = "";
        picture = "";
        f = "";

        try {
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            email = object.getString("email");
            id = object.getString("id");





            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/" + id + "/" + "friendlists",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            JSONObject main = response.getJSONObject();
                            try {
                                JSONArray data = main.getJSONArray("data");
                                int size = data.length();

                                for (int i = 0 ; i<data.length(); i++){
                                    Log.e("size" , data.get(i).toString());
                                   // myNewGraphReq(data.get(i).toString());
                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
            ).executeAsync();


            picture = object.getString("picture").replace("\\" , "");

           userImageView.setProfileId(id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String fullUserName = first_name +" "+ last_name;
        userName.setText(fullUserName);


        Toast.makeText(this ,first_name + " " + last_name + " " + email + " " + id + " " + picture ,Toast.LENGTH_SHORT ).show();
        Log.e("pic" , picture + first_name + " " + last_name + " " + email + " " + id + " " + f +" friends");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


//    private void myNewGraphReq(String friendlistId) {
//        final String graphPath = "/"+friendlistId+"/members/";
//        AccessToken token = AccessToken.getCurrentAccessToken();
//        GraphRequest request = new GraphRequest(token, graphPath, null, HttpMethod.GET, new GraphRequest.Callback() {
//            @Override
//            public void onCompleted(GraphResponse graphResponse) {
//                JSONObject object = graphResponse.getS();
//                try {
//
//                /* Do something with the user list */
//                /* ex: get first user in list, "name" */
//                    JSONObject user = object.getJSONObject("name");
//                    String usersName = user.getString("name");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        Bundle param = new Bundle();
//        param.putString("fields", "name");
//        request.setParameters(param);
//        request.executeAsync();
//    }

}
