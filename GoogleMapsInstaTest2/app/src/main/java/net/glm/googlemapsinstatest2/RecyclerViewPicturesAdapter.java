package net.glm.googlemapsinstatest2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.glm.googlemapsinstatest2.Data.ModelUser;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPicturesAdapter extends RecyclerView.Adapter<RecyclerViewPicturesAdapter.PictureViewHolder>{
    private List<Integer> picturesInAdapter = new ArrayList<>();

    public RecyclerViewPicturesAdapter(List<Integer> picturesInAdapter) {
        this.picturesInAdapter = picturesInAdapter;
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pictures_in_gridlayout,parent,false);
        PictureViewHolder pictureViewHolder = new PictureViewHolder(view);


        return pictureViewHolder;
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
        holder.bindImage(position);

    }

    @Override
    public int getItemCount() {
        return picturesInAdapter.size();
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder {

        ImageView pictureImageView;
        public PictureViewHolder(View itemView) {

            super(itemView);

            pictureImageView = itemView.findViewById(R.id.iv_picture_in_grid);
        }

        public void bindImage (int position){
            pictureImageView.setImageResource(picturesInAdapter.get(position));

        }
    }

}
