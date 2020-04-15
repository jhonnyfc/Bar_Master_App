package com.example.barmaster.ui.rutinasGrupos.rutinas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.barmaster.R;

public class RutinasRU extends Fragment {

    private RutinasRUViewModel mViewModel;

    public static RutinasRU newInstance() {
        return new RutinasRU();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rutinas_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RutinasRUViewModel.class);
        // TODO: Use the ViewModel
    }

}
