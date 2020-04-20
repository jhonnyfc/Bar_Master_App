package com.example.barmaster.ui.comunidad.creaPost;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.barmaster.R;
import com.example.barmaster.ui.comunidad.ComunidadCMFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PostCreator extends Fragment {
    final private int CODE_REQUEST_PERMISION = 14;
    final private String TableName = "Posts";
    private int FOTO_ASIGNADA;

    private ImageView myImagen;
    private EditText myTextNameRoutine;
    private EditText myMinutsTrain;
    private EditText myTextComment;
    private String myIdLikes;
    private FloatingActionButton myBotonFlo;
    private FloatingActionButton myGobackFlo;
    private Uri mifoto;

    private PostCreatorViewModel mViewModel;

    public static PostCreator newInstance() {
        return new PostCreator();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FOTO_ASIGNADA = 0;
        View root = inflater.inflate(R.layout.post_creator_fragment, container, false);

        myImagen = root.findViewById(R.id.foto_postcreator);
        myTextNameRoutine = root.findViewById(R.id.routinename_postcreator);
        myMinutsTrain = root.findViewById(R.id.duracion_postcreator);
        myTextComment = root.findViewById(R.id.comment_postcreator);

        myImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFoto();
            }
        });

        myBotonFlo = root.findViewById(R.id.fbotadd_post);
        myBotonFlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comporbar si todos los datos estan rellenados y crear el objeto y subrirlo
                if (FOTO_ASIGNADA == 0 || myTextNameRoutine.getText().toString().length() == 0 ||
                        myMinutsTrain.getText().toString().length() == 0 || myTextComment.getText().toString().length() == 0) {
                    if (FOTO_ASIGNADA == 0)
                        Toast.makeText(getContext(), "Foto No Seleccionada", Toast.LENGTH_SHORT).show();
                    if (myTextNameRoutine.getText().toString().length() == 0)
                        Toast.makeText(getContext(), "Falta Nombre de la rutina", Toast.LENGTH_SHORT).show();
                    if (myMinutsTrain.getText().toString().length() == 0)
                        Toast.makeText(getContext(), "Falta mins Duracion", Toast.LENGTH_SHORT).show();
                    if (myTextComment.getText().toString().length() == 0)
                        Toast.makeText(getContext(), "Introduzca una Descripcion", Toast.LENGTH_SHORT).show();
                } else {
                    guardaDatos(TableName);
                    goBack();
                }
            }
        });

        myGobackFlo = root.findViewById(R.id.goback_post);
        myGobackFlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        return root;
    }

    public void goBack(){
        Fragment someFragment = new ComunidadCMFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, someFragment ); // give your fragment container id in first parameter
        ((FragmentTransaction) transaction).addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void guardaDatos(final String TableName){
        final ParseFile myFotoPost = new ParseFile("postfoto.jpg",getByteFromUri(mifoto));
        myFotoPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    ParseUser myUser = ParseUser.getCurrentUser();
                    ParseObject myNewPost = new ParseObject(TableName);
                    myNewPost.put("idCreador",myUser.getUsername());
                    myNewPost.put("nombreRutina",myTextNameRoutine.getText().toString());
                    myNewPost.put("duracion",myMinutsTrain.getText().toString());
                    myNewPost.put("comment",myTextComment.getText().toString());
                    myNewPost.put("fotfile",myFotoPost);// guardar el photo file
                    myNewPost.put("likeId","0");// la primer vez se inicializa a 0 y luego se cambiara por el id del objeto

                    myNewPost.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null)
                                Toast.makeText(getContext(), "vUamOoS", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getContext(), "Error al subir el post", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Error al subir foto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void changeFoto(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                String[] permisos = {Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permisos,CODE_REQUEST_PERMISION);
            }else{
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(4,3)
                        .start(getContext(),PostCreator.this);
            }
        }else{
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(4,3)
                    .start(getContext(),PostCreator.this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == getActivity().RESULT_OK){
                FOTO_ASIGNADA = 1;
                Uri myNewFotoUri = result.getUri();

                myImagen.setImageURI(myNewFotoUri);
                mifoto = myNewFotoUri;
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Toast.makeText(getContext(), "Error al cargar foto", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error al cargar foto", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_REQUEST_PERMISION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(4,3)
                            .start(getContext(),PostCreator.this);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getContext(), "No se ha concedido los Permisos", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public byte[] getByteFromUri(Uri myuri){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), myuri).compress(Bitmap.CompressFormat.PNG, 100, stream);// can use something 70 in case u want to compress the image
            return stream.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }
}