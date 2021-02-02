package com.raontech.displaymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.viewHolder> {
    Context context;
    ArrayList<ImageModel> imageArrayList;
    public OnItemClickListener onItemClickListener;

    public ImageAdapter(Context context, ArrayList<ImageModel> imageArrayList) {
        this.context = context;
        this.imageArrayList = imageArrayList;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_list, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int i) {
        holder.title.setText(imageArrayList.get(i).getImageTitle());
    }

    @Override
    public int getItemCount() {
        return imageArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public viewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition(), v);
                }
            });
        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(int pos, View v);
    }
}
