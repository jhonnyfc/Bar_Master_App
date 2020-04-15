package com.example.barmaster.ui.comunidad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barmaster.R;
import com.example.barmaster.models.Post;
import com.example.barmaster.sharedData.MyAppDataControler;
import com.example.barmaster.ui.comunidad.creaPost.PostCreator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.CountCallback;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ComunidadCMFragment extends Fragment {
    final private Integer SIZE_OF_LOAD = 2;
    final private String TableName = "Posts";
    final private String LikesTable = "Likes";
    private boolean isLoading;

    private ComunidadViewModel mViewModel;

    private ArrayList<Post> myRecyListCardsCM;
    private RecyclerView myRecyclerViewCM;
    private RecyViwAdapterCM myAdapterCM;
    private RecyclerView.LayoutManager myLayoutManagerCM;

    private FloatingActionButton myFloatButtom;

    public static ComunidadCMFragment newInstance() {
        return new ComunidadCMFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        isLoading = false;
        View root = inflater.inflate(R.layout.comunidad_fragment, container, false);

        myRecyListCardsCM = new ArrayList<>();
        myRecyListCardsCM.add(null);// setear un row por defecto creadndo pos a null maybe

        myRecyclerViewCM = root.findViewById(R.id.lista_post);
        myRecyclerViewCM.setHasFixedSize(true);
        myLayoutManagerCM = new LinearLayoutManager(getActivity());
        myAdapterCM = new RecyViwAdapterCM(myRecyListCardsCM);

        myRecyclerViewCM.setLayoutManager(myLayoutManagerCM);
        myRecyclerViewCM.setAdapter(myAdapterCM);

        myAdapterCM.setOnItemClickListener(new RecyViwAdapterCM.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });

        final MyAppDataControler controler = new MyAppDataControler(getContext());
        if (controler.exsitenDatos() == true){

            // Boton: Se permite que se de like solo si esta registrado
            myAdapterCM.setOnDoulbeClickListener(new RecyViwAdapterCM.DoubleClickListener() {
                @Override
                public void onDoubleClick(final int position) {
                if (myRecyListCardsCM.get(position).getLikesId().equals("0")){//El post nunca a recivido un like
                    ParseQuery<ParseObject> misPost = new ParseQuery(TableName);
                    misPost.getInBackground(myRecyListCardsCM.get(position).getIdObjeto(), new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            object.put("likeId",object.getObjectId());
                            object.saveInBackground();

                            ParseObject newLike = new ParseObject(LikesTable);
                            newLike.put("likeOfPost",object.getObjectId());
                            newLike.put("idUser",controler.getMyData().getNickName());
                            newLike.saveInBackground();
                        }
                    });
                } else {
                    ParseQuery<ParseObject> misPostLikes = new ParseQuery(LikesTable);
                    misPostLikes.whereEqualTo("likeOfPost",myRecyListCardsCM.get(position).getLikesId()).whereEqualTo("idUser",controler.getMyData().getNickName());
                    misPostLikes.countInBackground(new CountCallback() {
                        @Override
                        public void done(int count, ParseException e) {
                            if (count >= 1){//Quitar like
                                ParseQuery<ParseObject> miPostLike = new ParseQuery(LikesTable);
                                miPostLike.whereEqualTo("likeOfPost",myRecyListCardsCM.get(position).getLikesId()).whereEqualTo("idUser",controler.getMyData().getNickName());
                                miPostLike.getFirstInBackground(new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject object, ParseException e) {
                                        object.deleteInBackground(new DeleteCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                myAdapterCM.notifyItemChanged(position);
                                            }
                                        });
                                    }
                                });
                            } else {//Crear el like
                                ParseObject newLike = new ParseObject(LikesTable);
                                newLike.put("likeOfPost",myRecyListCardsCM.get(position).getLikesId());
                                newLike.put("idUser",controler.getMyData().getNickName());
                                newLike.saveInBackground();
                                myAdapterCM.notifyItemChanged(position);
                            }
                        }
                    });
                }
                }
            });

            //Bton: Si ha iniciado sesion debe aparecer el boton flotante para agregar nuevo post
            myFloatButtom = root.findViewById(R.id.fbotadd_post);
            myFloatButtom.setVisibility(View.VISIBLE);
            myFloatButtom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //LLamar a la nueva actividad/fragmento que en la que se podra crear un nuevo post
                    Fragment someFragment = new PostCreator();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, someFragment ); // give your fragment container id in first parameter
                    ((FragmentTransaction) transaction).addToBackStack(null);  // if written, this transaction will be added to backstack
                    transaction.commit();
                }
            });
        }
        loadPostIni();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void loadPostIni(){
        ParseQuery<ParseObject> myPosts = new ParseQuery(TableName);
        myPosts.orderByDescending("_created_at").setLimit(SIZE_OF_LOAD);
        myPosts.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    myRecyListCardsCM.remove(myRecyListCardsCM.size() - 1);
                    myAdapterCM.notifyItemRemoved(myRecyListCardsCM.size());
                    for (ParseObject ob : objects) {
                        myRecyListCardsCM.add(new Post(ob.getObjectId(), ob.get("idCreador").toString(), ob.getString("nombreRutina"), Float.parseFloat(ob.getString("duracion")),
                                ob.getString("comment"), ob.getParseFile("fotfile").getUrl(), ob.get("likeId").toString(), ob.getCreatedAt()));
                        myAdapterCM.notifyItemInserted(myRecyListCardsCM.size() - 1);
                    }
                    addListinerScroll();
                }else{
                    Toast.makeText(getContext(), "Error al cargar datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void loadMore(){
        myRecyListCardsCM.add(null);
        myAdapterCM.notifyItemInserted(myRecyListCardsCM.size() - 1);

        ParseQuery<ParseObject> myPosts = new ParseQuery(TableName);
        myPosts.orderByDescending("_created_at").setLimit(SIZE_OF_LOAD).setSkip(myRecyListCardsCM.size()-1);
        myPosts.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    myRecyListCardsCM.remove(myRecyListCardsCM.size()-1);
                    myAdapterCM.notifyItemRemoved(myRecyListCardsCM.size());

                    for (ParseObject ob: objects){
                        myRecyListCardsCM.add(new Post(ob.getObjectId(),ob.get("idCreador").toString(),ob.getString("nombreRutina"),Float.parseFloat( ob.getString("duracion")),
                                ob.getString("comment"), ob.getParseFile("fotfile").getUrl(), ob.get("likeId").toString(), ob.getCreatedAt()));
                        myAdapterCM.notifyItemInserted(myRecyListCardsCM.size()-1);
                    }
                }else{
                    Toast.makeText(getContext(), "Error al cargar datos", Toast.LENGTH_SHORT).show();
                }
                isLoading = false;
            }
        });
    }

    public void addListinerScroll(){
        myRecyclerViewCM.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == myRecyListCardsCM.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }
}
