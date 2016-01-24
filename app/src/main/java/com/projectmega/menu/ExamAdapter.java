package com.projectmega.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Justin on 1/13/2016.
 */
public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {

    private Context context;

    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
                                                // IF the view under inflation and population is header or Item
    private static final int TYPE_ITEM = 1;

    private int subjectLogo = R.drawable.icon;
    private String slash = "/";
    private String percentSign = "%";
    private String scoreName = "Score:";
    private String nodata = "Not Available";
    private String nodata1 = "NA";

    List<ExamsList> examsLists;

    //Constructor of this class
    public ExamAdapter(List<ExamsList> examsLists, Context context){
        super();
        //Getting all exams
        this.examsLists = examsLists;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.exam_row, parent, false);
            ViewHolder vhItem = new ViewHolder(v,viewType);
            return  vhItem;
        }
        else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.examheader,parent,false); //Inflating the layout

            ViewHolder vhHeader = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view
            return vhHeader; //returning the object created
        }

        return  null;
    }

    @Override
    public void onBindViewHolder(ExamAdapter.ViewHolder holder, int position) {
        if(holder.Holderid ==1) {                              // as the list view is going to be called after the header view so we decrement the
                                                                // position by 1 and pass it to the holder while setting the text and image
            ExamsList examsList = examsLists.get(position-1);

            String test1 = examsList.getExamName();
            String test2 = examsList.getExamScore();
            String test3 = examsList.getExamPercent();
            String test4 = examsList.getExamItems();

            if (test1.equals("")){
                holder.textViewExam.setText(nodata);
                holder.textViewScoreName.setText(scoreName);
                holder.textViewScore.setText(nodata);
                holder.textViewSlash.setText("");
                holder.textViewItems.setText("");
                holder.textViewPercent.setText("");
            }
            else if (test2.equals("null")){
                holder.textViewExam.setText(examsList.getExamName());
                holder.textViewScoreName.setText(scoreName);
                holder.textViewScore.setText(nodata);
                holder.textViewSlash.setText("");
                holder.textViewItems.setText("");
                holder.textViewPercent.setText(nodata);
            }
            else if (test4.equals("null") || test4.equals("0")){
                holder.textViewExam.setText(examsList.getExamName());
                holder.textViewScoreName.setText(scoreName);
                holder.textViewScore.setText(nodata);
                holder.textViewSlash.setText("");
                holder.textViewItems.setText("");
                holder.textViewPercent.setText(nodata);
            }
            else if (test3 == "null"){
                holder.textViewExam.setText(examsList.getExamName());
                holder.textViewScoreName.setText(scoreName);
                holder.textViewScore.setText(nodata);
                holder.textViewSlash.setText("");
                holder.textViewItems.setText("");
                holder.textViewPercent.setText(nodata);
            }
            else {
                holder.textViewExam.setText(examsList.getExamName());
                holder.textViewScoreName.setText(scoreName);
                holder.textViewScore.setText(examsList.getExamScore());
                holder.textViewSlash.setText(slash);
                holder.textViewItems.setText(examsList.getExamItems());
                String percent = examsList.getExamPercent() + percentSign;
                holder.textViewPercent.setText(percent);
                //holder.textViewPercentSign.setText(percentSign);
            }


        }
        else{
            ExamsList examsList = examsLists.get(position);
            holder.imageView.setImageResource(subjectLogo);
            holder.subjectTitle.setText(examsList.getSubjectTitle());
        }
    }

    @Override
    public int getItemCount() {

        return examsLists.size();
    }

    // Witht the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public int Holderid;

        //Views
        public TextView textViewExam;
        public TextView textViewScoreName;
        public TextView textViewScore;
        public TextView textViewSlash;
        public TextView textViewItems;
        public TextView textViewPercent;
        public CircleImageView imageView;
        public TextView subjectTitle;

        //Initializing Views
        public ViewHolder(View itemView, int ViewType) {
            super(itemView);

            if(ViewType == TYPE_ITEM) {
                textViewExam = (TextView) itemView.findViewById(R.id.textViewExam);
                textViewScoreName = (TextView) itemView.findViewById(R.id.textViewScoreName);
                textViewScore = (TextView) itemView.findViewById(R.id.textViewScore);
                textViewSlash = (TextView) itemView.findViewById(R.id.textViewSlash);
                textViewItems = (TextView) itemView.findViewById(R.id.textViewItems);
                textViewPercent = (TextView) itemView.findViewById(R.id.textViewPercent);
                Holderid = 1;
            }
            else {
                imageView = (CircleImageView) itemView.findViewById(R.id.subjectLogo);
                subjectTitle = (TextView) itemView.findViewById(R.id.subjectTitle);
                Holderid = 0;
            }
        }
    }
}
