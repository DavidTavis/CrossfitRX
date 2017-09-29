package com.david.crossfit.model.mapper;

import android.content.Context;

import com.david.crossfit.model.dto.video_info.YoutubeVideoData;
import com.david.crossfit.model.dto.video_info.YoutubeVideoDataSearch;
import com.david.crossfit.model.vo.VideoData;
import com.david.crossfit.view.util.Utils;

import java.util.List;

import rx.functions.Func1;

/**
 * Created by TechnoA on 04.09.2017.
 */

public class VideoDataMapper implements Func1<YoutubeVideoData,List<VideoData>> {

    private Context context;
    public VideoDataMapper(Context context) {
        this.context = context;
    }

    @Override
    public List<VideoData> call(YoutubeVideoData data) {
        List<VideoData> list;
        if (data instanceof YoutubeVideoDataSearch) {
            list = rx.Observable.from(data.items)
                    .map(items -> new VideoData(items.snippet.thumbnails.medium.url,data.nextPageToken, items.snippet.title, items.id.videoId, Utils.formatPublishedDate(context, items.snippet.publishedAt), Utils.getTimeFromString(items.snippet.publishedAt))).toList()
                    .toBlocking()
                    .first();
        }else
            list = rx.Observable.from(data.items)
                    .map(items -> new VideoData(items.snippet.thumbnails.medium.url,data.nextPageToken, items.snippet.title, items.snippet.resourceId.videoId, Utils.formatPublishedDate(context, items.snippet.publishedAt), null)).toList()
                    .toBlocking()
                    .first();
        return list;
    }
}