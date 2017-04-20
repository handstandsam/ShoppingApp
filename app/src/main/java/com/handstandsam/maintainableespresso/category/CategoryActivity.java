package com.handstandsam.maintainableespresso.category;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.handstandsam.maintainableespresso.MyAbstractApplication;
import com.handstandsam.maintainableespresso.R;
import com.handstandsam.maintainableespresso.network.GitHubService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity {

    @Inject
    GitHubService gitHubService;

    @BindView(R.id.categories)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Category - Fruit");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        ((MyAbstractApplication) getApplication()).getAppComponent().inject(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(new CategoryRVAdapter());
    }
}
