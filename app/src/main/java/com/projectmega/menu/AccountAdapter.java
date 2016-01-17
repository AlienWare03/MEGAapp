package com.projectmega.menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Justin on 1/16/2016.
 */
public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private Context context;


    //private String LABELS[]={"ID Number","First Name","Middle Name","Last Name","Age","Section"};
    private String userTag = "Hello ";
    private String exclamation = "!";
    private String studentnumberLabel = "ID Number";
    private String firstnameLabel= "First Name";
    private String middlenameLabel = "Middle Name";
    private String lastnameLabel = "Last Name";
    private String ageLabel = "Age";
    private String sectionLabel = "Section";
    private String emailLabel = "Email";

    public SharedPreferences sharedPreferences;

    List<AccountList> accountLists;

    //Constructor of this class
    public AccountAdapter(List<AccountList> accountLists, Context context){
        super();
        //Getting all accounts
        this.accountLists = accountLists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*if (viewType == TYPE_ITEM){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.account_row, parent, false);
            ViewHolder vhItem = new ViewHolder(v,viewType);
            return  vhItem;
        }
        else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.accountheader,parent,false); //Inflating the layout

            ViewHolder vhHeader = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view
            return vhHeader; //returning the object created
        }*/

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

        //return null;
    }

    @Override
    public void onBindViewHolder(AccountAdapter.ViewHolder holder, int position) {
        /*if(holder.Holderid ==1) {
            AccountList accountList = accountLists.get(position-1);

            holder.textViewLabel1.setText(studentnumberLabel);
            /*holder.textViewLabel2.setText(firstnameLabel);
            holder.textViewLabel3.setText(middlenameLabel);
            holder.textViewLabel4.setText(lastnameLabel);
            holder.textViewLabel5.setText(ageLabel);
            holder.textViewLabel6.setText(sectionLabel);
            holder.textViewLabel7.setText(emailLabel);

            holder.textViewStudentNum.setText(accountList.getID());
            /*holder.textViewFirst.setText(accountList.getFirst());
            holder.textViewMiddle.setText(accountList.getMiddle());
            holder.textViewLast.setText(accountList.getLast());
            holder.textViewAge.setText(accountList.getAge());
            holder.textViewSection.setText(accountList.getSection());
            holder.textViewEmail.setText(accountList.getEmailadd());
        }
        else {
            AccountList accountList = accountLists.get(position);
            holder.textViewUser.setText(accountList.getFirst());
        }*/


        AccountList accountList = accountLists.get(position);
            //holder.textViewUser.setText(userLabel);
            holder.textViewLabel1.setText(studentnumberLabel);
            holder.textViewLabel2.setText(firstnameLabel);
            holder.textViewLabel3.setText(middlenameLabel);
            holder.textViewLabel4.setText(lastnameLabel);
            holder.textViewLabel5.setText(ageLabel);
            holder.textViewLabel6.setText(sectionLabel);
            holder.textViewLabel7.setText(emailLabel);

            String userWelcome = userTag + accountList.getUser() + exclamation;

            holder.textViewUser.setText(userWelcome);
            holder.textViewStudentNum.setText(accountList.getID());
            holder.textViewFirst.setText(accountList.getFirst());
            holder.textViewMiddle.setText(accountList.getMiddle());
            holder.textViewLast.setText(accountList.getLast());
            holder.textViewAge.setText(accountList.getAge());
            holder.textViewSection.setText(accountList.getSection());
            holder.textViewEmail.setText(accountList.getEmailadd());

    }

    @Override
    public int getItemCount() {
        return accountLists.size();
    }

    // Witht the following method we check what type of view is being passed
    /*@Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }*/

    class ViewHolder extends RecyclerView.ViewHolder {

        //public int Holderid;

        //Views
        public TextView textViewUser;
        public TextView textViewLabel1;
        public TextView textViewLabel2;
        public TextView textViewLabel3;
        public TextView textViewLabel4;
        public TextView textViewLabel5;
        public TextView textViewLabel6;
        public TextView textViewLabel7;
        public TextView textViewStudentNum;
        public TextView textViewFirst;
        public TextView textViewMiddle;
        public TextView textViewLast;
        public TextView textViewSection;
        public TextView textViewAge;
        public TextView textViewEmail;

        public ViewHolder(View itemView) {
            super(itemView);

            //if(ViewType == TYPE_ITEM) {
                textViewLabel1 = (TextView) itemView.findViewById(R.id.textViewLabel1);
                textViewLabel2 = (TextView) itemView.findViewById(R.id.textViewLabel2);
                textViewLabel3 = (TextView) itemView.findViewById(R.id.textViewLabel3);
                textViewLabel4 = (TextView) itemView.findViewById(R.id.textViewLabel4);
                textViewLabel5 = (TextView) itemView.findViewById(R.id.textViewLabel5);
                textViewLabel6 = (TextView) itemView.findViewById(R.id.textViewLabel6);
                textViewLabel7 = (TextView) itemView.findViewById(R.id.textViewLabel7);
                textViewStudentNum = (TextView) itemView.findViewById(R.id.textViewStudentNum);
                textViewSection = (TextView) itemView.findViewById(R.id.textViewSection);
                textViewAge = (TextView) itemView.findViewById(R.id.textViewAge);
                textViewEmail = (TextView) itemView.findViewById(R.id.textViewEmail);
                textViewFirst = (TextView) itemView.findViewById(R.id.textViewFirst);
                textViewMiddle = (TextView) itemView.findViewById(R.id.textViewMiddle);
                textViewLast = (TextView) itemView.findViewById(R.id.textViewLast);
                textViewUser = (TextView) itemView.findViewById(R.id.User);
                //Holderid = 1;
            /*}
            else {
                textViewUser = (TextView) itemView.findViewById(R.id.User);
                Holderid = 0;
            }*/

        }
    }



}



