package com.example.barmaster.ui.comunidad;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barmaster.R;
import com.example.barmaster.tools.DoubleClickListener;

public class ViewHolderRowCM extends RecyclerView.ViewHolder{
    public TextView niknName;
    public ImageView fotTrain;
    public TextView likeCounter;
    public TextView postTExt;
    public TextView timeDate;
    public TextView trainNanem;
    public TextView trainDuracion;

    public ViewHolderRowCM(@NonNull View itemView, final RecyViewAdapterCM.OnItemClickListener listener, final RecyViewAdapterCM.DoubleClickListener listenerdob) {
        super(itemView);
        niknName = itemView.findViewById(R.id.namepost);
        fotTrain = itemView.findViewById(R.id.fotopost);
        likeCounter = itemView.findViewById(R.id.likesnumberpost);
        postTExt = itemView.findViewById(R.id.textcommentpost);
        timeDate = itemView.findViewById(R.id.fechaTimepost);
        trainDuracion = itemView.findViewById(R.id.duracion_post);
        trainNanem = itemView.findViewById(R.id.nametrain_post);

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

        itemView.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onDoubleClick() {
                if (listenerdob != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listenerdob.onDoubleClick(position);
                    }
                }
            }
        });
    }
}
