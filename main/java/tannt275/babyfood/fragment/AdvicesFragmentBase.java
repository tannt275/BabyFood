package tannt275.babyfood.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tannt275.babyfood.R;
import tannt275.babyfood.adapter.AdapterAdvicesBase;
import tannt275.babyfood.database.DatabaseHandler;
import tannt275.babyfood.model.AdvicesModel;

/**
 * Created by TanNT on 12/18/2015.
 */
public class AdvicesFragmentBase extends Fragment implements AdapterView.OnItemClickListener {

    public static String TAG = AdvicesFragmentBase.class.getSimpleName();

    public static String tagFragment = "TAG_FRAGMENT";
    private String typeAdvices;
    private DatabaseHandler databaseHandler;
    private ListView listView;
    private List<AdvicesModel> advicesModelList;
    private AdapterAdvicesBase adapterAdvicesBase;

    private ClickItemInListAdvices clickItemInListAdvices;

    public void setClickItemInListAdvices(ClickItemInListAdvices clickItemInListAdvices) {
        this.clickItemInListAdvices = clickItemInListAdvices;
    }

    public static AdvicesFragmentBase newInstance(String type){
        AdvicesFragmentBase advicesFragment = new AdvicesFragmentBase();
        Bundle bundle = new Bundle();
        bundle.putString(tagFragment, type);
        advicesFragment.setArguments(bundle);
        return advicesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            typeAdvices = getArguments().getString(tagFragment);
            Log.d(TAG, "type Advices: " + typeAdvices);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.advices_fragment_base, container, false);
        listView = (ListView) rootView.findViewById(R.id.advicesListView);
        databaseHandler = new DatabaseHandler(getActivity());
        fillData();
        return rootView;
    }

    private void fillData() {

        advicesModelList = databaseHandler.getListAdvices(typeAdvices);
        adapterAdvicesBase = new AdapterAdvicesBase(getActivity(), advicesModelList);
        listView.setAdapter(adapterAdvicesBase);
        listView.setOnItemClickListener(this);
    }

    /*only test*/
    private String urlTemp = "https://www.google.com/imgres?imgurl=http://screencrave.com/wp-content/" +
            "uploads/2011/02/I-Am-Number-Four-Dianna-Agron-2-16-11-kc.jpg&" +
            "imgrefurl=http://screencrave.com/2011-02-17/i-am-number-four-movie-review/&h=399&" +
            "w=570&tbnid=xTM9TT2c7bkpWM:&docid=zrSp25744GkY_M&hl=en&ei=P7pzVv44xtSaBfuGovAG&tbm=" +
            "isch&ved=0ahUKEwi-1I_l9eTJAhVGqqYKHXuDCG44ZBAzCAMoADAA";

    private List<AdvicesModel> generateList(){
        List<AdvicesModel> list = new ArrayList<>();
        for (int i = 0; i < 20; i ++){
            AdvicesModel advicesModel = new AdvicesModel();
            advicesModel.set_name("Item thu " + (i+1) + "\n"+"A different solution might be to extend the view " +
                    "and override the setPressed(Boolean) method.");
            advicesModel.set_content("https://www.google.com/imgres?imgurl=http://screencrave.com/wp-content/\" +\n" +
                    "            \"uploads/2011/02/I-Am-Number-Four-Dianna-Agron-2-16-11-kc.jpg&\" +\n" +
                    "            \"imgrefurl=http://screencrave.com/2011-02-17/i-am-number-four-movie-review/&h=399&\" +\n" +
                    "            \"w=570&tbnid=xTM9TT2c7bkpWM:&docid=zrSp25744GkY_M&hl=en&ei=P7pzVv44xtSaBfuGovAG&tbm=\" +\n" +
                    "            \"isch&ved=0ahUKEwi-1I_l9eTJAhVGqqYKHXuDCG44ZBAzCAMoADAA\"");
            advicesModel.set_url(urlTemp);
            list.add(advicesModel);
        }
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AdvicesModel advicesModel = advicesModelList.get(position);
        clickItemInListAdvices.onClickItemInListAdvices(position, typeAdvices);
    }

    public interface ClickItemInListAdvices{
        public void onClickItemInListAdvices(int posClick, String type);
    }
}
