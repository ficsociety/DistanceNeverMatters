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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.entities.Model;
import apm.muei.distancenevermatters.fragments.MarkersModelsFragment;

public class ModelsRecyclerViewAdapter extends RecyclerView.Adapter<ModelsRecyclerViewAdapter.ViewHolder>{

    private List<Model> models;
    private Context mContext;
    private int lastClicked = -1;
    private MarkersModelsFragment fragment;


    public ModelsRecyclerViewAdapter(Context mContext, MarkersModelsFragment fragment) {
        this.mContext = mContext;
        this.fragment = fragment;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.resource_list_item, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .asBitmap()
                .load(models.get(position).getPreview().toString())
                .into(viewHolder.image);

        viewHolder.bind(models.get(position));

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
            image = itemView.findViewById(R.id.imageResourceView);
            name = itemView.findViewById(R.id.resourceName);
            checkBox = itemView.findViewById(R.id.checkBoxResource);
        }

        void bind(final Model model){
            if (lastClicked == -1){
                checkBox.setChecked(false);
            } else {
                if (lastClicked == getAdapterPosition()) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }

            name.setText(model.getName());

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkBox.setChecked(true);
                    if (lastClicked != getAdapterPosition()) {
                        fragment.setModel(models.get(getAdapterPosition()));
                        notifyItemChanged(lastClicked);
                        lastClicked =  getAdapterPosition();

                    }

                }
            });
        }
    }
}
