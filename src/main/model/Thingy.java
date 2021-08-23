package model;

import org.json.JSONObject;
import persistence.Writable;

public class Thingy implements Writable {
    private String name;
    private Category cate;


    public Thingy(String foodname, Category foodcate) {
        this.name = foodname;
        this.cate = foodcate;
    }

    public String getName() {
        return this.name;
    }

    public Category getCate() {
        return this.cate;
    }

    public String toString() {
        return cate + ":" + name;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("category", cate);
        return json;
    }
}
