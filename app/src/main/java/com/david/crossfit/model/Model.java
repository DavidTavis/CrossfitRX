package com.david.crossfit.model;

import com.david.crossfit.model.dto.video_duration.Duration;
import com.david.crossfit.model.dto.video_info.YoutubeVideoData;
import com.david.crossfit.model.dto.video_info.YoutubeVideoDataSearch;

import rx.Observable;

public interface Model {

    Observable<YoutubeVideoData> getYoutubeVideoDataPlaylist(String playlistId, String pageToken);

    Observable<YoutubeVideoDataSearch> getYoutubeVideoDataSearch(String channelId, String pageToken);

    Observable<Duration> getVideoDuration(String videoIds);
}
