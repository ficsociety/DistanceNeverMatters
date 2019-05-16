package apm.muei.distancenevermatters.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.entities.Dice;
import apm.muei.distancenevermatters.entities.dto.DiceResultDto;
import apm.muei.distancenevermatters.fragments.DiceFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DiceResultRecyclerAdapter extends RecyclerView.Adapter<DiceResultRecyclerAdapter.DiceResultViewHolder> {

    private DiceFragment fragment;
    private List<DiceResultDto> resultList;
    private List<Dice> diceList;

    // ViewHolder for game list items
    public class DiceResultViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.diceLabel)
        TextView diceLabel;

        @BindView(R.id.diceValue)
        TextView diceValue;


        public DiceResultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public DiceResultRecyclerAdapter(DiceFragment parentFragment, List<DiceResultDto> resultList, List<Dice> diceList) {

        this.fragment = parentFragment;
        this.resultList = resultList;
        this.diceList = diceList;
    }

    @Override
    public DiceResultRecyclerAdapter.DiceResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View gameView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dice_result_list_item, parent, false);

        DiceResultRecyclerAdapter.DiceResultViewHolder viewHolder = new DiceResultRecyclerAdapter.DiceResultViewHolder(gameView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DiceResultRecyclerAdapter.DiceResultViewHolder holder, int position) {
        holder.diceLabel.setText(resultList.get(position).getName());
        holder.diceValue.setText(Integer.toString(resultList.get(position).getValue()));

        /*
        for (List<Integer> item : resultList) {
            if (item.size() > 0) {
                for (Integer subItem : item) {
                    System.out.println(subItem);
                    holder.diceLabel.setText(diceList.get(i).getName());
                    holder.diceValue.setText(Integer.toString(subItem));
                }

            }
            i++;
        }*/
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

}

