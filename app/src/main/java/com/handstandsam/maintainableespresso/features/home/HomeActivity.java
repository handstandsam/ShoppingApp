package com.handstandsam.maintainableespresso.features.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.handstandsam.maintainableespresso.LoggedInActivity;
import com.handstandsam.maintainableespresso.MyAbstractApplication;
import com.handstandsam.maintainableespresso.R;
import com.handstandsam.maintainableespresso.features.login.LoginActivity;
import com.handstandsam.maintainableespresso.models.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

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

        void kickToLogin();
    }

    public class MyHomeView implements HomeView {

        @Override
        public Context getContext() {
            return HomeActivity.this;
        }

        @Override
        public void showCategories(List<Category> categories) {
            recyclerViewAdapter.setData(categories);
            Timber.d("showCategories: " + new GsonBuilder().create().toJson(categories));
        }

        @Override
        public void setWelcomeMessage(String welcomeStr) {
            welcomeMessageText.setText(welcomeStr);
        }

        @Override
        public void kickToLogin() {
            HomeActivity.this.startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            HomeActivity.this.finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume(getIntent());
    }
}
