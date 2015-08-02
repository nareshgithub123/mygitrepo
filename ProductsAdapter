package com.example.ahd.productdetails;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by ahd on 30/7/15.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>{

    public interface ProductsClickCallBack {
        void onClick();
    }

    private ProductsClickCallBack mListener;

    private ArrayList<ArrayList<String>> mItemList;

    private DisplayImageOptions mOptions;

    public ProductsAdapter(ArrayList<ArrayList<String>> list, Fragment listener){
        mItemList = list;
        mListener = (ProductsClickCallBack) listener;
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public static class ProductsViewHolder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private TextView productTitle;
        private RatingBar productRating;

        public ProductsViewHolder(View itemView) {
            super(itemView);
            productTitle = (TextView) itemView.findViewById(R.id.productTitle);
            productImage = (ImageView) itemView.findViewById(R.id.productImage);
            productRating = (RatingBar) itemView.findViewById(R.id.productRating);
        }

    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_products, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick();
            }
        });
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder viewHolder, int position) {
        viewHolder.productTitle.setText(mItemList.get(position).get(0));
        viewHolder.productRating.setRating(Double.valueOf(mItemList.get(position).get(1)).intValue());
        //viewHolder.productRating.setRating(Integer.parseInt(mItemList.get(position).get(1)));
        ImageLoader.getInstance().displayImage(mItemList.get(position).get(2), viewHolder.productImage,mOptions);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
