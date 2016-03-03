package tannt275.babyfood.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import tannt275.babyfood.R;
import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.model.AdvicesModel;
import tannt275.babyfood.viewholder.AdviceViewHolder;

/**
 * Created by tannt on 2/29/2016.
 */
public class AdvicesAdapter extends RecyclerView.Adapter<AdviceViewHolder> {

    private Context context;
    private List<AdvicesModel> advicesModelList;
    private LayoutInflater layoutInflater;
    private ClickItemAdvices clickItemAdvices;

    public ClickItemAdvices getClickItemAdvices() {
        return clickItemAdvices;
    }

    public void setClickItemAdvices(ClickItemAdvices clickItemAdvices) {
        this.clickItemAdvices = clickItemAdvices;
    }

    public AdvicesAdapter(Context context, List<AdvicesModel> advicesModelList) {
        this.context = context;
        this.advicesModelList = advicesModelList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public AdviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.advices_item_in_list, parent, false);
        return new AdviceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdviceViewHolder holder, int position) {
        final AdvicesModel advicesModel = advicesModelList.get(position);
        holder.content.setText(advicesModel.get_content());
        holder.name.setText(advicesModel.get_name());
        if (advicesModel.get_admin() ==1){
            ImageLoader.getInstance().displayImage(advicesModel.get_url(), holder.imageView, AppUtils.OPTION_IMAGE);
        } else if (advicesModel.get_admin() ==2){
            ImageLoader.getInstance().displayImage("file://" + advicesModel.get_url(), holder.imageView, AppUtils.OPTION_IMAGE_LOCAL);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickItemAdvices != null)
                    clickItemAdvices.onClickItem(advicesModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return advicesModelList.size();
    }

    public interface ClickItemAdvices {
        public void onClickItem(AdvicesModel advicesModel);
    }

}
