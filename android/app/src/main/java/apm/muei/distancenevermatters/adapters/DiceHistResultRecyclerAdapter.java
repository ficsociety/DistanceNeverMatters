package apm.muei.distancenevermatters.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.Server.DiceResult;
import apm.muei.distancenevermatters.activities.GameActivity;
import apm.muei.distancenevermatters.entities.Dice;
import apm.muei.distancenevermatters.entities.dto.DiceResultDto;
import apm.muei.distancenevermatters.fragments.DiceFragment;
import apm.muei.distancenevermatters.fragments.DiceHistoricFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DiceHistResultRecyclerAdapter extends RecyclerView.Adapter<DiceHistResultRecyclerAdapter.DiceHistResultViewHolder> {

    private DiceHistoricFragment fragment;

    // ViewHolder for game list items
    public class DiceHistResultViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.histUser)
        TextView userLabel;

        @BindView(R.id.histResultLabel)
        TextView resultLabel;

        @BindView(R.id.histResultValue)
        TextView resultValue;

        @BindView(R.id.histDesg)
        TextView desglose;


        public DiceHistResultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public DiceHistResultRecyclerAdapter(DiceHistoricFragment parentFragment) {

        this.fragment = parentFragment;
    }

    @Override
    public DiceHistResultRecyclerAdapter.DiceHistResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View gameView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dice_hist_list_item, parent, false);

        DiceHistResultRecyclerAdapter.DiceHistResultViewHolder viewHolder = new DiceHistResultRecyclerAdapter.DiceHistResultViewHolder(gameView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DiceHistResultRecyclerAdapter.DiceHistResultViewHolder holder, int position) {

        Queue<DiceResult> fifo = ((GameActivity) fragment.getActivity()).getFifo();
        List<DiceResult> list = new ArrayList(fifo);
        holder.userLabel.setText(list.get(position).getUser());

        String text = "";
        Integer total = 0;
        Integer index = 0;
        String previosDice = "";
        for (DiceResultDto dto : list.get(position).getResutlDice()) {
            if (previosDice.equals(dto.getName())) {
                text = text + ", " + Integer.toString(dto.getValue());
            } else {
                text = !text.equals("") ? text + "; " : text + "";
                text = text + dto.getName() + ": " + Integer.toString(dto.getValue());
            }
            total += dto.getValue();
            previosDice = dto.getName();
            index++;
        }
        holder.resultValue.setText(Integer.toString(total));
        holder.desglose.setText(text);

        //holder.diceLabel.setText(resultList.get(position).getName());
        //holder.diceValue.setText(Integer.toString(resultList.get(position).getValue()));
    }

    @Override
    public int getItemCount() {
        return ((GameActivity) fragment.getActivity()).getFifo().size();
    }

}

