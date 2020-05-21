package com.global.technolabs.smartshopee_delivery.Helper;

public class Routes {
    //    ``````````````````` ``````````````````````````````domain````````````````````````
    public static final String domain = "http://globaltechnolabs.com/smartshopee/Server/dbtool/";
    //    -----------------------------------------------insideroot----------------------------------------------
    public static final String controller = domain + "controller/api1/";
    public static final String img = domain + "img/";
    //    -----------------------------------------------controller/app/api1/folders----------------------------
    public static final String user = controller + "user/";
    public static final String common = controller + "common/";
    public static final String post = controller + "post/";
    public static final String profile_cover = controller + "profile_cover/";
    public static final String logiin_logout = controller + "logiin_logout/";
    public static final String post_like = controller + "post_like/";
    //   ---------------------------------------------controller/app/api1/files------------------------------
    public static final String insert = common + "insert.php";
    public static final String insert2 = common + "insert2.php";
    public static final String insertinTwoTable = common + "insertintwotable.php";
    public static final String update = common + "UpdateData.php";
    public static final String updateProfile_cover = profile_cover + "updateProfile_cover.php";
    public static final String selectOneByUser = profile_cover + "selectOneByUser.php";
    public static final String insertPost = post + "insertPost.php";
    public static final String logoutAll = common + "logout_all.php";
    public static final String selectAll = common + "selectAll.php";
    public static final String neworder = common + "neworder.php";
    public static final String deliveredorder = common + "deliveredorder.php";
    public static final String selectOne = common + "selectOne.php";
    public static final String selectOneByColumn = common + "selectOneByColumn.php";
    public static final String selectAllbyDate = common + "selectAllbyDate.php";
    public static final String selectAllByQuery = common + "selectAllByQuery.php";
    public static final String countData = common + "countdata.php";
    public static final String selectPost = post + "selectPost.php";
    public static final String LoginByApp = logiin_logout + "LoginByApp.php";
    public static final String deleteLike = common + "DeleteController.php";
    public static final String addquantity = common + "add_quantity.php";
    public static final String deductquantity = common + "deduct_quantity.php";

    //*********************************************img/folders***********************************************
    public static final String img_post = img + "post/";
    public static final String img_user = img + "user/";
    //------------------------------------------------------------------shared preferences---------------------------
    public static final String sharedPrefForLogin = "logindetail";
}
