package tannt275.babyfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import tannt275.babyfood.R;
import tannt275.babyfood.model.MenuModel;

/**
 * Created by TanNT on 12/17/2015.
 */
public class AdapterMenuDrawer extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<MenuModel> objectList;

    public AdapterMenuDrawer(Context context, List<MenuModel> objectList) {
        this.context = context;
        this.objectList = objectList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objectList.size();
    }

    @Override
    public Object getItem(int position) {
        return objectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuHolder menuHolder;
        if (convertView == null){
            menuHolder = new MenuHolder();
            convertView = layoutInflater.inflate(R.layout.menu_item, parent, false);
            menuHolder.icon = (ImageView) convertView.findViewById(R.id.iconMenu);
            menuHolder.title = (TextView) convertView.findViewById(R.id.titleMenu);
            convertView.setTag(menuHolder);
        } else menuHolder = (MenuHolder) convertView.getTag();

        MenuModel menuModel = objectList.get(position);
        menuHolder.icon.setImageResource(menuModel.getId());
        menuHolder.title.setText(menuModel.getTitle());

        return convertView;
    }

    public class MenuHolder {
        ImageView icon;
        TextView title;
    }
}
