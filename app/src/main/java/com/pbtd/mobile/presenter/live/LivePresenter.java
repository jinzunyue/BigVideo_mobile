package com.pbtd.mobile.presenter.live;

import android.content.Context;

import com.android.volley.VolleyError;
import com.pbtd.mobile.Constants;
import com.pbtd.mobile.model.live.CategoryInnerModel;
import com.pbtd.mobile.model.live.ProgramTimeModel;
import com.pbtd.mobile.model.live.WeekProgramModel;
import com.pbtd.mobile.presenter.BasePresenter;
import com.pbtd.mobile.utils.StringUtil;
import com.pbtd.mobile.volley.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xuqinchao on 17/5/4.
 */

public class LivePresenter extends BasePresenter<LiveContract.View> implements LiveContract.Presenter {

    private List<CategoryInnerModel> mCategoryInnerModel;

    public LivePresenter(Context ctx, LiveContract.View view) {
        super(ctx, view);
    }

    @Override
    public void getCategoryList() {
        mVolley.requestGetAction("https://api.starschina.com/api/tab/michannellist?" +
                "appKey=ZjNmMjc2ODViOTgy&appOs=Android&osVer=4.4.4&appVer=1.0", new VolleyController.VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray datas = jsonObject.getJSONArray("datas");
                    JSONObject jsonObject1 = datas.getJSONObject(0);
                    JSONArray data = jsonObject1.getJSONArray("data");

                    CategoryInnerModel[] categoryInnerModels = mGson.fromJson(data.toString(), CategoryInnerModel[].class);
                    mCategoryInnerModel = Arrays.asList(categoryInnerModels);

                    getCurrentTimeProgram();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public void getProgramOfWeek(String videoId) {
        String startDate = StringUtil.getCurrentDate(-4);
        String endDate = StringUtil.getCurrentDate(1);

        mVolley.requestGetAction("https://e.starschina.com/api/channels/" + videoId +
                "/epgs?appOs=Android&appVer=6.3&appKey=" + Constants.LIVE_KEY +
                "&page=1&pageSize=120&startDate=" + startDate + "&endDate=" + endDate, new VolleyController.VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray rows = jsonObject.getJSONArray("rows");
                    WeekProgramModel[] weekProgramModels = mGson.fromJson(rows.toString(), WeekProgramModel[].class);
                    List<WeekProgramModel> list = Arrays.asList(weekProgramModels);
                    mView.showProgramWeek(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                    mView.showError(e.getMessage());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    public void getCurrentTimeProgram() {
        mVolley.requestGetAction("https://e.starschina.com/api/currentepgs?appKey="
                + Constants.LIVE_KEY + "&appOs=Android&osVer=4.3&appVer=1.0", new VolleyController.VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject rows = jsonObject.getJSONObject("rows");
                    JSONObject jsonObject_271646 = rows.getJSONObject("271646");
                    ProgramTimeModel programTimeModel_271646 = mGson.fromJson(jsonObject_271646.toString(),
                            ProgramTimeModel.class);
                    JSONObject jsonObject_271647 = rows.getJSONObject("271647");
                    ProgramTimeModel programTimeModel_271647 = mGson.fromJson(jsonObject_271647.toString(),
                            ProgramTimeModel.class);
                    JSONObject jsonObject_271652 = rows.getJSONObject("271652");
                    ProgramTimeModel programTimeModel_271652 = mGson.fromJson(jsonObject_271652.toString(),
                            ProgramTimeModel.class);
                    JSONObject jsonObject_271660 = rows.getJSONObject("271660");
                    ProgramTimeModel programTimeModel_271660 = mGson.fromJson(jsonObject_271660.toString(),
                            ProgramTimeModel.class);

                    if (mCategoryInnerModel != null) {
                        for (int i = 0; i < mCategoryInnerModel.size(); i++) {
                            CategoryInnerModel categoryInnerModel = mCategoryInnerModel.get(i);
                            if ("271646".equals(categoryInnerModel.getVideoId() + "")) {
                                categoryInnerModel.setmTimeProgram(programTimeModel_271646);
                            }

                            if ("271647".equals(categoryInnerModel.getVideoId() + "")) {
                                categoryInnerModel.setmTimeProgram(programTimeModel_271647);
                            }

                            if ("271652".equals(categoryInnerModel.getVideoId() + "")) {
                                categoryInnerModel.setmTimeProgram(programTimeModel_271652);
                            }

                            if ("271660".equals(categoryInnerModel.getVideoId() + "")) {
                                categoryInnerModel.setmTimeProgram(programTimeModel_271660);
                            }
                        }
                        mView.showCategoryList(mCategoryInnerModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


}
