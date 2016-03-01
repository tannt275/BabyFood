package tannt275.babyfood.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TanNT on 12/18/2015.
 */
public class AdvicesModel extends BaseModel {
    private int _id;
    private String _name;
    private String _url;
    private String _content;
    private int _admin;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }

    public int get_admin() {
        return _admin;
    }

    public void set_admin(int _admin) {
        this._admin = _admin;
    }

}
