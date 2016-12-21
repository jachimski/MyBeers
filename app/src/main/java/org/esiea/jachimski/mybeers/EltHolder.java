package org.esiea.jachimski.mybeers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class EltHolder extends RecyclerView.ViewHolder {

    public TextView name;


    public EltHolder(View v) {
        super(v);
        name = (TextView)itemView.findViewById(R.id.listText);
    }

}
