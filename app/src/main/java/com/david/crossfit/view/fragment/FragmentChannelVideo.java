package com.david.crossfit.view.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.david.crossfit.R;
import com.david.crossfit.model.Model;
import com.david.crossfit.model.ModelImpl;
import com.david.crossfit.model.dto.video_duration.Duration;
import com.david.crossfit.model.mapper.VideoDataMapper;
import com.david.crossfit.model.vo.VideoData;
import com.david.crossfit.view.adapter.AdapterList;
import com.david.crossfit.view.util.Utils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.marshalchen.ultimaterecyclerview.ItemTouchListenerAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class FragmentChannelVideo extends Fragment implements View.OnClickListener {

    private Model model = new ModelImpl();
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

//    private static final String TAG = FragmentChannelVideo.class.getSimpleName();
//    private static final String TAGS = "URL";
    private static final String TEST_DEVICE_ID = "D3C109CC6468CAC6EA4DAC85A0946740";

    private TextView mLblNoResult;
    private LinearLayout mLytRetry;
    private CircleProgressBar mPrgLoading;
    private UltimateRecyclerView mUltimateRecyclerView;

    private AdView adView;
    private boolean isAdmobVisible;


    private int mVideoType;
    private String mChannelId;


    private OnVideoSelectedListener mCallback;


    private AdapterList mAdapterList = null;

//    private ArrayList<HashMap<String, String>> mTempVideoData = new ArrayList<>();
    private ArrayList<VideoData> mVideoData     = new ArrayList<>();

    private String mNextPageToken = "";
    private String mVideoIds = "";
    private String mDuration = "00:00";

    private boolean mIsStillLoading = true;

    private boolean mIsAppFirstLaunched = true;

    private boolean mIsFirstVideo = true;

    public interface OnVideoSelectedListener {
        public void onVideoSelected(String ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);
        setHasOptionsMenu(true);
        Bundle bundle = this.getArguments();

        mVideoType = Integer.parseInt(bundle.getString(Utils.TAG_VIDEO_TYPE));
        mChannelId = bundle.getString(Utils.TAG_CHANNEL_ID);

        mUltimateRecyclerView       = (UltimateRecyclerView) view.findViewById(R.id.ultimate_recycler_view);
        mLblNoResult                = (TextView) view.findViewById(R.id.lblNoResult);
        mLytRetry                   = (LinearLayout) view.findViewById(R.id.lytRetry);
        mPrgLoading                 = (CircleProgressBar) view.findViewById(R.id.prgLoading);
        AppCompatButton btnRetry    = (AppCompatButton) view.findViewById(R.id.raisedRetry);

        adView = (AdView) view.findViewById(R.id.adView);

        isAdmobVisible = Utils.admobVisibility(adView, Utils.IS_ADMOB_VISIBLE);

        new SyncShowAd(adView).execute();

        btnRetry.setOnClickListener(this);
        mPrgLoading.setColorSchemeResources(R.color.accent_color);
        mPrgLoading.setVisibility(View.VISIBLE);

        mIsAppFirstLaunched = true;
        mIsFirstVideo = true;

        mVideoData = new ArrayList<>();

        mAdapterList = new AdapterList(getActivity(), mVideoData);
        mUltimateRecyclerView.setAdapter(mAdapterList);
        mUltimateRecyclerView.setHasFixedSize(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mUltimateRecyclerView.setLayoutManager(linearLayoutManager);
        mUltimateRecyclerView.enableLoadmore();

        mAdapterList.setCustomLoadMoreView(LayoutInflater.from(getActivity())
                .inflate(R.layout.progressbar, null));

        mUltimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                if (mIsStillLoading) {
                    mIsStillLoading = false;
                    mAdapterList.setCustomLoadMoreView(LayoutInflater.from(getActivity()).inflate(R.layout.progressbar, null));

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            getVideoData();

                        }
                    }, 1000);
                } else {
                    disableLoadmore();
                }

            }
        });


        ItemTouchListenerAdapter itemTouchListenerAdapter =
                new ItemTouchListenerAdapter(mUltimateRecyclerView.mRecyclerView,
                        new ItemTouchListenerAdapter.RecyclerViewOnItemClickListener() {
                            @Override
                            public void onItemClick(RecyclerView parent, View clickedView, int position) {

                                if (position < mVideoData.size()) {

                                    mCallback.onVideoSelected(mVideoData.get(position).getVideoId());
                                }
                            }

                            @Override
                            public void onItemLongClick(RecyclerView recyclerView, View view, int i) {
                            }
                        });


        mUltimateRecyclerView.mRecyclerView.addOnItemTouchListener(itemTouchListenerAdapter);


        getVideoData();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnVideoSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnVideoSelectedListener");
        }
    }

    private void getVideoData() {

        mVideoIds = "";
//        final String[] videoId = new String[1];
        Subscription subscription;
        if(mVideoType == 2) {
            subscription = model.getYoutubeVideoDataPlaylist(mChannelId,mNextPageToken)
                    .map(new VideoDataMapper(getContext()))
                    .subscribe(new Observer<List<VideoData>>() {
                        @Override
                        public void onCompleted() {mPrgLoading.setVisibility(View.GONE);}

                        @Override
                        public void onError(Throwable e) {retryView();}

                        @Override
                        public void onNext(List<VideoData> videoDatas) {

                            executeData(videoDatas);

                        }
                    });

        }else {
            subscription = model.getYoutubeVideoDataSearch(mChannelId,mNextPageToken)
                    .map(new VideoDataMapper(getContext()))
                    .subscribe(new Observer<List<VideoData>>() {
                        @Override
                        public void onCompleted() {mPrgLoading.setVisibility(View.GONE);}

                        @Override
                        public void onError(Throwable e) {retryView();}

                        @Override
                        public void onNext(List<VideoData> videoDatas) {

                            executeData(videoDatas);

                        }
                    });

        }

        compositeSubscription.add(subscription);

    }

    private void executeData(List<VideoData> videoDatas) {
        String nextPageToken = "";
        for(VideoData videoData: videoDatas){
            mVideoIds = mVideoIds + videoData.getVideoId() + ",";
            nextPageToken = videoData.getNextPageToken();
            mVideoData.add(videoData);
            mAdapterList.notifyItemInserted(mVideoData.size());
            if(mIsFirstVideo) {
                mIsFirstVideo = false;
                mCallback.onVideoSelected(videoData.getVideoId());
            }
        }
        if (videoDatas.size() == Utils.PARAM_RESULT_PER_PAGE) {
            mNextPageToken = nextPageToken;

        } else {
            mNextPageToken = "";
            disableLoadmore();
        }
        getDuration();
    }

    private void getDuration() {

        Subscription duration = model.getVideoDuration(mVideoIds)
                .subscribe(new Observer<Duration>() {
                    @Override
                    public void onCompleted() {
                        mAdapterList.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        retryView();
                    }

                    @Override
                    public void onNext(Duration duration) {

                        for(int i = 0; i < duration.items.size(); i++){
                            mVideoData.get(i).setDuration(Utils.getTimeFromString(duration.items.get(i).getContentDetails().duration));
                        }

                    }
                });

        compositeSubscription.add(duration);

    }

    private void retryView() {
        mLytRetry.setVisibility(View.VISIBLE);
        mUltimateRecyclerView.setVisibility(View.GONE);
        mLblNoResult.setVisibility(View.GONE);
    }

    private void haveResultView() {
        mLytRetry.setVisibility(View.GONE);
        mUltimateRecyclerView.setVisibility(View.VISIBLE);
        mLblNoResult.setVisibility(View.GONE);
    }

    private void noResultView() {
        mLytRetry.setVisibility(View.GONE);
        mUltimateRecyclerView.setVisibility(View.GONE);
        mLblNoResult.setVisibility(View.VISIBLE);

    }

    private void disableLoadmore() {
        mIsStillLoading = false;
        if (mUltimateRecyclerView.isLoadMoreEnabled()) {
            mUltimateRecyclerView.disableLoadmore();
        }
        mAdapterList.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.clear();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.raisedRetry:
                mPrgLoading.setVisibility(View.VISIBLE);
                haveResultView();
                getVideoData();
                break;
            default:
                break;
        }
    }

    public class SyncShowAd extends AsyncTask<Void, Void, Void> {

        AdView ad;
        AdRequest adRequest, interstitialAdRequest;
        InterstitialAd interstitialAd;
        int interstitialTrigger;

        public SyncShowAd(AdView ad) {
            this.ad = ad;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (isAdmobVisible) {
                if (Utils.IS_ADMOB_IN_DEBUG) {
                    adRequest = new AdRequest.Builder()
                            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                            .addTestDevice(TEST_DEVICE_ID)
                            .build();
                } else {
                    adRequest = new AdRequest.Builder().build();
                }

                interstitialAd = new InterstitialAd(getActivity());
                interstitialAd.setAdUnitId(getActivity().getResources().getString(R.string.interstitial_ad_id));
                interstitialTrigger = Utils.loadIntPreferences(getActivity(), Utils.ARG_ADMOB_PREFERENCE, Utils.ARG_TRIGGER);

                if (interstitialTrigger == Utils.ARG_TRIGGER_VALUE) {
                    if (Utils.IS_ADMOB_IN_DEBUG){
                        interstitialAdRequest = new AdRequest.Builder()
                                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                                .addTestDevice(TEST_DEVICE_ID)
                                .build();
                    } else {
                        interstitialAdRequest = new AdRequest.Builder().build();
                    }
                    Utils.saveIntPreferences(getActivity(), Utils.ARG_ADMOB_PREFERENCE, Utils.ARG_TRIGGER, 1);
                } else {
                    Utils.saveIntPreferences(getActivity(), Utils.ARG_ADMOB_PREFERENCE, Utils.ARG_TRIGGER,
                            (interstitialTrigger + 1));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (isAdmobVisible) {
                ad.loadAd(adRequest);
                if (interstitialTrigger == Utils.ARG_TRIGGER_VALUE) {
                    interstitialAd.loadAd(interstitialAdRequest);
                    interstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {

                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {

                        }

                        @Override
                        public void onAdLoaded() {
                            if (interstitialAd.isLoaded()) {
                                interstitialAd.show();
                            }
                        }
                    });
                }
            }
        }
    }
}