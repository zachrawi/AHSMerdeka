package com.zachrawi.ahsmerdeka;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomersFragment extends Fragment {
    private static final String TAG = "###";

    RecyclerView recyclerView;
    CustomerAdapter customerAdapter;
    ArrayList<Customer> customers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customers, container, false);

        Log.d(TAG, "onCreateView: ");
        recyclerView = view.findViewById(R.id.recyclerView);

        final AHSDBHelper ahsdbHelper = new AHSDBHelper(getActivity());
        customers = ahsdbHelper.getCustomers();

        customerAdapter = new CustomerAdapter(getActivity(), R.layout.item_customer, customers, new CustomerAdapter.OnClickButtonListener() {
            @Override
            public void onEdit(int position) {
                Customer customer = customers.get(position);

                AddCustomerFragment addCustomerFragment = AddCustomerFragment.newInstance(customer.getId());

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, addCustomerFragment)
                        .commit();
            }

            @Override
            public void onDelete(final int position) {
                Customer customer = customers.get(position);

                AHSDBHelper ahsdbHelper1 = new AHSDBHelper(getActivity());
                ahsdbHelper1.deleteCustomer(customer.getId());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customers.remove(position);
                        customerAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customerAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_customers, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdd:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, AddCustomerFragment.newInstance(0))
                        .commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
