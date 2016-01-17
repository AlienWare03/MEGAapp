package com.projectmega.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Justin on 1/17/2016.
 */
public class GradeSubjectAdapter extends RecyclerView.Adapter<GradeSubjectAdapter.ViewHolder> {

    private Context context;

    //List to store Subjects
    List<GradeSubjectList> gradeSubjectLists;

    //Constructor of this class
    public GradeSubjectAdapter(List<GradeSubjectList> gradeSubjectLists, Context context){
        super();
        //Getting all subjects
        this.gradeSubjectLists = gradeSubjectLists;
        this.context = context;
    }


    @Override
    public GradeSubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gradesubject_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GradeSubjectAdapter.ViewHolder holder, int position) {
        //Getting the particular item from the list
        GradeSubjectList gradeSubjectList = gradeSubjectLists.get(position);

        //Showing data on the views
        holder.textViewSubject.setText(gradeSubjectList.getSubjectName());
    }

    @Override
    public int getItemCount() {
        return gradeSubjectLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        //Views
        public TextView textViewSubject;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            textViewSubject = (TextView) itemView.findViewById(R.id.textViewGradeSubject);
        }
    }
}
