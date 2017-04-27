package com.pbtd.mobile.model;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/27.
 */

public class PlayInfoModel {
    public List<ProgramInfoModel> programList;

    public class ProgramInfoModel {
        public String programCode;
        public String programName;
        public List<MovieInfoModel> movieList;
    }

    public class MovieInfoModel {
        public String movieUrl;
        public String moviesSreenFormat;
        public String movieCode;
        public String movieType;
        public String movieSourceType;
        public int movieDefinition;
        public int movieSequence;
    }
}


