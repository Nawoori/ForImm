package com.example.administrator.forimm5.Map;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.forimm5.DB.Center;
import com.example.administrator.forimm5.R;

import java.util.List;


public class MapAdapter extends RecyclerView.Adapter<MapAdapter.Holder> {

    List<List<Center>> data;        // 각 쿼리된 지역 배열을 다시 배열로 갖는 리스트를 리턴한다.
    MapFragment fragment;

    public MapAdapter(MapFragment fragment, List<List<Center>> data) {
        this.data = data;
        this.fragment = fragment;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_map, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        int count =  data.get(position).size();
        String name = data.get(position).get(0).getRegion();
        int resId = data.get(position).get(0).getResId();
        int selectedResId = data.get(position).get(0).getSelectedResId();

        holder.count.setText(count+"");
        holder.name.setText(name);
        holder.region.setImageResource(resId);
        holder.position = position;
        holder.selectedResId = selectedResId;
        holder.curregion = name;

    }

    @Override
    public int getItemCount() {

        return data.size();
    }


    public class Holder extends RecyclerView.ViewHolder {

        TextView count, name;
        ImageView region;
        int position, selectedResId;
        String curregion;

        public Holder(final View itemView) {
            super(itemView);

            count = (TextView) itemView.findViewById(R.id.centerCount);
            name = (TextView) itemView.findViewById(R.id.centerRegion);
            region = (ImageView) itemView.findViewById(R.id.centerRegionImg);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // todo 이게 클릭할 때마다 계속 마커 생성되고 페이저 생성되면 안 됨.
                    fragment.setLayoutVisibility();
                    fragment.moveLocal(position);
                    fragment.setMarkers(position);
                    fragment.setCenterPager(position);
                    fragment.setCurRegion(curregion);
//                    for(int i=0; i< data.size(); i++){
//
//                        region.setImageResource(data.get(i).get(0).getResId());
//                    }
//                    region.setImageResource(selectedResId);

                }
            });
        }

    }
}
