package com.example.administrator.forimm5.Main;

import android.content.Context;
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

/**
 * Created by Administrator on 2017-08-10.
 */

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.Holder> {

    List<Language> data;
    Context context;

    public LanguageAdapter(Context context) {
        this.data = LanguageLab.getInstance().getData();
        this.context = context;
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
        if(data.get(position).isSelected()){
            holder.flag.setImageResource(data.get(position).getSelectedImgId());
        }
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
                    for(Language language : data){
                        language.setSelected(false);
                    }
                    data.get(position).setSelected(true);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
