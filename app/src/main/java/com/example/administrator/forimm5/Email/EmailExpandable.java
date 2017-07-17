package com.example.administrator.forimm5.Email;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.administrator.forimm5.DB.Center;
import com.example.administrator.forimm5.R;

import java.util.List;
import java.util.Map;


public class EmailExpandable extends BaseExpandableListAdapter {

    List<String> chapters;
    Map<String, List<Center>> contents;
    EmailFragment fragment;

    public EmailExpandable(List<String> chapters, Map<String, List<Center>> contents, EmailFragment fragment) {
        this.chapters = chapters;
        this.contents = contents;
        this.fragment = fragment;
    }

    @Override
    public int getGroupCount() {
        return chapters.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return contents.get(chapters.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return chapters.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return contents.get(chapters.get(groupPosition)).get(childPosition);
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_email_parent, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.emailParent);
        title.setText(chapters.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_email_child, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.emailCenterName);
        TextView email = (TextView) convertView.findViewById(R.id.emailCenterMail);
        name.setText(contents.get(chapters.get(groupPosition)).get(childPosition).getName());
        email.setText(contents.get(chapters.get(groupPosition)).get(childPosition).getEmail());

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.setEmail(contents.get(chapters.get(groupPosition)).get(childPosition).getEmail());
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
