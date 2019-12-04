package per.goweii.rxhttp.request;

import androidx.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import per.goweii.rxhttp.core.RxHttp;
import per.goweii.rxhttp.request.base.BaseResponse;
import per.goweii.rxhttp.request.exception.ApiException;
import per.goweii.rxhttp.request.exception.ExceptionHandle;

/**
 * 描述：网络请求
 *
 * @author Cuizhen
 * @date 2018/9/9
 */
public class RxRequest<T, R extends BaseResponse<T>> {

    private final Observable<R> mObservable;


    private RxRequest(Observable<R> observable) {
        mObservable = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public static <T, R extends BaseResponse<T>> RxRequest<T, R> create(@NonNull Observable<R> observable) {
        return new RxRequest<>(observable);
    }


    /**
     * 发起请求并设置成功回调
     *
     * @param callback 请求回调
     */
    public Disposable request(@NonNull ResultCallback<T> callback) {

        return mObservable.subscribe(bean -> {

            if (!isSuccess(bean.getCode())) {

                throw new ApiException(bean.getCode(), bean.getMsg());

            } else {

                callback.onSuccess(bean.getCode(), bean.getData());
            }

        }, throwable -> {

            if (throwable instanceof ApiException) {

                ApiException apiException = (ApiException) throwable;

                callback.onError(apiException.getCode(), apiException.getMsg());

            } else {

                ExceptionHandle handle = RxHttp.getRequestSetting().getExceptionHandle();
                if (handle == null) {
                    handle = new ExceptionHandle();
                }
                handle.handle(throwable);

                callback.onError(handle.getCode(), handle.getMsg());

            }
        }, () -> {

            callback.onFinish();

        }, disposable -> {
            //rxJava的生命周期由外部管理
            callback.onStart(disposable);
        });
    }


    /**
     * 接口返回code判断
     *
     * @param code success code
     * @return boolean
     */
    private boolean isSuccess(int code) {
        if (code == RxHttp.getRequestSetting().getSuccessCode()) {
            return true;
        }
        int[] codes = RxHttp.getRequestSetting().getMultiSuccessCode();
        if (codes == null || codes.length == 0) {
            return false;
        }
        for (int i : codes) {
            if (code == i) {
                return true;
            }
        }
        return false;
    }


    public interface ResultCallback<E> {

        void onStart(Disposable d);

        void onSuccess(int code, E data);

        void onError(int code, String msg);

        void onFinish();
    }

}
