package com.zhuanghongji.wan.base_common.rx

import io.reactivex.*
import org.reactivestreams.Publisher

/**
 * Base Scheduler
 *
 * 用来处理上流下流相关的线程，比如 subscribeOn "IO 线程"，然后再 observeOn "Android 主线程"
 */
abstract class BaseScheduler<T> protected constructor(

    private val subscribeOnScheduler: Scheduler,

    private val observeOnScheduler: Scheduler

): ObservableTransformer<T, T>,
        SingleTransformer<T, T>,
        MaybeTransformer<T, T>,
        FlowableTransformer<T, T>,
        CompletableTransformer {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Maybe<T>): MaybeSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Completable): CompletableSource {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)
    }
}
