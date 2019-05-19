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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
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

    public interface OnItemDownloadSelected {
        void onDownloadSelected();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.resource_grid_item_download, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(maps.get(position).getUrl().toString())
                .into(viewHolder.image);

        viewHolder.bind(maps.get(position));

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://i.imgur.com/1YFuBjR.jpg";
                String name = "Map";
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
        return this.maps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        RadioButton checkBox;
        Button button;

        public ViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.imageResourceGridDownload);
            name = itemView.findViewById(R.id.resourceNameGridDownload);
            checkBox = itemView.findViewById(R.id.checkBoxResourceGridDownload);
            button = itemView.findViewById(R.id.buttonResourceGridDownload);

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
