package project.beryl.com.demoapp.activity.task2;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import project.beryl.com.demoapp.R;
import project.beryl.com.demoapp.model.CategoryData;
import project.beryl.com.demoapp.model.ParentCategoryData;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private static final String TAG = CategoryListAdapter.class.getSimpleName ();
    private Context context;
    private List<CategoryData> modelList;
    String accessToken = "";

    public CategoryListAdapter(Context context, List<CategoryData> modelList) {
        this.modelList = modelList;
    }

    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from (viewGroup.getContext ()).inflate (R.layout.details_row_list, viewGroup,
                false);
        return new CategoryListAdapter.ViewHolder (view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            holder.header_name.setText (modelList.get (position).getCategory());

        holder.header_name.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                holder.layout_product_Name.setBackgroundColor (Color.GRAY);

              /*  holder.childRecyclerView.setHasFixedSize (true);
                holder.layoutManager = new LinearLayoutManager(context);
                holder.childRecyclerView.setLayoutManager (holder.layoutManager);
                holder.mAdapter = new CategoryListAdapter(context, modelList.get(position).getCategory_data());
                holder.childRecyclerView.setAdapter (holder.mAdapter);*/
            }
        });
    }
    @Override
    public int getItemCount() {
        try {
            return modelList.size ();
        } catch (Exception e) {
            Log.d (TAG, "Exception " + e.getMessage ());
            return 0;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView header_name;
        LinearLayout layout_product_Name;
        RecyclerView childRecyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;
        public ViewHolder(View itemView) {
            super (itemView);
            header_name = itemView.findViewById (R.id.product_Name);
            layout_product_Name = itemView.findViewById (R.id.layout_product_Name);
            childRecyclerView = itemView.findViewById (R.id.recycler_view_two);
        }
    }
}