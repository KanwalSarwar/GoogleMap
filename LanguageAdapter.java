package com.example.usamanaseer.googlemap;


import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.usamanaseer.googlemap.MainActivity.code_param;
import static com.example.usamanaseer.googlemap.MainActivity.code_param2;

//import com.mayank.uddishverma.currencyconverter.utils.Globals;


public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {

    List<String> languageCode = new ArrayList<>();
    List<String> languageNames = new ArrayList<>();

    List<String> copylanguageCode = new ArrayList<>();
    List<String> copylanguageNames = new ArrayList<>();

    Context context;


    public static final String TAG = "LanguageAdapter";


    public LanguageAdapter(Context context,List<String> languageNames, List<String> languageCode ) {
        this.context=context;
        this.languageNames = languageNames;
        this.languageCode = languageCode;
        this.copylanguageNames = languageNames;
        this.copylanguageCode = languageCode;
    }


    public void filter(String s){
        copylanguageNames=new ArrayList<>();
        copylanguageCode=new ArrayList<>();
        if(s!=null && s.length()>1){
            for(int i=0;i<languageNames.size();i++){
                if(languageNames.get(i).toLowerCase().contains(s.toLowerCase())){
                    copylanguageCode.add(languageCode.get(i));
                    copylanguageNames.add(languageNames.get(i));
                }
            }

        }else {
            copylanguageNames=languageNames;
            copylanguageCode=languageCode;
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.language_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(this.copylanguageCode.get(position)!=null && this.copylanguageNames.get(position)!=null) {
            if (this.copylanguageCode.get(position) != null) {//(Globals.getCountryFlag(this.copylanguageCode.get(position)) != null) {
                if (this.copylanguageCode.get(position).length() > 1) {//(Globals.getCountryFlag(this.copylanguageCode.get(position)).length() > 1) {
                    //holder.flag.setText(this.copylanguageCode.get(position));//(Globals.getCountryFlag(this.copylanguageCode.get(position)));
                    holder.currency.setText(this.copylanguageNames.get(position));
                }else {
                    //holder.flag.setVisibility(View.GONE);
                    holder.currency.setVisibility(View.GONE);
                }
            }
        }

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p=0;
                if(MainActivity.flag==1){
                    if(holder.currency.getText().toString().toLowerCase().equals(MainActivity.TextTo.getText().toString().toLowerCase())){
                        p=1;
                    }else {
                        MainActivity.TextFrom.setText(holder.currency.getText().toString());
                        code_param2=copylanguageCode.get(position);
                    }
                }else if(MainActivity.flag==2){
                    if(holder.currency.getText().toString().toLowerCase().equals(MainActivity.TextFrom.getText().toString().toLowerCase())){
                        p=1;
                    }else {
                        MainActivity.TextTo.setText(holder.currency.getText().toString());
                        code_param=copylanguageCode.get(position);
                    }
                }
                if(p==0){
                    MainActivity.behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    MainActivity.behavior.setPeekHeight(0);
                    /*MainActivity.currency_to.setText("");
                    MainActivity.currency_from.setText("");*/
                    MainActivity.search.setText("");
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(holder.linear.getWindowToken(), 0);
                    com.example.usamanaseer.googlemap.utils.Prefs.setPrefs("language_from",MainActivity.TextFrom.getText().toString(),context);
                    com.example.usamanaseer.googlemap.utils.Prefs.setPrefs("language_to",MainActivity.TextTo.getText().toString(),context);
                }else {
                    Toast.makeText(context,"Select same currencies on both sides",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return copylanguageCode.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView flag, currency;
        public LinearLayout linear;

        public ViewHolder(View itemView) {
            super(itemView);
            //flag = (TextView) itemView.findViewById(R.id.flag);
            currency = (TextView) itemView.findViewById(R.id.language);
            linear=(LinearLayout)itemView.findViewById(R.id.languagelinear);
        }

    }


}

