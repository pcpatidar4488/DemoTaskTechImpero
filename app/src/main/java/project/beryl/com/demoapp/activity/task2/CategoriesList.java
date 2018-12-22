package project.beryl.com.demoapp.activity.task2;

import android.app.ProgressDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import project.beryl.com.demoapp.R;
import project.beryl.com.demoapp.api_call.ApiCallServices;
import project.beryl.com.demoapp.model.CategoryData;
import project.beryl.com.demoapp.model.GetCategoryData;
import project.beryl.com.demoapp.model.ParentCategoryData;
import project.beryl.com.demoapp.model.SubcategoryData;
import project.beryl.com.demoapp.utils.Cv;
import project.beryl.com.demoapp.utils.EventBussHandler;
import project.beryl.com.demoapp.utils.Utility;

public class CategoriesList extends EventBussHandler {
    ProgressDialog mProgressDialog;
    CoordinatorLayout coordinatorLayout;
    RecyclerView parantRecyclerView, childRecyclerView, subChildRecyclerView,subChild4RecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<ParentCategoryData> parentList = new ArrayList();
    ArrayList<CategoryData> categoryList = new ArrayList();
    ArrayList<SubcategoryData> subCategoryList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        InitializationViews();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait");
        if (Utility.isConnected(getApplicationContext())) {
            mProgressDialog.show();
            ApiCallServices.action(getApplicationContext(), Cv.ACTION_GET_DATA);
        } else {
            Snackbar.make(coordinatorLayout, "Please check you internet connection first", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void InitializationViews() {

        parantRecyclerView = (RecyclerView) findViewById (R.id.recycler_view_one);
        parantRecyclerView.setHasFixedSize (true);
        layoutManager = new LinearLayoutManager(this);
        parantRecyclerView.setLayoutManager (layoutManager);

        childRecyclerView = (RecyclerView) findViewById (R.id.recycler_view_two);
        childRecyclerView.setHasFixedSize (true);
        layoutManager = new LinearLayoutManager (this);
        childRecyclerView.setLayoutManager (layoutManager);

        subChildRecyclerView = (RecyclerView) findViewById (R.id.recycler_view_three);
        subChildRecyclerView.setHasFixedSize (true);
        layoutManager = new LinearLayoutManager (this);
        subChildRecyclerView.setLayoutManager (layoutManager);

        subChild4RecyclerView = (RecyclerView) findViewById (R.id.recycler_view_four);
        subChild4RecyclerView.setHasFixedSize (true);
        layoutManager = new LinearLayoutManager (this);
        subChild4RecyclerView.setLayoutManager (layoutManager);

    }

    @Subscribe
    public void get_api(GetCategoryData response) {
        mProgressDialog.dismiss();
        String status = response.getStatus();
        //  Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
        parentList = response.getData().getParent_category_data();
        parentDataFun(parentList);
    }

    void  parentDataFun(ArrayList<ParentCategoryData> parentList){
        mAdapter = new ParentListAdapter(CategoriesList.this, parentList);
        parantRecyclerView.setAdapter (mAdapter);
    }

    @Subscribe
    public void time_out(String timeout) {
        mProgressDialog.dismiss();
        Toast.makeText(this, "Time out", Toast.LENGTH_SHORT).show();
    }
}
