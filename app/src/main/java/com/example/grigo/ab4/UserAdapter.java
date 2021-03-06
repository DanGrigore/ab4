package com.example.grigo.ab4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.grigo.ab4.models.UserModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by grigo on 03-Mar-18.
 */

public class UserAdapter extends ArrayAdapter {

    private List<UserModel> userModelList;
    private int resource;
    private LayoutInflater inflater;


    public UserAdapter(@NonNull Context context, int resource, @NonNull List<UserModel> objects) {
        super(context, resource, objects);
        userModelList = objects;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(resource, null);
            holder.profilePicture = (ImageView) convertView.findViewById(R.id.profilePicture);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);

        ImageLoader.getInstance().displayImage(userModelList.get(position).getProfile_image(),
                holder.profilePicture, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
        holder.name.setText(userModelList.get(position).getDisplay_name());

        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(),DetailsPage.class);
                intent.putExtra("name",userModelList.get(position).getDisplay_name());
                intent.putExtra("profilePicutre",userModelList.get(position).getProfile_image());
                intent.putExtra("location",userModelList.get(position).getLocation());
                intent.putExtra("profilePic",userModelList.get(position).getProfile_image());
                int bronze = userModelList.get(position).getBadge_counts().getBronze();
                int silver = userModelList.get(position).getBadge_counts().getSilver();
                int gold = userModelList.get(position).getBadge_counts().getGold();
                intent.putExtra("bronze", bronze);
                intent.putExtra("silver", silver);
                intent.putExtra("gold", gold);

                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        private ImageView profilePicture;
        private TextView name;
    }
}
