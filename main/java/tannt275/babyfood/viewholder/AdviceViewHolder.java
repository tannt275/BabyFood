package tannt275.babyfood.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tannt275.babyfood.R;

/**
 * Created by tannt on 2/29/2016.
 */
public class AdviceViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView name;
    public TextView content;
    public RelativeLayout layout;

    public AdviceViewHolder(View itemView) {
        super(itemView);
        layout = (RelativeLayout) itemView.findViewById(R.id.itemCardView);
        imageView = (ImageView) itemView.findViewById(R.id.itemUrl);
        name = (TextView) itemView.findViewById(R.id.itemName);
        content = (TextView) itemView.findViewById(R.id.itemContent);
    }
}
