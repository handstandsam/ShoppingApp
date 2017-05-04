package com.handstandsam.maintainableespresso;

public class MyApplication extends MyAbstractApplication {


    protected abstract AppComponent createAppComponent(); {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(NetworkModule.LOCALHOST_ENDPOINT))
                .repositoryModule(new RepositoryModule(this))
                .build();
    }


}
