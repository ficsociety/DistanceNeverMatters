package apm.muei.distancenevermatters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.entities.Dice;
import apm.muei.distancenevermatters.entities.Map;


public class DiceGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<Dice> diceList;

    public DiceGridViewAdapter(Context context, List<Dice> diceList) {
        this.context = context;
        this.diceList = diceList;
    }

    @Override
    public int getCount() {
        return diceList.size();
    }

    @Override
    public Dice getItem(int position) {
        return diceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.resource_list_item, viewGroup, false);
        }

        TextView name = (TextView) view.findViewById(R.id.resourceName);

        Dice dice = getItem(position);
        name.setText(dice.getName());


        return view;
    }

}