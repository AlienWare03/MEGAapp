package com.projectmega.menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Justin on 1/17/2016.
 */
public class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.ViewHolder> {

    private Context context;

    private String gradeLabel = "Final Grade:";

    private String nodata = "N/A";


    public SharedPreferences sharedPreferences;

    List<GradeList> gradeLists;

    public GradesAdapter(List<GradeList> gradeLists,Context context){
        this.gradeLists = gradeLists;
        this.context = context;
    }


    @Override
    public GradesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grade_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GradesAdapter.ViewHolder holder, int position) {

        GradeList gradeList = gradeLists.get(position);
        holder.finalGradeLabel.setText(gradeLabel);
        String test = gradeList.getFinalGrade();
        if (test == "null"){
            holder.finalGrade.setText(nodata);
        }
        else {
            holder.finalGrade.setText(gradeList.getFinalGrade());
        }




    }

    @Override
    public int getItemCount() {
        return gradeLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView finalGrade;
        public TextView finalGradeLabel;

        public ViewHolder(View itemView) {
            super(itemView);

            finalGrade = (TextView) itemView.findViewById(R.id.finalGrade);
            finalGradeLabel = (TextView) itemView.findViewById(R.id.finalGradeLabel);
        }
    }

}
