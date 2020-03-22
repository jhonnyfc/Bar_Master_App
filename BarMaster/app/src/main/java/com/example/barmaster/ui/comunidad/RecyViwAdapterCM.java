package com.example.barmaster.ui.comunidad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barmaster.R;
import com.example.barmaster.models.Post;

import java.util.ArrayList;

public class RecyViwAdapterCM extends RecyclerView.Adapter<ViewHolderRowCM>{
    private Context myContext;
    private ArrayList<Post> myListOfRows;
    private OnItemClickListener myCLickListener;

    public RecyViwAdapterCM(ArrayList<Post> listaIn) {
        myListOfRows = listaIn;
    }

    @NonNull
    @Override
    public ViewHolderRowCM onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderRowCM myViewHolder = null;
        myContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(myContext);

        View v1 = inflater.inflate(R.layout.z_rowcard_rutinagrup, parent,false);
        myViewHolder = new ViewHolderRowCM(v1,myCLickListener);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRowCM holder, int position) {
        Post currentItem = myListOfRows.get(position);

        if (currentItem.getComment() == null){
            holder.niknName.setText(currentItem.getIdCreator());
            holder.fotTrain.setImageBitmap(null);//hacer glide para obtenr ese dato
            holder.likeCounter.setText(null);//hacer consulta para obtenr este dato
            holder.postTExt.setText((currentItem.getComment()));
        }else {
//            holder.grupName.setText(currentItem.getTitle());
//
//
//            RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.progresspiner).error(R.drawable.progresspiner);
//
//            Glide.with(myContext)
//                    .load(currentItem.getImageId())
//                    .apply(requestOptions)
//                    .into(holder.grupFoto);
        }
    }

    @Override
    public int getItemCount() {
        return myListOfRows.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        myCLickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
