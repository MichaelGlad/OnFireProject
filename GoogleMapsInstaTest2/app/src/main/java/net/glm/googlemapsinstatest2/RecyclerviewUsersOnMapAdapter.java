package net.glm.googlemapsinstatest2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.glm.googlemapsinstatest2.Data.ModelUser1;

import java.util.ArrayList;
import java.util.List;

import static net.glm.googlemapsinstatest2.Utility.Utility.getResizebleCircleBitmap;

/**
 * Created by Michael on 19/02/2018.
 */

public class RecyclerviewUsersOnMapAdapter extends RecyclerView.Adapter<RecyclerviewUsersOnMapAdapter.RecyclerViewUsersViewHoder> {
    private ArrayList<ModelUser1> usersInAdapter = new ArrayList<>();

    public void addAll (List<ModelUser1> users){
        int pos = getItemCount();
        this.usersInAdapter.addAll(users);
        notifyItemRangeChanged(pos, this.usersInAdapter.size());
            }


    @Override
    public RecyclerViewUsersViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card,parent,false);

        return new RecyclerViewUsersViewHoder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewUsersViewHoder holder, int position) {
        holder.bind(usersInAdapter.get(position));

    }

    @Override
    public int getItemCount() {
        return usersInAdapter.size();
    }

    public class RecyclerViewUsersViewHoder extends RecyclerView.ViewHolder{
        private TextView userName;
        private ImageView userImage;


        public RecyclerViewUsersViewHoder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tv_name);
            userImage = itemView.findViewById(R.id.image_view_user);
        }

        public void bind (ModelUser1 user){
            userName.setText(user.getName());
            Bitmap circleBitmap =  getResizebleCircleBitmap(BitmapFactory.decodeResource(itemView.getResources(), user.getImgId()));
            userImage.setImageBitmap(circleBitmap);

        }
    }
}
