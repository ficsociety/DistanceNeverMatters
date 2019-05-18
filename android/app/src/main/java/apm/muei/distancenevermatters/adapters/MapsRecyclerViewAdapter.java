package apm.muei.distancenevermatters.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.entities.Map;
import apm.muei.distancenevermatters.fragments.CreateGameFragment;

public class MapsRecyclerViewAdapter extends RecyclerView.Adapter<MapsRecyclerViewAdapter.ViewHolder>{

    private List<Map> maps;
    private Context mContext;
    CreateGameFragment fragment;
    private int lastClicked = -1;


    public MapsRecyclerViewAdapter(Context mContext, CreateGameFragment fragment, int lastClicked) {
        this.mContext = mContext;
        this.fragment = fragment;
        this.lastClicked = lastClicked;
    }

    public void setMaps(List<Map> maps) {
        this.maps = maps;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.resource_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(maps.get(position).getUrl().toString())
                .into(viewHolder.image);

        viewHolder.bind(maps.get(position));
    }

    @Override
    public int getItemCount() {
        return this.maps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        RadioButton checkBox;

        public ViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.imageResourceView);
            name = itemView.findViewById(R.id.resourceName);
            checkBox = itemView.findViewById(R.id.checkBoxResource);

        }

        void bind(final Map map){
            if (lastClicked == -1){
                checkBox.setChecked(false);
            } else {
                if (lastClicked == getAdapterPosition() || fragment.getSelectedMap() == getAdapterPosition()) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }
            name.setText(map.getName());

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkBox.setChecked(true);
                    if (lastClicked != getAdapterPosition()) {
                        notifyItemChanged(lastClicked);
                        lastClicked = getAdapterPosition();
                        fragment.setSelectedMap(getAdapterPosition());
                    }
                }
            });
        }
    }
}
