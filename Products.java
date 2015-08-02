package com.example.ahd.productdetails;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Products extends Fragment implements ProductsAdapter.ProductsClickCallBack{


    public Products() {
        // Required empty public constructor
    }

    public interface ProductsClickListener {
        void onProductClick();
    }

    private ProductsClickListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_products, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listProducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        ArrayList<ArrayList<String>> list = new ArrayList<>();
        JSONProductsHandler jsonProdHandler = new JSONProductsHandler();
        int noOfProducts = 0;
        try {
            noOfProducts = jsonProdHandler.getNumberOfAvailableProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> productBasicDetails = new ArrayList<>();
        try {
            for (int i = 0; i < noOfProducts; i++) {
                productBasicDetails = jsonProdHandler.getProductBasicDetails(i);
                list.add(productBasicDetails);
                productBasicDetails = new ArrayList<>();
                //productBasicDetails.clear();
            }
        }
            catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(new ProductsAdapter(list,this));
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (ProductsClickListener) activity;
    }

    @Override
    public void onClick() {
        mListener.onProductClick();
    }
}

