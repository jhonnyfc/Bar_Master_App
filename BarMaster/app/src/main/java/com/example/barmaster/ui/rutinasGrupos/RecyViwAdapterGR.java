package com.example.barmaster.ui.rutinasGrupos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barmaster.R;

import java.util.ArrayList;

public class RecyViwAdapterGR extends RecyclerView.Adapter<ViewHoldRowGR> {
    private ArrayList<CardRowDataModelGR> listOfRows;
    private OnItemClickListener myListener;

    public RecyViwAdapterGR(ArrayList<CardRowDataModelGR> listaIn) {
        listOfRows = listaIn;
    }

    @NonNull
    @Override
    public ViewHoldRowGR onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHoldRowGR myViewHolder = null;
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

        holder.grupFoto.setImageBitmap(currentItem.getImageId());
        holder.grupName.setText(currentItem.getTitle());
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
