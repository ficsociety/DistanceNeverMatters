package apm.muei.distancenevermatters.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.fragments.DiceFragment;

public class DiceRecyclerViewAdapter extends RecyclerView.Adapter<DiceRecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    DiceFragment fragment;

    public DiceRecyclerViewAdapter(Context mContext, DiceFragment fragment) {
        this.mContext = mContext;
        this.fragment = fragment;
    }

    @Override
    public DiceRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.resource_list_item, viewGroup, false);
        return new DiceRecyclerViewAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final DiceRecyclerViewAdapter.ViewHolder viewHolder, final int position) {

        System.out.println("dasdasdas");
        viewHolder.name.setText("dsadsadas");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        CheckBox checkBox;

        public ViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.imageResourceView);
            name = itemView.findViewById(R.id.resourceName);
            checkBox = itemView.findViewById(R.id.checkBoxResource);
        }
    }
}
