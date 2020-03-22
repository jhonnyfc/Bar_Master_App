package com.example.barmaster.ui.rutinasGrupos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.barmaster.R;

import java.util.ArrayList;

public class RecyViwAdapterGR extends RecyclerView.Adapter<ViewHoldRowGR> {
    private Context myContext;
    private ArrayList<CardRowDataModelGR> listOfRows;
    private OnItemClickListener myListener;

    public RecyViwAdapterGR(ArrayList<CardRowDataModelGR> listaIn) {
        listOfRows = listaIn;
    }

    @NonNull
    @Override
    public ViewHoldRowGR onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHoldRowGR myViewHolder = null;
        myContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //        switch (viewType) {
//            case ITEM:
        View v1 = inflater.inflate(R.layout.z_rowcard_rutinagrup, parent,false);
        myViewHolder = new ViewHoldRowGR(v1,myListener);
//                break;
//            case LOADING:
////                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
////                viewHolder = new LoadingVH(v2);
//                break;
//        }

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHoldRowGR holder, int position) {
        CardRowDataModelGR currentItem = listOfRows.get(position);

        if (currentItem.getImageId() == null){
            holder.grupName.setText(currentItem.getTitle());
            holder.grupFoto.setImageBitmap(null);
        }else {
            holder.grupName.setText(currentItem.getTitle());


        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.progresspiner).error(R.drawable.progresspiner);

            Glide.with(myContext)
                    .load(currentItem.getImageId())
                    .apply(requestOptions)
                    .into(holder.grupFoto);
        }
    }

    @Override
    public int getItemCount() {
        return listOfRows.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        myListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

//    public boolean addAll(ArrayList<CardRowDataModelGR> myRecyListCards){
//        listOfRows = myRecyListCards;
//        return true;
//    }
}
