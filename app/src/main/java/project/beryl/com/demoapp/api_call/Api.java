package project.beryl.com.demoapp.api_call;


import project.beryl.com.demoapp.model.GetCategoryData;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

   @GET("getcategory")
   Call<GetCategoryData> getApi();
}
