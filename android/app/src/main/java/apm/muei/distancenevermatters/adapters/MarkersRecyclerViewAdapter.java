package apm.muei.distancenevermatters.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.entities.Marker;
import butterknife.OnClick;
import butterknife.ButterKnife;

public class MarkersRecyclerViewAdapter extends RecyclerView.Adapter<MarkersRecyclerViewAdapter.ViewHolder> {

    private List<Marker> markers;
    private Context mContext;
    private int lastClicked = -1;

    private Fragment fragment;

    public interface OnItemDownloadSelected {
        void onDownloadSelected();
    }

    public MarkersRecyclerViewAdapter(Context mContext, List<Marker> markers, Fragment parentFragment) {
        this.markers =markers;
        this.mContext = mContext;
        if (parentFragment instanceof OnItemDownloadSelected) {
            fragment = parentFragment;
        } else {
            throw new RuntimeException(fragment.toString()
                    + " must implement OnGameDetailsListener");
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.resource_grid_item_download, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .asBitmap()
                .load(markers.get(position).getUrl().toString())
                .apply(requestOptions)
                .into(viewHolder.image);

        viewHolder.name.setText(markers.get(position).getName());


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
        return this.markers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        CheckBox checkBox;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            image = itemView.findViewById(R.id.imageResourceGridDownload);
            name = itemView.findViewById(R.id.resourceNameGridDownload);
            checkBox = itemView.findViewById(R.id.checkBoxResourceGridDownload);

        }

        @OnClick(R.id.buttonResourceGridDownload)
        public void downloadMarker(){
            Toast.makeText(fragment.getActivity().getApplicationContext(),
                    "Descargando", Toast.LENGTH_LONG).show();
        }

    }

}
