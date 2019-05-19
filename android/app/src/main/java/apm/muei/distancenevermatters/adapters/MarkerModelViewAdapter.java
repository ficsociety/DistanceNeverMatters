package apm.muei.distancenevermatters.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.entities.Marker;
import apm.muei.distancenevermatters.entities.Model;
import apm.muei.distancenevermatters.fragments.CreateGameFragment;
import butterknife.ButterKnife;


public class MarkerModelViewAdapter extends RecyclerView.Adapter<MarkerModelViewAdapter.ViewHolder> {

    private Map<Marker, Model> markerModel;
    private Context mContext;
    CreateGameFragment fragment;

    public MarkerModelViewAdapter(Context context, CreateGameFragment createGameFragment, Map<Marker, Model> markerModel){
        this.mContext = context;
        this.fragment = createGameFragment;
        this.markerModel = markerModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.marker_model_element, viewGroup, false);
        return new MarkerModelViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        List<Marker> markerList = new ArrayList<>();
        markerList.addAll(markerModel.keySet());

        Marker marker = markerList.get(position);
        Model model = markerModel.get(marker);

        viewHolder.bind(marker, model);
    }

    @Override
    public int getItemCount() {
        return markerModel.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView marker;
        TextView model;
        ImageButton deleteButton;

        public ViewHolder(View itemView){
            super(itemView);

            ButterKnife.bind(this, itemView);

            marker = itemView.findViewById(R.id.markerModelItemMarker);
            model = itemView.findViewById(R.id.markerModelItemModel);
            deleteButton = itemView.findViewById(R.id.markerModelDeleteBtn);

        }

        void bind (Marker marker, Model model){
            this.marker.setText(marker.getName());
            this.model.setText(model.getName());

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<Marker> markerList = new ArrayList<>();
                    markerList.addAll(markerModel.keySet());
                    Marker marker = markerList.get(getAdapterPosition());
                    fragment.removeMarkerModel(marker);
                    notifyDataSetChanged();
                }
            });
        }
    }

}
