package uk.openvk.android.refresh.user_interface.fragments.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uk.openvk.android.refresh.R;
import uk.openvk.android.refresh.api.Account;
import uk.openvk.android.refresh.api.models.Conversation;
import uk.openvk.android.refresh.user_interface.GlideApp;
import uk.openvk.android.refresh.user_interface.layouts.ProgressLayout;
import uk.openvk.android.refresh.user_interface.list_adapters.ConversationsAdapter;

public class MessagesFragment extends Fragment {
    private View view;
    private ArrayList<Conversation> conversations;
    private RecyclerView conversationsView;
    private ConversationsAdapter conversationsAdapter;
    private LinearLayoutManager llm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.conversations_fragment, container, false);
        ((SwipeRefreshLayout) view.findViewById(R.id.messages_swipe_layout))
                .setProgressBackgroundColorSchemeResource(R.color.navbarColor);
        ((SwipeRefreshLayout) view.findViewById(R.id.messages_swipe_layout)).setVisibility(View.GONE);
        TypedValue typedValue = new TypedValue();
        requireContext().getTheme().resolveAttribute(androidx.appcompat.R.attr.colorAccent, typedValue, true);
        if(PreferenceManager.getDefaultSharedPreferences(requireContext()).getBoolean("dark_theme", false)) {
            ((SwipeRefreshLayout) view.findViewById(R.id.messages_swipe_layout)).setProgressBackgroundColorSchemeColor(getResources().getColor(com.google.android.material.R.color.background_material_dark));
        } else {
            ((SwipeRefreshLayout) view.findViewById(R.id.messages_swipe_layout)).setProgressBackgroundColorSchemeColor(getResources().getColor(android.R.color.white));
        }
        ((SwipeRefreshLayout) view.findViewById(R.id.messages_swipe_layout)).setColorSchemeColors(typedValue.data);
        ((ProgressLayout) view.findViewById(R.id.progress_layout)).setVisibility(View.VISIBLE);
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createAdapter(Context ctx, ArrayList<Conversation> conversations, Account account) {
        this.conversations = conversations;
        conversationsView = (RecyclerView) view.findViewById(R.id.conversations_rv);
        if(conversationsAdapter == null) {
            conversationsAdapter = new ConversationsAdapter(getContext(), this.conversations, account);
            llm = new LinearLayoutManager(ctx);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            conversationsView.setLayoutManager(llm);
            conversationsView.setAdapter(conversationsAdapter);
        } else {
            //conversationsAdapter.setArray(wallPosts);
            conversationsAdapter.notifyDataSetChanged();
        }
        ((ProgressLayout) view.findViewById(R.id.progress_layout)).setVisibility(View.GONE);
        ((SwipeRefreshLayout) view.findViewById(R.id.messages_swipe_layout)).setVisibility(View.VISIBLE);
    }

    public void disableUpdateState() {
        ((SwipeRefreshLayout) view.findViewById(R.id.newsfeed_swipe_layout)).setRefreshing(false);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void loadAvatars(ArrayList<Conversation> conversations) {
        Context ctx = requireContext();
        conversationsAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void refreshAdapter() {
        if(conversationsAdapter != null) {
            conversationsAdapter.notifyDataSetChanged();
        }
    }
}
