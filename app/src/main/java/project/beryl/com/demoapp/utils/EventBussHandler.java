package project.beryl.com.demoapp.utils;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

public class EventBussHandler extends AppCompatActivity {
    public EventBussHandler(){
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }
}
