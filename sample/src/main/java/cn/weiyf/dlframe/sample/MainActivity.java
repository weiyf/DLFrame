package cn.weiyf.dlframe.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.orhanobut.logger.Logger;

import cn.weiyf.dlframe.net.DLException;
import cn.weiyf.dlframe.net.DLObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class MainActivity extends BaseActivity {

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onError(new DLException(-100, "custom"));
            }
        }).subscribe(new DLObserver<Object>(getSupportFragmentManager()) {
            @Override
            public void _onNext(Object o) {
                Logger.i("onNext");
            }
        });

    }

    @Subscribe(tags = @Tag("handlerError"))
    public void handlerError(DLException exception) {
        showToast(exception.toString());
    }
}
