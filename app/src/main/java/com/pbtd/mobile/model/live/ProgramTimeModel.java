package com.pbtd.mobile.model.live;

/**
 * Created by xuqinchao on 17/5/12.
 */

public class ProgramTimeModel {
    private InnerModel current;
    private InnerModel next;

    public InnerModel getCurrent() {
        return current;
    }

    public void setCurrent(InnerModel current) {
        this.current = current;
    }

    public InnerModel getNext() {
        return next;
    }

    public void setNext(InnerModel next) {
        this.next = next;
    }

    public class InnerModel {
        private String epgName;
        private long startTime;
        private long endTime;
        private String typeName;

        public String getEpgName() {
            return epgName;
        }

        public void setEpgName(String epgName) {
            this.epgName = epgName;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }
}
