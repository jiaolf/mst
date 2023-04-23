package com.jiaolf.dream.mvp;

/**
 * Create by jiaolf on 2023/4/19
 *
 * 从这里可以看出，View层和Model层完全解耦。
 * P层作为桥梁，或者中间人的角色
 * 缺点是：随着业务越来越复杂，接口也会暴增，项目中的类越来越多
 */
public class GoodsPresenter {
    GoodsViewInterface mView;
    GoodsModel model = new GoodsModel();

    public GoodsPresenter(GoodsViewInterface mView) {
        this.mView = mView;
    }

    // 获取数据
    public void fetchGoodsInfo() {
        mView.showLoading();

        model.getGoodsInfo(new DataCallback() {
            @Override
            public void onSuccess(String data) {
                mView.showGoods(data);
                mView.hideLoading();
            }
        });
    }

}
