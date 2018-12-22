package project.beryl.com.demoapp.api_call;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static String BASE_URL = "http://connectosh.com/ohs/Webservice/";

    public static Api getClient(){
        Retrofit retrofit=null;
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        Api service = retrofit.create(Api.class);
        return service;
    }

}
