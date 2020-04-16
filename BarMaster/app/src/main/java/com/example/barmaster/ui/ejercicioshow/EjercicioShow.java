package com.example.barmaster.ui.ejercicioshow;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barmaster.R;

public class EjercicioShow extends Fragment {

    private EjercicioShowViewModel mViewModel;

    public static EjercicioShow newInstance() {
        return new EjercicioShow();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ejercicio_show_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EjercicioShowViewModel.class);
        // TODO: Use the ViewModel
    }

}
