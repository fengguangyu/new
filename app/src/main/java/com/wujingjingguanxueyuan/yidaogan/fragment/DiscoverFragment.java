package com.wujingjingguanxueyuan.yidaogan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wujingjingguanxueyuan.yidaogan.R;
import com.wujingjingguanxueyuan.yidaogan.activity.PublishPostActivity;
import com.wujingjingguanxueyuan.yidaogan.adapter.CommentAdapter;
import com.wujingjingguanxueyuan.yidaogan.adapter.CommonAdapter;
import com.wujingjingguanxueyuan.yidaogan.adapter.CommonViewHolder;
import com.wujingjingguanxueyuan.yidaogan.adapter.PostAdapter;
import com.wujingjingguanxueyuan.yidaogan.base.BaseFragment;
import com.wujingjingguanxueyuan.yidaogan.event.RefreshPostEvent;
import com.wujingjingguanxueyuan.yidaogan.mvp.bean.Post;
import com.wujingjingguanxueyuan.yidaogan.mvp.presenter.ShowPostPresenter;
import com.wujingjingguanxueyuan.yidaogan.mvp.view.ShowPostsView;
import com.wujingjingguanxueyuan.yidaogan.widget.SwipeRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created on 17/9/12 21:26
 */

public class DiscoverFragment extends BaseFragment{// implements ShowPostsView {
    private View rootView;

  //  @Bind(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout mRefreshLayout;
  //  @Bind(R.id.swipe_recycle_post)
  ListView mSwipeRecyclePost;
    //@Bind(R.id.tv_error)
    TextView mTvError;
   // @Bind(R.id.fb_add_post)
    FloatingActionButton mFbAddPost;


    private List<Post> mPosts = new ArrayList<>();
    private PostAdapter mPostAdapter;
    private ShowPostPresenter mShowPostPresenter;
    private int page = 1;
    private final int COUNT = 12;
    private final int PAGE = 1;
    private CommonAdapter<Post> adapter;


    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        mSwipeRecyclePost= rootView.findViewById(R.id.swipe_recycle_post);
        mRefreshLayout = rootView.findViewById(R.id.SwipeRefreshLayout);
        mTvError =  rootView.findViewById(R.id.tv_error);
        mFbAddPost =  rootView.findViewById(R.id.fb_add_post);
       // ButterKnife.bind(this, rootView);
        mFbAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PublishPostActivity.class));
            }
        });

        adapter = new CommonAdapter<Post>(R.layout.item_post,mPosts){

            @Override
            public void convert(CommonViewHolder holder, Post item) {
                holder.setText(R.id.tv_user_name,item.getAuthor().getUsername());
                holder.setText(R.id.tv_post_content,item.getContent());
                holder.setText(R.id.tv_post_time,item.getCreatedAt());
                ImageView mIvUserAvatar = holder.getView(R.id.iv_user_avatar);
                if (item.getAuthor().getAvatar() == null)
                    Glide.with(mContext).load(R.mipmap.icon_message_press).into(mIvUserAvatar);
                else
                    Glide.with(mContext).load(item.getAuthor().getAvatar().getFileUrl()).into(mIvUserAvatar);
            }
        };
        mSwipeRecyclePost.setAdapter(adapter);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FindPosts();
            }
        });

       /* mSwipeRecyclePost.getRecyclerView().setHasFixedSize(true);
        mSwipeRecyclePost.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRecyclePost.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });*/
        return rootView;
    }

    private void FindPosts(){
        BmobQuery<Post> postBmobQuery = new BmobQuery<>();
        //postBmobQuery.addWhereEqualTo("author", BmobUser.getCurrentUser());
        postBmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if(e ==null){
                    mPosts = list;
                    adapter.swapData(mPosts);

                    if(mRefreshLayout !=null && mRefreshLayout.isRefreshing()){
                        mRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });
    }

    /**
     *
     */
    private void loadMore() {
        page = page + 1;
      //  mSwipeRecyclePost.setRefreshEnable(true);
        mShowPostPresenter.showPosts();//page, COUNT);
    }

    /**
     *
     */
    private void refresh() {
        page = PAGE;
      //  mSwipeRecyclePost.setLoadMoreEnable(true);
        mShowPostPresenter.showPosts();//(page, COUNT);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        page = PAGE;
        if(mPosts != null && mPosts.size() ==0){
           // mShowPostPresenter = new ShowPostPresenter(this);
          //  mShowPostPresenter.showPosts();//(PAGE, COUNT);
            FindPosts();
        }else {
            adapter.swapData(mPosts);
        }
    }

    public DiscoverFragment() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
      //  ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_error, R.id.fb_add_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_error:
                break;
            case R.id.fb_add_post:
                startActivity(new Intent(getActivity(), PublishPostActivity.class));
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshPostEvent(RefreshPostEvent event) {
      //  refresh();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
