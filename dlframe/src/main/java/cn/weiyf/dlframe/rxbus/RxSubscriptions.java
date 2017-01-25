package cn.weiyf.dlframe.rxbus;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxSubscriptions {
    private static CompositeDisposable mDisposable = new CompositeDisposable();

    public static boolean isDisposed() {
        return mDisposable.isDisposed();
    }

    public static void add(Disposable disposable) {
        if (disposable != null) {
            mDisposable.add(disposable);
        }
    }

    public static void remove(Disposable disposable) {
        if (disposable != null) {
            mDisposable.remove(disposable);
        }
    }

    public static void clear() {
        mDisposable.clear();
    }

    public static void dispose() {
        mDisposable.dispose();
    }

}
