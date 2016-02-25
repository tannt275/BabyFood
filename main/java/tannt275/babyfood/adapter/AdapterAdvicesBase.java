package tannt275.babyfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import tannt275.babyfood.R;
import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.model.AdvicesModel;

/**
 * Created by TanNT on 12/18/2015.
 */
public class AdapterAdvicesBase extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<AdvicesModel> advicesModelList;

    public AdapterAdvicesBase(Context context, List<AdvicesModel> advicesModelList) {
        this.context = context;
        this.advicesModelList = advicesModelList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return advicesModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return advicesModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdvicesHolder advicesHolder;
        if (convertView == null){
            advicesHolder = new AdvicesHolder();
            convertView = layoutInflater.inflate(R.layout.advices_item_in_list, parent, false);
            advicesHolder.itemName = (TextView) convertView.findViewById(R.id.itemName);
            advicesHolder.itemContent = (TextView) convertView.findViewById(R.id.itemContent);
            advicesHolder.itemUrl = (ImageView) convertView.findViewById(R.id.itemUrl);
            convertView.setTag(advicesHolder);
        } else advicesHolder = (AdvicesHolder) convertView.getTag();

        AdvicesModel advicesModel = advicesModelList.get(position);
        ImageLoader.getInstance().displayImage(advicesModel.get_url(), advicesHolder.itemUrl, AppUtils.OPTION_IMAGE);
        advicesHolder.itemName.setText(advicesModel.get_name());
        advicesHolder.itemContent.setText(advicesModel.get_content());
        return convertView;
    }

    public class AdvicesHolder{
        TextView itemName;
        TextView itemContent;
        ImageView itemUrl;
    }
}
