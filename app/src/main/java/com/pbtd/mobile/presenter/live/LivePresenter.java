package com.pbtd.mobile.presenter.live;

import android.content.Context;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.pbtd.mobile.Constants;
import com.pbtd.mobile.model.live.CategoryInnerModel;
import com.pbtd.mobile.model.live.ProgramTimeModel;
import com.pbtd.mobile.model.live.WeekProgramModel;
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

public class LivePresenter implements LiveContract.Presenter {

    private final LiveContract.View mView;
    private final Context mContext;
    private final Gson mGson;
    private List<CategoryInnerModel> mCategoryInnerModel;

    public LivePresenter(Context context, LiveContract.View view) {
        mContext = context;
        mView = view;
        mGson = new Gson();
    }

    @Override
    public void getCategoryList() {
        VolleyController volleyController = new VolleyController(mContext, new VolleyController.VolleyCallback() {
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
        volleyController.requestGetAction("https://api.starschina.com/api/tab/michannellist?" +
                "appKey=ZjNmMjc2ODViOTgy&appOs=Android&osVer=4.4.4&appVer=1.0");
    }

    @Override
    public void getProgramOfWeek(String videoId) {
        VolleyController volleyController = new VolleyController(mContext, new VolleyController.VolleyCallback() {
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

        String startDate = StringUtil.getCurrentDate(-5);
        String endDate = StringUtil.getCurrentDate(1);

        volleyController.requestGetAction("https://e.starschina.com/api/channels/" + videoId +
                "/epgs?appOs=Android&appVer=6.3&appKey=" + Constants.KEY+
                "&page=1&pageSize=120&startDate=" + startDate + "&endDate=" + endDate);
    }

    public void getCurrentTimeProgram() {
        VolleyController volleyController = new VolleyController(mContext, new VolleyController.VolleyCallback() {
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
        volleyController.requestGetAction("https://e.starschina.com/api/currentepgs?appKey="
                + Constants.KEY+ "&appOs=Android&osVer=4.3&appVer=1.0");
    }


}
