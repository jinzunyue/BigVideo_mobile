package com.pbtd.mobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.pbtd.mobile.model.RecommentList;
import com.pbtd.mobile.network.RetrofitUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.pbtd.mobile", appContext.getPackageName());
    }

    @Test
    public void recommentAPI() {
        RetrofitUtil.getInstance(false).getRequestApi().getRecomment(3206)
                .enqueue(new Callback<RecommentList>() {
                    @Override
                    public void onResponse(Call<RecommentList> call, Response<RecommentList> response) {
                        RecommentList body = response.body();
                    }

                    @Override
                    public void onFailure(Call<RecommentList> call, Throwable t) {

                    }
                });
    }
}
