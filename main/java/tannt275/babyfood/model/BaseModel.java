package tannt275.babyfood.model;

import com.google.gson.Gson;

/**
 * Created by tannt on 2/25/2016.
 */
public class BaseModel {
    public String convertToString(){
        return new Gson().toJson(this);
    }
}
