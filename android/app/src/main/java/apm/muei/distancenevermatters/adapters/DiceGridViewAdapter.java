package apm.muei.distancenevermatters.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.entities.Dice;
import apm.muei.distancenevermatters.fragments.DiceFragment;


public class DiceGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<Dice> diceList;
    private DiceFragment fragment;
    RadioButton checkBox;

    public DiceGridViewAdapter(Context context, DiceFragment fragment, List<Dice> diceList) {
        this.context = context;
        this.fragment = fragment;
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
    public View getView(final int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.resource_list_item, viewGroup, false);
        }

        TextView name = (TextView) view.findViewById(R.id.resourceName);
        ImageView image = (ImageView) view.findViewById(R.id.imageResourceView);
        checkBox = view.findViewById(R.id.checkBoxResource);

        Dice dice = getItem(position);
        name.setText(dice.getName());
        image.setImageResource(dice.getImg());
        image.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));

        checkBox.setChecked(false);
        if (fragment.getItemSelected() == position) {
            checkBox.setChecked(true);
        }

        if (fragment.getItemSelected() == -1) {
            fragment.getInputNumber().setText("");
        }

        fragment.getBtnLess().setEnabled(fragment.getItemSelected() != -1);
        fragment.getBtnMore().setEnabled(fragment.getItemSelected() != -1);
        fragment.getInputNumber().setEnabled(fragment.getItemSelected() != -1);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragment.getItemSelected() == position) {
                    fragment.setItemSelected(-1);

                } else {
                     fragment.setItemSelected(position);
                     fragment.getInputNumber().setText(Integer.toString(fragment.getDiceValues()[position]));
                }
                notifyDataSetChanged();
            }
        });

        return view;
    }

}