package com.handstandsam.shoppingapp.features.category;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.handstandsam.shoppingapp.LoggedInActivity;
import com.handstandsam.shoppingapp.MyAbstractApplication;
import com.handstandsam.shoppingapp.R;
import com.handstandsam.shoppingapp.models.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends LoggedInActivity {

    @BindView(R.id.categories)
    RecyclerView recyclerView;

    private CategoryRVAdapter recyclerViewAdapter;

    private CategoryView view;

    private CategoryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        ((MyAbstractApplication) getApplication()).getAppComponent().inject(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewAdapter = new CategoryRVAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
        view = new MyCategoryView();
        presenter = new CategoryPresenter(view);
    }

    public interface CategoryView {

        Context getContext();

        void showItems(List<Item> items);

        void setActionBarTitle(String title);

        void showNetworkError(String message);
    }

    public class MyCategoryView implements CategoryView {

        @Override
        public Context getContext() {
            return CategoryActivity.this.getApplicationContext();
        }

        @Override
        public void showItems(List<Item> items) {
            recyclerViewAdapter.setItems(items);
        }

        @Override
        public void setActionBarTitle(String title) {
            getSupportActionBar().setTitle(title);
        }

        @Override
        public void showNetworkError(String message) {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    CategoryActivity.this).setTitle("Networking Error");
            if (message != null) {
                builder.setMessage(message);
            }
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    CategoryActivity.this.finish();
                }
            }).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume(getIntent());
    }
}
