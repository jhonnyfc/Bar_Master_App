package com.example.barmaster.ui.rutinasGrupos;

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
import com.example.barmaster.models.GrupoMuscularRutina;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

public class RecyViewAdapterGR extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String tableFotos = "FotosAll";

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private Context myContext;
    private ArrayList<GrupoMuscularRutina> mylistOfRows;
    private OnItemClickListener myListener;

    public RecyViewAdapterGR(ArrayList<GrupoMuscularRutina> listaIn) {
        mylistOfRows = listaIn;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        myContext = parent.getContext();

        if (viewType == VIEW_TYPE_ITEM) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View v1 = inflater.inflate(R.layout.z_rowcard_grupo_rutina, parent, false);
            return new ViewHolderRowGR(v1, myListener);
        } else {
            View view = LayoutInflater.from(myContext).inflate(R.layout.x_item_loading ,parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderIn, int position) {
        if (holderIn instanceof ViewHolderRowGR){
            final ViewHolderRowGR holder = (ViewHolderRowGR) holderIn;
            final GrupoMuscularRutina currentItem = mylistOfRows.get(position);

            holder.grupName.setText(currentItem.getTitle());

            ParseQuery<ParseObject> query = ParseQuery.getQuery(tableFotos);
            query.whereEqualTo("fotoName",currentItem.getImageId());
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    String urlFoto = object.getParseFile("picture").getUrl();

                    RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.progresspiner).error(R.drawable.progresspiner);

                    Glide.with(myContext)
                            .load(urlFoto)
                            .apply(requestOptions)
                            .into(holder.grupFoto);
                }
            });

        }else if (holderIn instanceof LoadingViewHolder){
            showLoadingView((LoadingViewHolder) holderIn, position);
        }
    }

    @Override
    public int getItemCount() {
        return mylistOfRows.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        myListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    public int getItemViewType(int position) {
        return mylistOfRows.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }
}
