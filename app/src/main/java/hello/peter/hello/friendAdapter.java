package hello.peter.hello;

import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Ishdavis on 1/27/2016.
 */
public class friendAdapter {
    private final ArrayList Data;

    public friendAdapter(Map<String, String> map) {
        Data = new ArrayList();
        Data.addAll(map.entrySet());
    }

    public int getCount() {
        return Data.size();
    }

    public long getItemId(int position) {
        return (long)position;
    }

    public Map.Entry<String, String> getItem(int position) {
        return (Map.Entry) Data.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendadder, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, String> item = getItem(position);

        return result;
    }
}
