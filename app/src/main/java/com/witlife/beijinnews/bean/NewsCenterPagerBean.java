package com.witlife.beijinnews.bean;

import java.util.List;

/**
 * // * 作者：尚硅谷-杨光福 on 2016/8/15 14:04
 * // * 微信：yangguangfu520
 * // * QQ号：541433511
 * // * 作用：新闻中心解析后对应的数据
 * //
 */

public class NewsCenterPagerBean {

    private int retcode;

    private List<DataEntity> data;
    private List<Integer> extend;

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setExtend(List<Integer> extend) {
        this.extend = extend;
    }

    public int getRetcode() {
        return retcode;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public List<Integer> getExtend() {
        return extend;
    }

    public static class DataEntity {
        private int id;
        private String title;
        private int type;

        private List<ChildrenData> children;

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setChildren(List<ChildrenData> children) {
            this.children = children;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public int getType() {
            return type;
        }

        public List<ChildrenData> getChildren() {
            return children;
        }

        public static class ChildrenData {
            private int id;
            private String title;
            private int type;
            private String url;

            public void setId(int id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public int getType() {
                return type;
            }

            public String getUrl() {
                return url;
            }
        }
    }
}

