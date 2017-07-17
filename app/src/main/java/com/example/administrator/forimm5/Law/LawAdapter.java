package com.example.administrator.forimm5.Law;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.forimm5.DB.LawChild;
import com.example.administrator.forimm5.DB.LawParent;
import com.example.administrator.forimm5.R;
import com.example.administrator.forimm5.Util.CustomInteraction;

import java.util.ArrayList;
import java.util.List;


/**
 * 노동법 법률 어댑터
 */
public class LawAdapter extends BaseExpandableListAdapter {

    // 사용할 데이터와 기존 데이터를 분리해서 저장
    List<LawParent> data = new ArrayList<>();
    List<LawParent> original = new ArrayList<>();


    public LawAdapter(List<LawParent> data) {
        this.data = data;
        this.original = data;
    }

    // 전체 부모 뷰 개수
    @Override
    public int getGroupCount() {
        return data.size();
    }

    // 각 부모 뷰에 할당된 자식 뷰 개수
    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getData().size();
    }

    // 현재 부모 뷰
    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    // 현재 자식 뷰
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getData().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null) {
           convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_law_parent, parent, false);
        }
        // 부모뷰 제목 설정
        TextView parentTitle = (TextView) convertView.findViewById(R.id.lawParentTitle);
        parentTitle.setText(data.get(groupPosition).getChapter());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // 애니메이션 처리할 떄 null 값 처리해주고 재활용하기 때문에 문제 생김. 대부분 뷰 관련 문제는 재사용성 때문 인 듯 하다
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_law_child, parent, false);
        // 자식뷰 제목 설정
        TextView title = (TextView) convertView.findViewById(R.id.lawChildTitle);
        title.setText(data.get(groupPosition).getData().get(childPosition).getTitle());
        // 자식뷰 내용 설정
        final TextView content = (TextView) convertView.findViewById(R.id.lawContent);
        content.setText(data.get(groupPosition).getData().get(childPosition).getContent());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(content.getVisibility()== View.GONE){
                    content.setVisibility(View.VISIBLE);
                } else {
                    content.setVisibility(View.GONE);
                }
            }
        });
        // + 버튼 설정
        ImageView start = (ImageView) convertView.findViewById(R.id.lawChildStartInter);
        final CustomInteraction interaction = (CustomInteraction) convertView.findViewById(R.id.lawChildInteraction);
        ImageView cancelInteraction = (ImageView) interaction.findViewById(R.id.cancelInteraction);
        final Animation slide_in = AnimationUtils.loadAnimation(parent.getContext(), R.anim.slide_in_left);
        final Animation slide_out = AnimationUtils.loadAnimation(parent.getContext(), R.anim.slide_out_left);
        interaction.setAnimation(slide_in);
        interaction.setAnimation(slide_out);

        slide_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                interaction.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(interaction.getVisibility() == View.GONE){
                    interaction.setVisibility(View.VISIBLE);
                    interaction.startAnimation(slide_in);
                }
            }
        });

        cancelInteraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(interaction.getVisibility() == View.VISIBLE) {
                    interaction.startAnimation(slide_out);
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public void filter(String newText){

        data = original;

        if(newText.length() == 0){
            return;
        } else {

            List<LawParent> newParentList = new ArrayList<>();

            for(LawParent parent : data){
                List<LawChild> newChildList = new ArrayList<>();
                for(LawChild child : parent.getData()){
                    if(child.getTitle().contains(newText) || child.getContent().contains(newText)){
                        newChildList.add(child);
                    }
                }
                if(newChildList.size() > 0){
                    // 새로운 parent 를 넣어줘야지 위에 있는 parent 전체를 넣어버리면 어떡하냐
                    LawParent newParent = new LawParent(parent.getChapter(), newChildList);
                    newParentList.add(newParent);
                }
            }
            data = newParentList;
        }
        notifyDataSetChanged();
    }


}
