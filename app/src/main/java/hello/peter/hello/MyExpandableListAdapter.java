package hello.peter.hello;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ishdavis on 1/17/2016.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private final SparseArray<Group> groups;
    public LayoutInflater inflater;
    public Activity activity;
    public int [][] state;
    int groupSize, childSize;

    public MyExpandableListAdapter(Activity act, SparseArray<Group> groups, int groupNum, int childNum) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
        groupSize = groupNum;
        childSize = childNum;
        state = new int[groupNum][childNum];
    }

    /**
     * Returns map of all interests and whether they were chosen
     * @return
     */
    public Map<String, Integer> getInterests(){
        Map<String,Integer> map = new HashMap<String,Integer>();
        for(int i = 0; i < groupSize; i++){
            int size = getChildrenCount(i);
            for(int k = 0; k < size; k++){
                map.put((String)getChild(i, k),state[i][k]);
            }
        }
        return map;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String children = (String) getChild(groupPosition, childPosition);
        TextView text = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_details, null);
        }
        text = (TextView) convertView.findViewById(R.id.textView6);
        Drawable [] check = text.getCompoundDrawables();
        if(state[groupPosition][childPosition] == 1){//If option has been chosen
            Drawable current = check[0];
            //text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pictures, 0, 0, 0);
            text.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_check_mark_md, 0, 0, 0);
        }
        else {//If option hasn't been chosen
            //text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pictures,0,0,0);
            text.setText(children);
            text.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_checkmark, 0, 0, 0);
        }
        final View child = convertView;
        final int childPos = childPosition, groupPos = groupPosition;
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView texts = (TextView) child.findViewById(R.id.textView6);
                if(state[groupPos][childPos] == 1){//Deselect option that has been chosen
                    //currents.setAlpha(0);
                    state[groupPos][childPos] = 0;
                    //texts.setText(children);
                    texts.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_checkmark, 0, 0, 0);
                }
                else {//Select option that hasn't been chosen
                    //currents.setAlpha(0);
                    state[groupPos][childPos] = 1;
                    texts.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_check_mark_md,0,0,0);
                }
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_group, null);
        }
        Group group = (Group) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(group.string);
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
