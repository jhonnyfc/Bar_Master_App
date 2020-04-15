package com.example.barmaster.ui.comunidad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.barmaster.R;
import com.example.barmaster.models.Post;
import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;

public class RecyViewAdapterCM extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    final private String LikesTable = "Likes";

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private Context myContext;
    private ArrayList<Post> myListOfRows;
    private OnItemClickListener myCLickListener;
    private DoubleClickListener myCLickListenerDob;

    public RecyViewAdapterCM(ArrayList<Post> listaIn) {
        myListOfRows = listaIn;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        myContext = parent.getContext();

        if (viewType == VIEW_TYPE_ITEM) {
            LayoutInflater inflater = LayoutInflater.from(myContext);

            View v1 = inflater.inflate(R.layout.z_rowcard_post, parent,false);
            return new ViewHolderRowCM(v1,myCLickListener,myCLickListenerDob);
        } else {
            View view = LayoutInflater.from(myContext).inflate(R.layout.x_item_loading ,parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holderIn, int position) {
        if (holderIn instanceof ViewHolderRowCM){
            ViewHolderRowCM holder = (ViewHolderRowCM) holderIn;

            final Post currentItem = myListOfRows.get(position);
            holder.niknName.setText("@" + currentItem.getIdCreator());
            holder.postTExt.setText(currentItem.getComment());
            holder.trainNanem.setText(currentItem.getRutinaName());
            holder.trainDuracion.setText("Duracion: " + currentItem.getDuration().toString() + " mins");
            setTime(holder, currentItem);// Se pone el tiempo transcurrido desde la publicacion

            if (currentItem.getLikesId().equals("0")) {//Si aun no se ha registrado nigun id de like
                holder.likeCounter.setText(currentItem.getLikesId());//hacer consulta para obtenr este dato

            } else {
                ParseQuery<ParseObject> misPostLikes = new ParseQuery(LikesTable);
                misPostLikes.whereEqualTo("likeOfPost",currentItem.getIdObjeto());
                misPostLikes.countInBackground(new CountCallback() {
                    @Override
                    public void done(int count, ParseException e) {
                        ViewHolderRowCM holder = (ViewHolderRowCM) holderIn;
                        if (count >= 1){
                            holder.likeCounter.setText(String.valueOf(count));//hacer consulta para obtenr este dato
                        } else
                            holder.likeCounter.setText("0");
                    }
                });
            }

            RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.progresspiner).error(R.drawable.progresspiner);

            Glide.with(myContext)
                    .load(currentItem.getFotoPostUrl())
                    .apply(requestOptions)
                    .into(holder.fotTrain);

        } else if (holderIn instanceof LoadingViewHolder){
            showLoadingView((LoadingViewHolder) holderIn, position);
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

    public void setOnDoulbeClickListener(DoubleClickListener listener) {
        myCLickListenerDob = listener;
    }

    public interface DoubleClickListener {
        void onDoubleClick(int position);
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public int getItemViewType(int position) {
        return myListOfRows.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setTime(ViewHolderRowCM holder,Post currentItem){
        Integer timepoDias = Math.round( (new Date().getTime() - currentItem.getFecha().getTime())/(24*60*60*1000) );
        if (timepoDias == 1)
            holder.timeDate.setText(timepoDias.toString()+" day ago");
        else if (timepoDias > 1)
            holder.timeDate.setText(timepoDias.toString()+" days ago");
        else{
            Integer timepoHoras = Math.round( (new Date().getTime() - currentItem.getFecha().getTime())/(60*60*1000) );
            if (timepoHoras == 1)
                holder.timeDate.setText(timepoHoras.toString()+" hour ago");
            else if (timepoHoras > 1)
                holder.timeDate.setText(timepoHoras.toString()+" hours ago");
            else{
                Integer timepoMins = Math.round( (new Date().getTime() - currentItem.getFecha().getTime())/(60*1000) );
                if (timepoMins == 1)
                    holder.timeDate.setText(timepoMins.toString()+" minute ago");
                else if (timepoMins > 1)
                    holder.timeDate.setText(timepoMins.toString()+" minutes ago");
                else{
                    Integer timepoSec = Math.round( (new Date().getTime() - currentItem.getFecha().getTime())/(1000) );
                    if (timepoSec == 1)
                        holder.timeDate.setText(timepoSec.toString()+" second ago");
                    else if (timepoSec > 1)
                        holder.timeDate.setText(timepoSec.toString()+" seconds ago");
                }
            }
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }
}
