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
    private static final String CURRENT_FRAGMENT_TAG = "CurrentFragmentTag";
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    protected abstract Class<? extends Fragment> getDefaultFragment();
    protected Fragment currentFragment;
    protected String currentFragmentTag;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(CURRENT_FRAGMENT_TAG,currentFragmentTag);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        if (savedInstanceState==null) {
            navigate(getDefaultFragment(), false, false);
        } else {
            currentFragmentTag = savedInstanceState.getString(CURRENT_FRAGMENT_TAG);
            currentFragment = getFragmentByTag(currentFragmentTag);
        }
    }

    private Fragment getFragmentByTag(String fragmentTag){
        FragmentManager fm = getSupportFragmentManager();
        return fm.findFragmentByTag(fragmentTag);
    }

    /**
     * Navigate to another fragment with default parameters
     * bundle = null
     * @param fragmentClass
     * @param isRemoval
     */
    public void navigate(Class<? extends Fragment> fragmentClass,boolean isRemoval,boolean inBackStack){
        navigate(fragmentClass,null,isRemoval,inBackStack);
    }

    /**
     * Navigate to another fragment with default parameters
     * bundle = null
     * isRemoval = false
     * @param fragmentClass
     */
    public void navigate(Class<? extends Fragment> fragmentClass){
        navigate(fragmentClass,null,false,true);
    }

    /**
     * Navigate to another fragment with class object and bundle
     * @param fragmentClass class object
     * @param bundle bundle with arguments, can be null
     * @param isRemoval true - detach and destroy current fragment. false - detach only, without destroying current fragment
     * @param inBackStack true - add fragment transaction in back stack
     */
    public void navigate(Class<? extends Fragment> fragmentClass,Bundle bundle,boolean isRemoval,boolean inBackStack){
        try {
            FragmentManager fm = getSupportFragmentManager();
            Fragment currentFragment = fm.findFragmentById(R.id.fragment_container);
            Fragment fragment = fm.findFragmentByTag(fragmentClass.getCanonicalName());
            if (fragment == null) {
                fragment = fragmentClass.getConstructor().newInstance();
                if (bundle!=null){
                    fragment.setArguments(bundle);
                }
            } else {
                (fragment).setArguments(bundle);
            }
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            if (currentFragment!=null){
                fragmentTransaction.detach(currentFragment);
                if (isRemoval){
                    fragmentTransaction.remove(currentFragment);
                }
            }
            fragmentTransaction.add(R.id.fragment_container,fragment,fragmentClass.getCanonicalName());
            fragmentTransaction.attach(fragment);
            if (inBackStack){
                fragmentTransaction.addToBackStack(fragmentClass.getCanonicalName());
            }
            fragmentTransaction.commit();
            this.currentFragment = fragment;
            currentFragmentTag = fragmentClass.getCanonicalName();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException("Class fragment does not have a constructor");
        }
    }

    public void popBackStack(){
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
    }
}
