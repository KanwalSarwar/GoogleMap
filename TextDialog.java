package com.example.usamanaseer.googlemap;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.List;

public class TextDialog extends DialogFragment {


    private FragmentTabHost mTabHost;
    private ViewPager viewPager;
    private static VotersPagerAdapter adapter;
    private static int src;


    public TextDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static TextDialog newInstance(String title, String title2) {
        TextDialog frag = new TextDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("title2", title2);
        //src=title;

        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_dialog, container);

        //getDialog().setTitle(getArguments().getString("title"));
        mTabHost = (FragmentTabHost) view.findViewById(R.id.tabs);
        mTabHost.setup(getActivity(), getChildFragmentManager());
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("English"), Fragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("French"), Fragment.class, null);


        adapter = new VotersPagerAdapter(getChildFragmentManager(), getArguments());
        adapter.setTitles(new String[]{"English", "French"});

        viewPager = (ViewPager)view.findViewById(R.id.pager1);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                mTabHost.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int i = mTabHost.getCurrentTab();
                viewPager.setCurrentItem(i);
                src = i;
            }
        });

        return view;
    }


    public class VotersPagerAdapter extends FragmentPagerAdapter {

        Bundle bundle;
        String[] titles;

        public VotersPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public VotersPagerAdapter(FragmentManager fm, Bundle bundle) {
            super(fm);
            this.bundle = bundle;
        }

        @Override
        public Fragment getItem(int num) {
            Fragment fragment = new VotersListFragment();
            Bundle args = new Bundle();
            //args.putSerializable("voters",bundle.getSerializable( num == 0 ? "title" : "title2"));
            //args.putString("translate",args.getString("title"));
            fragment.setArguments(getArguments());
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        public void setTitles(String[] titles) {
            this.titles = titles;
        }
    }

    public static class VotersListFragment extends Fragment {

        List<String> voters;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.list_fragment, container, false);
            int selectedTabPosition = getArguments().getInt("tab1") ;//.getInt(PageFragment.ARG_DAY_INDEX, 0);

            String name = getFragmentManager().findFragmentByTag("tab1").getTag();

            if(src == 0){ //container.getChildAt(0).equals("tab1")
                TextView ttext=(TextView)view.findViewById(R.id.ttext);
                ttext.setText(getArguments().getString("title2"));
            }else{
                TextView ttext=(TextView)view.findViewById(R.id.ttext);
                ttext.setText(getArguments().getString("title"));
            }
            //Bundle args = new Bundle();
            //args.get("title");
            //args.putString("title2", title2);
            //frag.setArguments(args);
            return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {

            super.onActivityCreated(savedInstanceState);
            //voters = (ArrayList) getArguments().getSerializable("voters");

            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, voters);
            setListAdapter(adapter);*/

            /*getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), ProfileActivity_.class);
                    String login = voters.get(i);
                    intent.putExtra("login", Utils.encodeString(login.substring(0, login.indexOf("(")).trim()));
                    startActivity(intent);
                }
            });*/

        }

    }

}