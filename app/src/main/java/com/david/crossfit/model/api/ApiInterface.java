package com.david.crossfit.model.api;

import com.david.crossfit.model.dto.video_duration.Duration;
import com.david.crossfit.model.dto.video_info.YoutubeVideoData;
import com.david.crossfit.model.dto.video_info.YoutubeVideoDataSearch;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiInterface {

    String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    @Headers({"Accept: application/json"})
    @GET("playlistItems?part=snippet,id&fields=nextPageToken,pageInfo(totalResults),items(snippet(title,thumbnails,publishedAt,resourceId(videoId)))&key=AIzaSyAzy4gma2A2I9iqOPwBzgkEr_3_5-5cz5I&maxResults=8")
    Observable<YoutubeVideoData> getYoutubeVideoDataPlaylist(@Query("playlistId") String playlistId, @Query("pageToken") String pageToken);

    @Headers({"Accept: application/json"})
    @GET("search?part=snippet,id&order=date&type=video&fields=nextPageToken,pageInfo(totalResults),items(id(videoId),snippet(title,thumbnails,publishedAt))&key=AIzaSyAzy4gma2A2I9iqOPwBzgkEr_3_5-5cz5I&maxResults=8")
    Observable<YoutubeVideoDataSearch> getYoutubeVideoDataSearch(@Query("channelId") String channelId, @Query("pageToken") String pageToken);

    @Headers({"Accept: application/json"})
    @GET("videos?part=contentDetails&fields=pageInfo(totalResults),items(contentDetails(duration))&key=AIzaSyAzy4gma2A2I9iqOPwBzgkEr_3_5-5cz5I")
    Observable<Duration> getVideoDuration(@Query("id") String videoIds);

}
