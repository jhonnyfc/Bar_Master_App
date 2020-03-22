package com.example.barmaster.ui.comunidad;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barmaster.R;

public class ViewHolderRowCM extends RecyclerView.ViewHolder{
    public TextView niknName;
    public ImageView fotTrain;
    public TextView likeCounter;
    public TextView postTExt;

    public ViewHolderRowCM(@NonNull View itemView, final RecyViwAdapterCM.OnItemClickListener listener) {
        super(itemView);
        niknName = itemView.findViewById(R.id.namepost);
        fotTrain = itemView.findViewById(R.id.fotopost);
        likeCounter = itemView.findViewById(R.id.likesnumberpost);
        postTExt = itemView.findViewById(R.id.textcommentpost);

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
