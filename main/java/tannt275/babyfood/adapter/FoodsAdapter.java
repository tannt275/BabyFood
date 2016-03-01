package tannt275.babyfood.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import tannt275.babyfood.R;
import tannt275.babyfood.model.FoodModel;
import tannt275.babyfood.viewholder.FoodsViewHolder;

/**
 * Created by tannt on 2/29/2016.
 */
public class FoodsAdapter extends RecyclerView.Adapter<FoodsViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<FoodModel> foodModels;
    private ClickItemFoods clickItemFoods;

    public ClickItemFoods getClickItemFoods() {
        return clickItemFoods;
    }

    public void setClickItemFoods(ClickItemFoods clickItemFoods) {
        this.clickItemFoods = clickItemFoods;
    }

    public FoodsAdapter(Context context, List<FoodModel> foodModels) {
        this.context = context;
        this.foodModels = foodModels;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public FoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.fragment_food_item, parent, false);
        return new FoodsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FoodsViewHolder holder, int position) {
        final FoodModel foodModel = foodModels.get(position);
        holder.name.setText(String.valueOf(position + 1) + "." + foodModel.get_nameFood());
        holder.content.setText(foodModel.get_methodContent());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickItemFoods != null)
                    clickItemFoods.onClickItemFoods(foodModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodModels.size();
    }

    public interface ClickItemFoods {
        public void onClickItemFoods(FoodModel foodModel);
    }
}
