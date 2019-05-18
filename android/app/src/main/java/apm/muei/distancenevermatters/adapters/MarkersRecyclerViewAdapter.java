package apm.muei.distancenevermatters.adapters;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.entities.Marker;
import apm.muei.distancenevermatters.entities.Model;
import apm.muei.distancenevermatters.fragments.MarkerModelInterface;
import apm.muei.distancenevermatters.fragments.MarkersModelsFragment;
import butterknife.ButterKnife;


public class MarkersRecyclerViewAdapter extends RecyclerView.Adapter<MarkersRecyclerViewAdapter.ViewHolder> {

    private List<Marker> markers;
    private Context mContext;
    private int lastClicked = -1;

    private MarkerModelInterface fragment;

    public void setMarkers(List<Marker> markers) {
        this.markers = markers;
    }

    public interface OnItemDownloadSelected {
        void onDownloadSelected();
    }

    public MarkersRecyclerViewAdapter(Context mContext, MarkerModelInterface fragment) {
        this.markers = markers;
        this.mContext = mContext;
        if (fragment instanceof OnItemDownloadSelected) {
            this.fragment = fragment;
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

        viewHolder.bind(markers.get(position));


        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positiondownload = viewHolder.getAdapterPosition();
                String url = markers.get(positiondownload).getUrl().toString();
                String name = markers.get(positiondownload).getName();
                Context cxt = fragment.getActivity().getApplicationContext();
                descargar(cxt, url, name);

            }

            public void descargar(Context cxt, String urldownload, String name) {
                if (ContextCompat.checkSelfPermission(fragment.getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(cxt, "Descargando", Toast.LENGTH_LONG).show();
                    File direct =
                            new File(Environment
                                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                                    .getAbsolutePath() + "/" + "Marcadores" + "/");

                    if (!direct.exists()) {
                        direct.mkdir();
                    }

                    DownloadManager dm = (DownloadManager) fragment.getActivity().getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri downloadUri = Uri.parse(urldownload);
                    DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                            .setAllowedOverRoaming(false)
                            .setTitle(name)
                            .setMimeType("image/jpg")
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                                    File.separator + "Marcadores" + File.separator + name +".jpg");

                    dm.enqueue(request);
                }
                else{
                    ActivityCompat.requestPermissions(fragment.getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            2);
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return this.markers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        RadioButton checkBox;
        Button button;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            image = itemView.findViewById(R.id.imageResourceGridDownload);
            name = itemView.findViewById(R.id.resourceNameGridDownload);
            checkBox = itemView.findViewById(R.id.checkBoxResourceGridDownload);
            button = itemView.findViewById(R.id.buttonResourceGridDownload);
        }


        void bind(final Marker marker) {
            if (lastClicked == -1) {
                checkBox.setChecked(false);
            } else {
                if (lastClicked == getAdapterPosition()) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }

            name.setText(marker.getName());

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkBox.setChecked(true);
                    if (lastClicked != getAdapterPosition()) {
                        fragment.setMarker(markers.get(getAdapterPosition()));
                        notifyItemChanged(lastClicked);
                        lastClicked = getAdapterPosition();

                    }

                }
            });
        }
    }

}
