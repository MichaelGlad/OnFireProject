package net.glm.googlemapsinstatest2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.glm.googlemapsinstatest2.Data.ModelUser;

import java.util.ArrayList;
import java.util.List;

import static net.glm.googlemapsinstatest2.Utility.Utility.getResizebleCircleBitmap;

/**
 * Created by Michael on 19/02/2018.
 */

public class RecyclerviewUsersOnMapAdapter extends RecyclerView.Adapter<RecyclerviewUsersOnMapAdapter.RecyclerViewUsersViewHoder> {
    private ArrayList<ModelUser> usersInAdapter = new ArrayList<>();

    public static final String USER_NUMBER = "User number";

    public void addAll (List<ModelUser> users){
        int pos = getItemCount();
        this.usersInAdapter.addAll(users);
        notifyItemRangeChanged(pos, this.usersInAdapter.size());
            }


    @Override
    public RecyclerViewUsersViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card,parent,false);
        RecyclerViewUsersViewHoder userViewHoder = new RecyclerViewUsersViewHoder(view);
        view.setOnClickListener(userViewHoder);

        return userViewHoder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewUsersViewHoder holder, int position) {
        holder.bind(usersInAdapter.get(position));

    }

    @Override
    public int getItemCount() {
        return usersInAdapter.size();
    }

    public class RecyclerViewUsersViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView userName;
        private ImageView userImage;
        private TextView userAge;
        private TextView userStatus;


        public RecyclerViewUsersViewHoder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tv_card_user_name);
            userImage = itemView.findViewById(R.id.image_view_user);
            userStatus = itemView.findViewById(R.id.tv_card_user_status_data);
            userAge = itemView.findViewById(R.id.tv_card_user_age_data);
        }

        public void bind (ModelUser user){
            userName.setText(user.getName());
            userAge.setText(String.valueOf(user.getAge()));
            userStatus.setText(user.getStatus());
            Bitmap circleBitmap =  getResizebleCircleBitmap(BitmapFactory.decodeResource(itemView.getResources(), user.getImgId()),(int)  30);
            userImage.setImageBitmap(circleBitmap);

        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(v.getContext(),ProfileActivity.class);
            intent.putExtra(USER_NUMBER,getAdapterPosition());
            v.getContext().startActivity(intent);

        }
    }
}
