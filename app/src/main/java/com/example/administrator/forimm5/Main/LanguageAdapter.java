package com.example.administrator.forimm5.Main;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.forimm5.DB.Language;
import com.example.administrator.forimm5.DB.LanguageLab;
import com.example.administrator.forimm5.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2017-08-10.
 */

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.Holder> {

    private static final String LANG_POSITION = "lang_string";

    List<Language> data;
    Context context;
    MainFragment fragment;
    SharedPreferences sp;

    public LanguageAdapter(Context context, MainFragment fragment) {
        this.data = LanguageLab.getInstance().getData();
        this.context = context;
        this.fragment = fragment;
        sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.flag.setImageResource(data.get(position).getResId());
        holder.name.setText(data.get(position).getName());
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/NotoSans-Bold.ttf");
        holder.name.setTypeface(typeface);
        holder.position = position;

        if(sp.getInt(LANG_POSITION, 0) == position){
            holder.flag.setImageResource(data.get(position).getSelectedImgId());
        }
//        todo 이거 왜 9번씩 뜨냐
//        for(Language language : data){
//            Log.e("확인", language.isSelected()+"");
//        }
//        if(data.get(position).isSelected()){
//            holder.flag.setImageResource(data.get(position).getSelectedImgId());
//        }
//
//        if(position == 0 && context.getResources().getConfiguration().locale == Locale.getDefault()){
//            holder.flag.setImageResource(data.get(position).getSelectedImgId());
//        } else if(position == 1 && context.getResources().getConfiguration().locale == Locale.US){
//            holder.flag.setImageResource(data.get(position).getSelectedImgId());
//        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView flag;
        TextView name;
        int position;

        public Holder(View itemView) {
            super(itemView);
            flag = (ImageView) itemView.findViewById(R.id.languageFlag);
            name = (TextView) itemView.findViewById(R.id.languageName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (position){
                        case 0:
                            setLang(Locale.getDefault());
                            break;
                        case 1:
                            setLang(Locale.US);
                            break;
                    }
                    saveSP(position);
                }
            });
        }

        private void setLang(Locale locale){
            Configuration config = new Configuration();
            config.locale = locale;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            fragment.refreshFragment();
        }

        private void saveSP(int position){
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(LANG_POSITION, position);
            editor.commit();
        }

//        public void setChecked(){
//            for(Language language : data){
//                language.setSelected(false);
//            }
//            data.get(position).setSelected(true);
//            notifyDataSetChanged();
//        }
    }
}
