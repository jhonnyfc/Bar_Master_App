package com.example.barmaster.ui.perfil;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;

import com.example.barmaster.Controlador_Activity;
import com.example.barmaster.R;
import com.example.barmaster.sharedData.MyAppDataControler;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditProfile extends Activity implements View.OnClickListener{
    private static final int SHOW_SUBACTIVITY = 1;
    private static final int RESULT_FROM_CHANGE_FOTO_OK = 3;
    private static final int RESULT_FROM_CHANGE_FOTO_NOOK = 4;

    private TextView nickInTx;

    private EditText nameInTx;
    private EditText faminameInTx;
    private EditText correoInTx;

    private Button confirmarBt;
    private Button goBackBt;
    private Button changPhBt;

    private MyAppDataControler myDataControler;
    private Bitmap mifoto;


    private ImageView perfilView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        perfilView = findViewById(R.id.mifotomodi);
        mifoto = getBitmapFromUser(ParseUser.getCurrentUser());
        perfilView.setImageBitmap(mifoto);

        //Textos
        myDataControler = new MyAppDataControler(this);

        nickInTx = findViewById(R.id.nicknamemodi);
        nickInTx.setText(myDataControler.getMyData().getNickName());

        nameInTx = findViewById(R.id.namemodi);
        nameInTx.setText(myDataControler.getMyData().getNombre());

        faminameInTx = findViewById(R.id.familynamemodi);
        faminameInTx.setText(myDataControler.getMyData().getApellido());

        correoInTx = findViewById(R.id.correomodi);
        correoInTx.setText(myDataControler.getMyData().getCorreo());


        //Botones
        confirmarBt = findViewById(R.id.confirmodi);
        goBackBt = findViewById(R.id.skipmodi);
        changPhBt = findViewById(R.id.changpictmodi);

        confirmarBt.setOnClickListener(this);
        goBackBt.setOnClickListener(this);
        changPhBt.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId() ) {
            case R.id.confirmodi:
                //Validacion de credenciales para modificacion de datos
                validacion();
                break;
            case R.id.skipmodi://Saltar log ventan e ir el inicio
                goBack();
                break;
            case R.id.changpictmodi:// Creac accion camiar foto
                changeFoto();
                break;
            default:
                break;
        }
    }

    public void changeFoto(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                String[] permisos = {Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permisos,14);
            }else{
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(EditProfile.this);
            }
        }else{
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(EditProfile.this);
        }
    }


    public void goBack(){
        startActivity(new Intent(getApplicationContext(), Controlador_Activity.class));
    }

    public Bitmap getBitmapFromUser(ParseUser userCc) {
        try {
            ParseFile mifile = userCc.getParseFile("foto");
            byte [] blob = mifile.getData();

            Bitmap  myBitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);

            System.out.println("lolok");
            return myBitmap;
        } catch (ParseException e) {
            // Log exception
            return null;
        }

    }

    public void validacion(){
        startActivityForResult(new Intent(this, ValidacionKey.class),SHOW_SUBACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK){
                Uri myNewFotoUri = result.getUri();
                perfilView.setImageURI(myNewFotoUri);
                mifoto = getBitMapFromUri(myNewFotoUri);
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Toast.makeText(getApplicationContext(), "Error al cargar foto", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error al cargar foto", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode != CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Bundle bundle = data.getExtras();
            String contraseñaValidacion = bundle.getString("contraseña");

            if (resultCode == resultCode) {
                if (contraseñaValidacion.equals(myDataControler.getMyData().getPassWord())) {
                    Toast.makeText(getApplicationContext(), "Contraseña correcta", Toast.LENGTH_SHORT).show();


                    //Aculaizar los datos
                    ParseFile photoFile = new ParseFile("perfilFoto.jpg", getByteFromBitMap(mifoto));

                    final ParseUser usuarioAcual = ParseUser.getCurrentUser();
                    usuarioAcual.setEmail(correoInTx.getText().toString());
                    usuarioAcual.put("name",nameInTx.getText().toString());
                    usuarioAcual.put("familyname",faminameInTx.getText().toString());
                    usuarioAcual.put("foto",photoFile);

                    usuarioAcual.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "Actualizacion de\ndatos correcta", Toast.LENGTH_SHORT).show();
                                //Acutalizacion de los datos si el usuario ha creado nueva cuenta
                                MyAppDataControler controler = new MyAppDataControler(getApplicationContext());
                                controler.saveMyDataByParse(usuarioAcual,"");

                            } else {
                                // Sign up didn't succeed. Look at the ParseException
                                // to figure out what went wrong
                                Toast.makeText(getApplicationContext(), "$: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Contrseña erronea", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public byte[] getByteFromBitMap(Bitmap bitmap){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);// can use something 70 in case u want to compress the image

        return stream.toByteArray();
    }

    public Bitmap getBitMapFromUri(Uri myuri){
        try {
            return MediaStore.Images.Media.getBitmap(this.getContentResolver(), myuri);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 14: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1,1)
                            .start(EditProfile.this);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "No se ha concedido los Permisos", Toast.LENGTH_SHORT).show();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
