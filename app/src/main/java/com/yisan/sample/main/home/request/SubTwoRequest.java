package com.yisan.sample.main.home.request;

import com.yisan.sample.api.WanApi;
import com.yisan.sample.http.BaseRequest;
import com.yisan.sample.http.RequestListener;
import com.yisan.sample.main.knowledge.model.ChapterBean;

import java.util.List;

import per.goweii.rxhttp.core.RxLife;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.sample.main.home.request
 * @date：2019/12/4 0004 下午 4:59
 */
public class SubTwoRequest extends BaseRequest {


    public static void getKnowledgeList(RxLife rxLife, RequestListener<List<ChapterBean>> listener) {

        request(rxLife, WanApi.api().getKnowledgeList(), listener);

    }


}
