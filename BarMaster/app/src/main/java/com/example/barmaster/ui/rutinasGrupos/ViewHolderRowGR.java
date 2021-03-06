package com.example.barmaster.ui.rutinasGrupos;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.barmaster.R;

public class ViewHolderRowGR extends RecyclerView.ViewHolder {
    public TextView grupName;
    public ImageView grupFoto;

    public ViewHolderRowGR(View itemView, final RecyViewAdapterGR.OnItemClickListener listener) {
        super(itemView);
        grupName = itemView.findViewById(R.id.textoCardRow);
        grupFoto = itemView.findViewById(R.id.fotoCardRow);

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
