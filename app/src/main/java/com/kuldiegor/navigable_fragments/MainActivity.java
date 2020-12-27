package com.kuldiegor.navigable_fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MainActivity extends NavigateFragmentActivity {

    @Override
    protected Class<? extends Fragment> getDefaultFragment() {
        return MainFragment.class;
    }
}