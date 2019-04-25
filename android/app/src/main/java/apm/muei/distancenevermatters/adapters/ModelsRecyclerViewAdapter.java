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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.entities.Map;
import apm.muei.distancenevermatters.entities.Model;

public class ModelsRecyclerViewAdapter extends RecyclerView.Adapter<ModelsRecyclerViewAdapter.ViewHolder>{

    private List<Model> models;
    private Context mContext;
    private int lastClicked = -1;


    public ModelsRecyclerViewAdapter(Context mContext, List<Model> models) {
        this.models = models;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.resource_grid_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .asBitmap()
                .load(models.get(position).getPreview().toString())
                .apply(requestOptions)
                .into(viewHolder.image);

        viewHolder.name.setText(models.get(position).getName());


        viewHolder.checkBox.setChecked(position == lastClicked);


        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastClicked = viewHolder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        CheckBox checkBox;

        public ViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.imageResourceGrid);
            name = itemView.findViewById(R.id.resourceNameGrid);
            checkBox = itemView.findViewById(R.id.checkBoxResourceGrid);

        }
    }
}
