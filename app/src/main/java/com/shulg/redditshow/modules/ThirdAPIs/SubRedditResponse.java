package com.shulg.redditshow.modules.ThirdAPIs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shulg on 10/17/17.
 */

public class SubRedditResponse {

    // data structure example
//    {
//        "parent_type": "some_type",
//        "data": {
//            "children": [
//                {
//                    "id": "some_id",
//                    "data": {
//                            "name": "some_name",
//                            "desc": "bla bla bla"
//                    }
//                },
//                {
//                    "id": "some_other_id",
//                    "data": {
//                            "name": "some_other_name",
//                            "desc": "other bla bla bla"
//                    }
//                }
//            ]
//        }
//    }

    public String kind;
    public DataBeanX data;


    public static class DataBeanX {
        public List<ChildrenBean> children;


        public static class ChildrenBean {
            /**
             * id : some_id
             * data : {"name":"some_name","desc":"bla bla bla"}
             */

            public String kind;
            public DataBean data;


            public static class DataBean {
                /**
                 * name : some_name
                 * desc : bla bla bla
                 */

                public String title;

                public int subscribers;

                @SerializedName("display_name_prefixed")
                public String searchName;

                @SerializedName("icon_img")
                public String iconImage;

                @SerializedName("banner_img")
                public String bannerImage;

                @SerializedName("header_img")
                public String headerImage;

                @SerializedName("display_name")
                public String name;

                @SerializedName("public_description")
                public String description;

            }
        }
    }
}