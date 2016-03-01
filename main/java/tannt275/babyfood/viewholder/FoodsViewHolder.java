package tannt275.babyfood.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import tannt275.babyfood.R;

/**
 * Created by tannt on 2/29/2016.
 */
public class FoodsViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout layout;
    public TextView name;
    public TextView content;

    public FoodsViewHolder(View itemView) {
        super(itemView);
        layout = (LinearLayout) itemView.findViewById(R.id.fragment_foods_itemLayout);
        name = (TextView) itemView.findViewById(R.id.fragment_foods_itemName);
        content = (TextView) itemView.findViewById(R.id.fragment_foods_itemContent);
    }
}
