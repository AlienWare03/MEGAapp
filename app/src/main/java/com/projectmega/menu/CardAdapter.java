package com.projectmega.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Justin on 1/8/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context context;

    private String nodata = "Not Available";

    //List to store Subjects
    List<SubjectList> subjectLists;

    //Constructor of this class
    public CardAdapter(List<SubjectList> subjectLists, Context context){
        super();
        //Getting all subjects
        this.subjectLists = subjectLists;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder holder, int position) {

        //Getting the particular item from the list
        SubjectList subjectList = subjectLists.get(position);

        String test = subjectList.getSubjectName();
        if (test =="null"){
            holder.textViewSubject.setText(nodata);
        }
        else {
            //Showing data on the views
            holder.textViewSubject.setText(subjectList.getSubjectName());
        }

    /*
        holder.textViewSubject.setOnClickListener(clickListener);
        holder.textViewSubject.setTag(holder);

        View.OnClickListener clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ViewHolder holder = (ViewHolder) v.getTag();
                int position = holder.getAdapterPosition();

                SubjectList subjectList = subjectLists.get(position);
                Toast.makeText(this,subjectList.getSubjectName(),Toast.LENGTH_SHORT).show();

            }
        };*/
    }
    @Override
    public int getItemCount() {
        return subjectLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        //Views
        public TextView textViewSubject;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            textViewSubject = (TextView) itemView.findViewById(R.id.textViewSubject);
        }
    }



}
