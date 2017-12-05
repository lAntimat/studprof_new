package ru.lantimat.studprof;

import com.loopj.android.http.PersistentCookieStore;

/**
 * Created by lAntimat on 05.12.2017.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(this); /**Обязательно один раз нужно задать CookieStore*/
        SpRestClient.setCookieStore(myCookieStore);

        Repository.setContext(getApplicationContext());
    }

}
