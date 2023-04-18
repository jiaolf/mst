package com.jiaolf.dream.net;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Create by jiaolf on 2020/02/21 下午 10:26
 */
public class BaseVO {
    public String code;
    public boolean success;
    public String message;
    public Object data;

    /*
        1.  106和118是重复的
        2.  第90条：获取系统消息	/messages/getUserList    名字和path不对应？
        3.  126是啥意思？  获取服务端数据	getUserInfoTwo	{ userId: userId }

         */
    public static interface TempApi {

        /*** 70 - 100 ***/

    // 获取部位名称(并根据首个部位获取功能列表)
    //
        @GET("getArticlePositionList")
        Call<BaseVO> getBodyPartsList(

        );



        // 根据userId获取他的好友列表
    // { userId: userId, startNum: num }
        @GET("getFriendsListByUserId")
        Call<BaseVO> getFriendsListByUserId(

        );

        // 获取当前医师的文章处方列表
    // {userId:userId}
        @GET("getPresArticleList")
        Call<BaseVO> getDoctorChufangList(

        );

        // 加载更多审核成功的文章处方
    // { userId: userId, startNum: startNum }
        @GET("morePresArticles")
        Call<BaseVO> getCheckedSuccessChufnag(

        );

        // 加载更多审核中的视频
    // { userId: userId, startNum: startNum }
        @GET("morePresExamineIng")
        Call<BaseVO> getCheckingChufang(

        );

        // 加载更多的审核失败的文章处方
    // { userId: userId, startNum: startNum }
        @GET("morePresExamineFail")
        Call<BaseVO> getCheckFailedChufang(

        );

        // 删除文章操作
    // { articleId: articleId, userId: userId }
        @GET("delPresArticleById")
        Call<BaseVO> deleteArticle(

        );


        // 根据处方方案的id，获取方案的详细信息
    // { prescriptionId: prescriptionId}
        @GET("getPrescriptionInfo")
        Call<BaseVO> getPrescriptionInfo(

        );

        // 我的课程支付
    // { userId: userId,  prescriptionId: prescriptionId, needPayMoney: moneyValue, openId: openId, payWay: WX, payProgram: 'APPLETS' }
        @GET("useWeChatToPayPrescription")
        Call<BaseVO> payPrescriptionByWeChat(

        );

        // 根据视频id，获取视频的详细信息
    // {videoId:videoId}
        @GET("getVideoInfoByVideoId")
        Call<BaseVO> getVideoInfoByVideoId(

        );

        // 获取认证用户的录制视频列表
    // { userId: userId }
        @GET("getRecordVideoList")
        Call<BaseVO> getRecordVideoList(

        );

        // 点击加载更多的审核通过的视频
    // { userId: userId, startNum: startNum}
        @GET("moreExamineSuccess")
        Call<BaseVO> getCheckedSuccessVideo(

        );

        // 点击加载更多的审核中的视频
    // { userId: userId, startNum: startNum}
        @GET("moreExamineIng")
        Call<BaseVO> getCheckingVideo(

        );

        // 点击加载更多的审核未通过的视频
    // { userId: userId, startNum: startNum}
        @GET("moreExamineFail")
        Call<BaseVO> getCheckFailedVideo(

        );

        // 根据录制视频的id获取视频的详细信息
    // { recordVideoId: recordVideoId}
        @GET("getRecordVideoInfoById")
        Call<BaseVO> getRecordVideoInfoById(

        );

        // 删除录制视频
    // { recordVideoId: recordVideoId, creatorId: creatorId }
        @GET("delRecordVideoById")
        Call<BaseVO> deleteRecordVideoById(

        );





        // 管理网获取数据
    // { userId: userId }
        @GET("getUserNetworks")
        Call<BaseVO> getUserNetworks(

        );

        // 添加绑定医师
    // { userId: userId, bindUserId: bindUserId}
        @GET("bindUserAndMe")
        Call<BaseVO> bindUserAndMe(

        );

        // 解绑原先的主任，绑定新的主任
    // { userId: userId, bindUserId: bindUserIdss}
        @GET("unbindOriginalBindNow")
        Call<BaseVO> unbindOriginalBindNew(

        );

        // 解绑用户关系
    // { userId: userId, bindUserId: bindUserId, relationId: relationId }
        @GET("unBindUserAndMe")
        Call<BaseVO> unBindUserAndMe(

        );

        // 获取该主任未绑定的好友医师列表
    // { userId: userId}
        @GET("getDoctorFriendsList")
        Call<BaseVO> getDoctorFriendsList(

        );

        // 绑定提交的医师id
    // { userId: userId,bindFriendsIdList: bindFriendsIdList
        @GET("bindUserListAndMe")
        Call<BaseVO> bindUserListAndMe(

        );

        // 获取讲师网数据
    // { userId: userId}
        @GET("getLecturerNetworks")
        Call<BaseVO> getLecturerData(

        );

        // 添加绑定
    // { userId: userId, bindUserId: bindUserId }
        @GET("bindLecturerUserAndMe")
        Call<BaseVO> bindLecturerUserAndMe(

        );





        // 获取出诊记录
    // data: { userId: personalId}
        @GET("getUserVisite")
        Call<BaseVO> getUserVisits(

        );

        // 删除某条出诊记录值
    // { userId: userId, visiteId: visiteId}
        @GET("delVisite")
        Call<BaseVO> deleteVisits(

        );


        // 确定添加出诊记录
    // { userId: userId, visiteAddress: visiteAddress, visiteTime: visiteTime }
        @GET("addVisite")
        Call<BaseVO> addVisits(

        );

        // 获取执业专业度
    // { userId: personalId }
        @GET("getProfessional")
        Call<BaseVO> getProfessional(

        );





        // 获取我的收藏的商品库
    // { userId: personalId, startNum: startNum, searchValue: searchValue }
        @GET("getMyGoodsList")
        Call<BaseVO> getMyGoodsList(

        );



        // 获取收费标准
    // { userId: userId }
    //    @GET("approve/charges/info")
    //    Call<BaseVO> getDoctorServicePrice(
    //
    //    );





        // 获取服务项目数据上门服务项目
    // { userId: userId }
        @GET("getServiceItemList")
        Call<BaseVO> getServiceItems(

        );

        // 提交数据上门服务项目
    //  {userId: userId,itemName: 理疗项目名,itemPrice: 理疗项目金额,itemContent: 项目介绍,id: id ,}
        @GET("addOrUpdateServiceItem")
        Call<BaseVO> addOrUpdateServiceItem(

        );

        // 删除服务项目上门服务项目
    //  {userId: userId,id: id}
        @GET("deleteServiceItem")
        Call<BaseVO> deleteServiceItem(

        );

        /**
         * 131 - 160
         */
    // 提交信息门诊简介
    // { userId: userId, pageType: pageType, contentValue: contentValue }
        @GET("updateContentInfo")
        Call<BaseVO> updateMenzhenInfo(

        );

        // 获取专业人员信息
    // { userId: userId }
        @GET("getClinicPersonnelList")
        Call<BaseVO> getClinicPersonnelList(

        );

        // 删除专业人员信息
    // { userId: userId, id: id }
        @GET("deleteClinicPersonnel")
        Call<BaseVO> deleteClinicPersonnel(

        );

        // 提交专业人员信息
    // {user_id: userId,head_img: headImgId,name: 请输入姓名,position: 请输入职位,id: id,}
        @GET("addOrUpdateClinicPersonnel")
        Call<BaseVO> addOrUpdateClinicPersonnel(

        );

        // 修改诊所封面
    // { userId: userId, clinicImageList: imgsId }
        @GET("updateClinicImageList")
        Call<BaseVO> updateClinicImageList(

        );

        // 获取诊所信息
    // {approveName: approveName, pageNum: that.data.mendianpageNum,pageSize: that.data.mendianpageSize}
        @GET("getNearbyDoctor")
        Call<BaseVO> getClinicInfo(

        );

        // 获取诊所详情
    // userId: this.data.clinicId
        @GET("getClinicInfo")
        Call<BaseVO> getClinicDetail(

        );

    //    // 获取医师服务项目列表
    //// userId: friendsId
    //    @GET("getServiceItemList")
    //    Call<BaseVO> getServiceItems(
    //
    //    );

        // 判断地点与医师之间的距离是否超过服务范围
    // doctorId: this.data.friendsId,address: this.data.orderServiceAddress
        @GET("getTwoAddressDistance")
        Call<BaseVO> checkServiceDistance(

        );

        // 下单
    //
        @GET("generateServiceItemOrder")
        Call<BaseVO> createServiceOrder(

        );



        // 获取侧边栏列表
    // get
        @GET("getMallDomainList")
        Call<BaseVO> getMallDomainList(

        );



        /** 160 - 193 */

    }
}
