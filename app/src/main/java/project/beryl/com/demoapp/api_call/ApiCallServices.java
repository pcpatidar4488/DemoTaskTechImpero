package project.beryl.com.demoapp.api_call;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import project.beryl.com.demoapp.model.GetCategoryData;
import project.beryl.com.demoapp.utils.Cv;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ApiCallServices extends IntentService {

    private Api api;

    public ApiCallServices() {
        super("ApiCallServices");
    }

    public static void action(Context context, String action) {
        Intent intent = new Intent(context, ApiCallServices.class);
        intent.setAction(action);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        api = ApiClient.getClient();
        if (intent != null) {
            final String action = intent.getAction();
            if (Cv.ACTION_GET_DATA.equals(action)) {
                handleCategoryData();
            }
        }
    }

    private void handleCategoryData() {
        api.getApi().enqueue(new Callback<GetCategoryData>() {
            @Override
            public void onResponse(Call<GetCategoryData> call, Response<GetCategoryData> r) {
                if (r.isSuccessful()) {
                    GetCategoryData body = r.body();
                    EventBus.getDefault().post(body);
                } else {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }

            @Override
            public void onFailure(Call<GetCategoryData> call, Throwable t) {
                try {
                    EventBus.getDefault().post(t.getMessage());
                } catch (Exception ex) {
                    EventBus.getDefault().post(Cv.TIMEOUT);
                }
            }
        });
    }
}
