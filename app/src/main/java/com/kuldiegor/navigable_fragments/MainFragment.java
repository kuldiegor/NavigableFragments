package com.kuldiegor.navigable_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuldiegor.navigable_fragments.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {
    //Блок констант


    //Блок переменных
    private FragmentMainBinding binding;


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //Object object = getArguments().getSerializable(ARG_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigateFragmentActivity navigateFragmentActivity = (NavigateFragmentActivity) getActivity();
                navigateFragmentActivity.navigate(SecondFragment.class);
            }
        });
        return binding.getRoot();
    }
}