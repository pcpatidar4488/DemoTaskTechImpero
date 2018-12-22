package project.beryl.com.demoapp.activity.task1;

import android.app.ProgressDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import project.beryl.com.demoapp.R;
import project.beryl.com.demoapp.api_call.ApiCallServices;
import project.beryl.com.demoapp.model.CategoryData;
import project.beryl.com.demoapp.model.GetCategoryData;
import project.beryl.com.demoapp.model.ParentCategoryData;
import project.beryl.com.demoapp.model.SubcategoryData;
import project.beryl.com.demoapp.utils.Cv;
import project.beryl.com.demoapp.utils.EventBussHandler;
import project.beryl.com.demoapp.utils.Utility;

public class MainActivity extends EventBussHandler {
    ProgressDialog mProgressDialog;
    CoordinatorLayout coordinatorLayout;
    List<NLevelItem> list;
    ListView listView;
    ArrayList<ParentCategoryData> parentList = new ArrayList();
    ArrayList<CategoryData> categoryList = new ArrayList();
    ArrayList<SubcategoryData> subCategoryList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        listView = (ListView) findViewById(R.id.listView1);
        list = new ArrayList<NLevelItem>();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait");
        if (Utility.isConnected(getApplicationContext())) {
            mProgressDialog.show();
            ApiCallServices.action(MainActivity.this, Cv.ACTION_GET_DATA);
        } else {
            Snackbar.make(coordinatorLayout, "Please check you internet connection first", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void get_api(GetCategoryData response) {
        mProgressDialog.dismiss();
        String status = response.getStatus();
      //  Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
        parentList = response.getData().getParent_category_data();

       // Random rng = new Random();
        final LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < parentList.size(); i++) {
            ImageView image_view;
            categoryList = response.getData().getParent_category_data().get(i).getCategory_data();
            final NLevelItem grandParent = new NLevelItem(new SomeObject(parentList.get(i).getParent_category()),null, new NLevelView() {
                @Override
                public View getView(NLevelItem item) {
                    View view = inflater.inflate(R.layout.parent_list_item, null);
                    TextView tv = (TextView) view.findViewById(R.id.textView);
                    ImageView image_view = (ImageView) view.findViewById(R.id.right_arrow);
                    tv.setBackgroundColor(getResources().getColor(R.color.darkGrey));
                    String name = (String) ((SomeObject) item.getWrappedObject()).getName();
                    tv.setText(name);
                    if (item.isExpanded()){
                        image_view.setImageResource(R.drawable.down_arrow);
                    }else {
                        image_view.setImageResource(R.drawable.up_arrow);
                    }
                    return view;
                }
            });
            list.add(grandParent);
            if (categoryList!=null){
                for (int j = 0; j < categoryList.size(); j++) {
                    subCategoryList = response.getData().getParent_category_data().get(i).getCategory_data().get(j).getSubcategory_data();
                    NLevelItem parent = new NLevelItem(new SomeObject(categoryList.get(j).getCategory()),grandParent, new NLevelView() {

                        @Override
                        public View getView(NLevelItem item) {
                            View view = inflater.inflate(R.layout.category_list_item, null);
                            TextView tv = (TextView) view.findViewById(R.id.textView);
                            ImageView right_arrow = (ImageView) view.findViewById(R.id.right_arrow);
                            tv.setBackgroundColor(getResources().getColor(R.color.lightgray));
                            String name = (String) ((SomeObject) item.getWrappedObject()).getName();
                            tv.setText(name);
                            if (item.isExpanded()){
                                right_arrow.setImageResource(R.drawable.down_arrow);
                            }else {
                                right_arrow.setImageResource(R.drawable.up_arrow);
                            }
                            return view;
                        }
                    });

                    list.add(parent);
                    if (subCategoryList!=null){
                        for( int k = 0; k < subCategoryList.size(); k++) {
                            NLevelItem child = new NLevelItem(new SomeObject(subCategoryList.get(k).getSubcategory()),parent, new NLevelView() {

                                @Override
                                public View getView(NLevelItem item) {
                                    View view = inflater.inflate(R.layout.sub_category_list_item, null);
                                    TextView tv = (TextView) view.findViewById(R.id.textView);
                                    tv.setBackgroundColor(getResources().getColor(R.color.white));
                                    String name = (String) ((SomeObject) item.getWrappedObject()).getName();
                                    tv.setText(name);
                                    return view;
                                }
                            });

                            list.add(child);
                        }
                    }
                }
            }
        }

        NLevelAdapter adapter = new NLevelAdapter(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                ((NLevelAdapter)listView.getAdapter()).toggle(arg2);
                ((NLevelAdapter)listView.getAdapter()).getFilter().filter();
               // Toast.makeText(MainActivity.this, "Click   "+arg1.getParent().toString(), Toast.LENGTH_SHORT).show();
              //  System.out.println("Click   "+arg0+"\n"+arg1+"\n "+arg2+"\n "+arg3);
            }

        });
    }

    class SomeObject {
        public String name;

        public SomeObject(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    @Subscribe
    public void time_out(String timeout) {
        mProgressDialog.dismiss();
        Toast.makeText(this, "Time out", Toast.LENGTH_SHORT).show();
    }


}
