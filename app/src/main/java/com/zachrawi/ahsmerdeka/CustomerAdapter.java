package com.zachrawi.ahsmerdeka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    private Context mContext;
    private int mResource;
    private ArrayList<Customer> mCustomers;
    private OnClickButtonListener mOnClickButtonListener;

    public CustomerAdapter(Context context, int resource, ArrayList<Customer> customers, OnClickButtonListener onClickButtonListener) {
        mContext = context;
        mResource = resource;
        mCustomers = customers;
        mOnClickButtonListener = onClickButtonListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(mResource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Customer customer = mCustomers.get(position);

        holder.tvName.setText(customer.getName());
        holder.tvAddress.setText(customer.getAddress());
        holder.tvPhone.setText(customer.getPhone());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickButtonListener.onEdit(holder.getAdapterPosition());
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickButtonListener.onDelete(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCustomers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAddress;
        TextView tvPhone;
        MaterialButton btnEdit;
        MaterialButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public interface OnClickButtonListener {
        void onEdit(int position);
        void onDelete(int position);
    }
}
