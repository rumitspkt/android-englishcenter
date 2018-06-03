package com.example.mobileda.englishcenter.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mobileda.englishcenter.fragment.MarksFragment;
import com.example.mobileda.englishcenter.fragment.NewmarkFragment;


public class PaperMarkAdapter  extends FragmentStatePagerAdapter {

    public PaperMarkAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new MarksFragment();
                break;
            case 1:
                frag=new NewmarkFragment();
                break;
        }
        return frag;
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title="Xem điểm";
                break;
            case 1:
                title="Nhập điểm";
                break;
        }

        return title;
    }

}
