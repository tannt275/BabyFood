package tannt275.babyfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import tannt275.babyfood.R;
import tannt275.babyfood.model.FoodsDay;

/**
 * Created by tannt on 3/4/2016.
 */
public class FoodInWeekFragmentAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<FoodsDay> foodsDayList;

    public FoodInWeekFragmentAdapter(Context context, List<FoodsDay> foodsDayList) {
        this.context = context;
        this.foodsDayList = foodsDayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return foodsDayList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodsDayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.food_in_week_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.content = (TextView) convertView.findViewById(R.id.content);
            viewHolder.method = (TextView) convertView.findViewById(R.id.method);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        FoodsDay foodsDay = foodsDayList.get(position);
        viewHolder.time.setText(foodsDay.get_time() + "");
        viewHolder.content.setText(foodsDay.get_content());
        viewHolder.method.setText(foodsDay.get_method() + "");
        return convertView;
    }

    private class ViewHolder {
        TextView time;
        TextView content;
        TextView method;
    }
}
