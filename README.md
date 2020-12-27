# NavigableFragments
Library, useful to navigate in "single activity pattern"

# How to use
copy from src->main->res->layout `activity_fragment.xml` to your project  
copy from src->main->java->java->`com.kuldiegor.navigable_fragments.NavigateFragmentActivity`  

then extend you `Activity` from `NavigateFragmentActivity` (like in this project `MainActivity`)  
then override `getDefaultFragment()`. Home Fragment class.

# To navigate in fragment to another fragment
```
NavigateFragmentActivity navigateFragmentActivity = (NavigateFragmentActivity) getActivity();  
navigateFragmentActivity.navigate(SecondFragment.class);  
```

# To navigate in fragment to another fragment with data (arguments in bundle)
```
NavigateFragmentActivity navigateFragmentActivity = (NavigateFragmentActivity) getActivity();  
Bundle bundle = new Bundle();  
bundle.putString("Some key of data","Some data");  
navigateFragmentActivity.navigate(SecondFragment.class,bundle,false);  
```

# To navigate in fragment to another fragment with closing current fragment
```
NavigateFragmentActivity navigateFragmentActivity = (NavigateFragmentActivity) getActivity();  
navigateFragmentActivity.navigate(SecondFragment.class,true); 
```
