package tannt275.babyfood.model;

/**
 * Created by TanNT on 12/18/2015.
 */
public class FoodModel {
    private String _nameFood;
    private String _materialContent;
    private String _methodContent;
    private String _timesFood;
    private int _admins;
    private int _id;
    private int _favorite;

    public String get_nameFood() {
        return _nameFood;
    }

    public void set_nameFood(String _nameFood) {
        this._nameFood = _nameFood;
    }

    public String get_materialContent() {
        return _materialContent;
    }

    public void set_materialContent(String _materialContent) {
        this._materialContent = _materialContent;
    }

    public String get_methodContent() {
        return _methodContent;
    }

    public void set_methodContent(String _methodContent) {
        this._methodContent = _methodContent;
    }

    public String get_timesFood() {
        return _timesFood;
    }

    public void set_timesFood(String _timesFood) {
        this._timesFood = _timesFood;
    }

    public int get_admins() {
        return _admins;
    }

    public void set_admins(int _admins) {
        this._admins = _admins;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_favorite() {
        return _favorite;
    }

    public void set_favorite(int _favorite) {
        this._favorite = _favorite;
    }
}
