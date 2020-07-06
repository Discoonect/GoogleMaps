package com.test.googlemaps.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.test.googlemaps.MapsActivity;
import com.test.googlemaps.R;
import com.test.googlemaps.model.Results;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    Context context;
    ArrayList<Results> resultsList;

    public RecyclerViewAdapter(Context context, ArrayList<Results> resultsList){
        this.context = context;
        this.resultsList = resultsList;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.result_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Results results = resultsList.get(position);

        String name = results.getName();
        String vic = results.getVicinity();

        holder.txtVic.setText(vic);
        holder.txtName.setText(name);

    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtName;
        public TextView txtVic;
        public CardView cardView;


        public ViewHolder(@Nullable View itemView){
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtVic = itemView.findViewById(R.id.txtVic);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    Results results = resultsList.get(index);
                    Intent i = new Intent(context, MapsActivity.class);
                    i.putExtra("results",results);
                    context.startActivity(i);
                }
            });

        }

        public void openWebPage(String url){
            Uri webPage = Uri.parse(url);
            Intent i = new Intent(Intent.ACTION_VIEW,webPage);
            if(i.resolveActivity(context.getPackageManager()) != null){
                context.startActivity(i);
            }
        }
    }
}

