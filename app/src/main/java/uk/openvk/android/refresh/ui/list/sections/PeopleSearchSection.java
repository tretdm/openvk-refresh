package uk.openvk.android.refresh.ui.list.sections;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import uk.openvk.android.refresh.R;
import uk.openvk.android.refresh.api.models.User;

public class PeopleSearchSection extends Section {
    private Context ctx;
    private ArrayList<User> items;

    public PeopleSearchSection(Context context, ArrayList<User> items) {
        super(SectionParameters.builder().headerResourceId(R.layout.list_item_section)
                .itemResourceId(R.layout.list_item_search_result).build());
        this.ctx = context;
        this.items = items;
    }

    @Override
    public int getContentItemsTotal() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new Holder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindHeaderViewHolder(holder);
        HeaderHolder headerHolder = (HeaderHolder) holder;
        headerHolder.section_title.setText(ctx.getResources().getString(R.string.people_section, items.size()));
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder itemHolder = (Holder) holder;
        User item = items.get(position);
        itemHolder.title.setText(String.format("%s %s", item.first_name, item.last_name));
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public TextView title;
        public Holder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.search_result_title);
        }
    }

    private static class HeaderHolder extends RecyclerView.ViewHolder {
        private final TextView section_title;

        public HeaderHolder(@NonNull View view) {
            super(view);
            section_title = view.findViewById(R.id.section_title);
        }
    }
}
