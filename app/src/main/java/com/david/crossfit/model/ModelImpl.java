package com.david.crossfit.model;

import com.david.crossfit.model.api.ApiFactory;
import com.david.crossfit.model.api.ApiInterface;
import com.david.crossfit.model.dto.video_duration.Duration;
import com.david.crossfit.model.dto.video_info.YoutubeVideoData;
import com.david.crossfit.model.dto.video_info.YoutubeVideoDataSearch;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ModelImpl implements Model {

    private final Observable.Transformer schedulersTransformer;
    private ApiInterface apiInterface = ApiFactory.getApiInterface();


    public ModelImpl() {
        schedulersTransformer = o -> ((Observable) o).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io()) // TODO: remove when https://github.com/square/okhttp/issues/1592 is fixed
        ;
    }

    @Override
    public Observable<YoutubeVideoData> getYoutubeVideoDataPlaylist(String playlistId, String pageToken) {
        return apiInterface
                .getYoutubeVideoDataPlaylist(playlistId, pageToken)
                .compose(applySchedulers());
    }

    @Override
    public Observable<YoutubeVideoDataSearch> getYoutubeVideoDataSearch(String channelId, String pageToken) {
        return apiInterface
                .getYoutubeVideoDataSearch(channelId, pageToken)
                .compose(applySchedulers());
    }

    @Override
    public Observable<Duration> getVideoDuration(String videoIds) {
        return apiInterface
                .getVideoDuration(videoIds)
                .compose(applySchedulers());
    }

    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }

}
