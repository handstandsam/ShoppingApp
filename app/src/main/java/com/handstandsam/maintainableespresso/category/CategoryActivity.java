package com.handstandsam.maintainableespresso.category;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.GsonBuilder;
import com.handstandsam.maintainableespresso.MyAbstractApplication;
import com.handstandsam.maintainableespresso.R;
import com.handstandsam.maintainableespresso.models.Item;
import com.handstandsam.maintainableespresso.network.GitHubService;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CategoryActivity extends AppCompatActivity {

    @Inject
    GitHubService gitHubService;

    @BindView(R.id.categories)
    RecyclerView recyclerView;

    private CategoryRVAdapter recyclerViewAdapter;

    private CategoryView view;

    private CategoryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
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
    }

    public class MyCategoryView implements CategoryView {

        @Override
        public Context getContext() {
            return CategoryActivity.this.getApplicationContext();
        }

        @Override
        public void showItems(List<Item> items) {
            recyclerViewAdapter.setItems(items);
            Timber.d("showItems: " + new GsonBuilder().create().toJson(items));
        }

        @Override
        public void setActionBarTitle(String title) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume(getIntent());
    }
}
