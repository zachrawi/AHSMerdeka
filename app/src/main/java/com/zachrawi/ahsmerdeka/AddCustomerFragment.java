package com.zachrawi.ahsmerdeka;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddCustomerFragment extends Fragment {
    private static final String TAG = "###";

    private static String KEY_ID = "id";

    private int id;

    TextInputEditText etName;
    TextInputEditText etAddress;
    TextInputEditText etPhone;
    MaterialButton btnSave;

    public static AddCustomerFragment newInstance(int id) {
        AddCustomerFragment fragment = new AddCustomerFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID, id);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_customer, container, false);

        etName = view.findViewById(R.id.etName);
        etAddress = view.findViewById(R.id.etAddress);
        etPhone = view.findViewById(R.id.etPhone);
        btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AHSDBHelper ahsdbHelper = new AHSDBHelper(getActivity());

                Customer customer = new Customer();
                customer.setId(id);
                customer.setName(etName.getText().toString());
                customer.setAddress(etAddress.getText().toString());
                customer.setPhone(etPhone.getText().toString());

                if (id > 0) {
                    ahsdbHelper.updateCustomer(customer);
                } else {
                    ahsdbHelper.addCustomer(customer);
                }

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new CustomersFragment())
                        .commit();

                hideKeyboard();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        id = bundle.getInt(KEY_ID);

        if (id > 0) {
            AHSDBHelper ahsdbHelper = new AHSDBHelper(getActivity());

            Customer customer = ahsdbHelper.getCustomer(id);

            etName.setText(customer.getName());
            etAddress.setText(customer.getAddress());
            etPhone.setText(customer.getPhone());
        }

        super.onViewCreated(view, savedInstanceState);
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null) {
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
