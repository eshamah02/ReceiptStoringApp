package com.example.receiptstoringapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder>{

    //initializing the context and making a receipt list
    private Context mCtx;
    private List<Receipt> receiptList;

    /*
    Constructor for new receiptAdapter

    @param mCtx - context of receipt adapter
    @param receiptList - newly created list of receipts
     */

    public ReceiptAdapter(Context mCtx, List<Receipt> receiptList) {
        this.mCtx = mCtx;
        this.receiptList = receiptList;
    }

    /*
    Creates viewholder to represent data

    @param parent - viewgroup that the new group will be added into
    @param viewType - view type of the new view

    @returns new holder that holds the View of the new view type
     */

    @NonNull
    @Override
    public ReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        ReceiptViewHolder holder = new ReceiptViewHolder(view);
        return holder;
    }

    /*
    Holder function for recycler view layout (setting each TextView from the xml file to a position)

    @param holder - viewholder which will be updated to represent contents of receiptList from database
    @param position - position of the item within the receiptList
     */

    @Override
    public void onBindViewHolder(@NonNull ReceiptViewHolder holder, int position) {
        Receipt receipt = receiptList.get(position);

        holder.textViewPurpose.setText(receipt.getPurpose());
        holder.textViewPlace.setText(receipt.getPlace());
        holder.textViewDate.setText(receipt.getDate());
        holder.textViewAmount.setText(String.valueOf(receipt.getAmount()));

    }

    /*
    Gets item count of items in the adapter

    @returns the total number of items in the data set
     */

    @Override
    public int getItemCount() {
        return receiptList.size();
    }

    class ReceiptViewHolder extends RecyclerView.ViewHolder {

        TextView textViewPurpose, textViewPlace, textViewDate, textViewAmount;

        public ReceiptViewHolder(@NonNull View itemView) {
            super(itemView);

            //assigning xml ids of items to variable names
            textViewPurpose = itemView.findViewById(R.id.textViewPurpose);
            textViewPlace = itemView.findViewById(R.id.textViewPlace);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);

        }
    }
}
