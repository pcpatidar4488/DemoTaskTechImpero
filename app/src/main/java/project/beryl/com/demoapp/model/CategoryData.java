package project.beryl.com.demoapp.model;

import java.util.ArrayList;

public class CategoryData {
    String category_id;
    String category;
    ArrayList<SubcategoryData> subcategory_data;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<SubcategoryData> getSubcategory_data() {
        return subcategory_data;
    }

    public void setSubcategory_data(ArrayList<SubcategoryData> subcategory_data) {
        this.subcategory_data = subcategory_data;
    }
}
