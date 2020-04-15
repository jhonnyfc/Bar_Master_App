package com.example.barmaster.ui.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barmaster.R;

public class ViewHolderRowHM extends RecyclerView.ViewHolder{
    public ImageView fotoEjer;
    public TextView ejerName;

    public ViewHolderRowHM(@NonNull View itemView, final RecyViewAdapterHM.OnItemClickListener listener) {
        super(itemView);
        fotoEjer = itemView.findViewById(R.id.foto_home);
        ejerName = itemView.findViewById(R.id.namejer_home);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }
}
