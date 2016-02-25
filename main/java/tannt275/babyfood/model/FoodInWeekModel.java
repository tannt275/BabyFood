package tannt275.babyfood.model;

/**
 * Created by tannt on 2/25/2016.
 */
public class FoodInWeekModel extends BaseModel {

    private int _id;
    private String _day;
    private String _content;
    private int _idResource;

    public int get_idResource() {
        return _idResource;
    }

    public void set_idResource(int _idResource) {
        this._idResource = _idResource;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_day() {
        return _day;
    }

    public void set_day(String _day) {
        this._day = _day;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }
}
