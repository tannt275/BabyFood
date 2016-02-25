package tannt275.babyfood.model;

/**
 * Created by TanNT on 12/18/2015.
 */
public class NutritionTowerModel {
    private int _id;
    private String _url;
    private String _nameItem;
    private String _contentItem;

    public String get_nameItem() {
        return _nameItem;
    }

    public void set_nameItem(String _nameItem) {
        this._nameItem = _nameItem;
    }

    public String get_contentItem() {
        return _contentItem;
    }

    public void set_contentItem(String _contentItem) {
        this._contentItem = _contentItem;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }
}
