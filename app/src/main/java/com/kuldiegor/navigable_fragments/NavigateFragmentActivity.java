package com.kuldiegor.navigable_fragments;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.InvocationTargetException;

/**
 * Author kuldiegor github https://github.com/kuldiegor/NavigableFragments
 */
public abstract class NavigateFragmentActivity extends AppCompatActivity {
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    protected abstract Class<? extends Fragment> getDefaultFragment();
    protected Fragment currentFragment;
    protected String currentFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        navigate(getDefaultFragment(),null,false);
    }

    /**
     * Navigate to another fragment with default parameters
     * bundle = null
     * @param fragmentClass
     * @param isRemoval
     */
    public void navigate(Class<? extends Fragment> fragmentClass,boolean isRemoval){
        navigate(fragmentClass,null,isRemoval);
    }

    /**
     * Navigate to another fragment with default parameters
     * bundle = null
     * isRemoval = false
     * @param fragmentClass
     */
    public void navigate(Class<? extends Fragment> fragmentClass){
        navigate(fragmentClass,null,false);
    }

    /**
     * Navigate to another fragment with class object and bundle
     * @param fragmentClass class object
     * @param bundle bundle with arguments, can be null
     * @param isRemoval true - detach and destroy current fragment. false - detach only, without destroying current fragment
     */
    public void navigate(Class<? extends Fragment> fragmentClass,Bundle bundle,boolean isRemoval){
        try {
            FragmentManager fm = getSupportFragmentManager();
            Fragment currentFragment = fm.findFragmentById(R.id.fragment_container);
            if (currentFragment!=null){
                FragmentTransaction fragmentTransaction = fm.beginTransaction()
                        .detach(currentFragment);
                if (isRemoval){
                    fragmentTransaction.remove(currentFragment);
                }
                fragmentTransaction.commit();
            }
            Fragment fragment = fm.findFragmentByTag(fragmentClass.getCanonicalName());
            if (fragment == null) {
                fragment = fragmentClass.getConstructor().newInstance();
                if (bundle!=null){
                    fragment.setArguments(bundle);
                }
                fm.beginTransaction()
                        .add(R.id.fragment_container,fragment,fragmentClass.getCanonicalName())
                        .commit();
            } else {
                (fragment).setArguments(bundle);
                fm.beginTransaction()
                        .attach(fragment)
                        .commit();
            }
            this.currentFragment = fragment;
            currentFragmentTag = fragmentClass.getCanonicalName();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException("Class fragment does not have a constructor");
            //e.printStackTrace();
        }
    }
}
