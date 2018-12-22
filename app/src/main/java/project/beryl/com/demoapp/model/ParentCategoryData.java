package project.beryl.com.demoapp.model;

import java.util.ArrayList;

public class ParentCategoryData {
    String parent_category_id;
    String parent_category;
    ArrayList<CategoryData> category_data;

    public String getParent_category_id() {
        return parent_category_id;
    }

    public void setParent_category_id(String parent_category_id) {
        this.parent_category_id = parent_category_id;
    }

    public String getParent_category() {
        return parent_category;
    }

    public void setParent_category(String parent_category) {
        this.parent_category = parent_category;
    }

    public ArrayList<CategoryData> getCategory_data() {
        return category_data;
    }

    public void setCategory_data(ArrayList<CategoryData> category_data) {
        this.category_data = category_data;
    }
}
