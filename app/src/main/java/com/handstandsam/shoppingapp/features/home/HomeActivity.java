package com.handstandsam.shoppingapp.features.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.handstandsam.shoppingapp.LoggedInActivity;
import com.handstandsam.shoppingapp.MyAbstractApplication;
import com.handstandsam.shoppingapp.R;
import com.handstandsam.shoppingapp.models.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends LoggedInActivity {

    HomePresenter presenter;

    @BindView(R.id.categories)
    RecyclerView recyclerView;

    @BindView(R.id.welcome_message)
    TextView welcomeMessageText;

    HomeView homeView;

    private HomeRVAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Categories");
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        ((MyAbstractApplication) getApplication()).getAppComponent().inject(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewAdapter = new HomeRVAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        homeView = new MyHomeView();
        presenter = new HomePresenter(homeView);
    }

    public interface HomeView {

        Context getContext();

        void showCategories(List<Category> categories);

        void setWelcomeMessage(String welcomeStr);
    }

    public class MyHomeView implements HomeView {

        @Override
        public Context getContext() {
            return HomeActivity.this;
        }

        @Override
        public void showCategories(List<Category> categories) {
            recyclerViewAdapter.setData(categories);
        }

        @Override
        public void setWelcomeMessage(String welcomeStr) {
            welcomeMessageText.setText(welcomeStr);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume(getIntent());
    }
}
