package vintr.com.forecast.Adapters;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import vintr.com.forecast.Fragments.CityFragment;


public class DynamicFragmentAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private ArrayList<String> contents;

    public DynamicFragmentAdapter(FragmentManager fm, ArrayList<String> contents) {
        super(fm);
        this.mNumOfTabs = contents.size();
        this.contents = contents;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putString("city", contents.get(position));
        b.putInt("position", position);
        Fragment frag = CityFragment.newInstance();
        frag.setArguments(b);
        return frag;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}