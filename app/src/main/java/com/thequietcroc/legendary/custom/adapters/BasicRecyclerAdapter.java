package com.thequietcroc.legendary.custom.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thequietcroc.legendary.R;

import java.util.List;

public class BasicRecyclerAdapter<T> extends RecyclerView.Adapter<BasicRecyclerAdapter.ViewHolder> {

    private final List<T> componentList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(final View view) {
            super(view);

            textView = view.findViewById(R.id.basicRecyclerItemTextview);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public BasicRecyclerAdapter(final List<T> componentList) {
        this.componentList = componentList;
    }

    public List<T> getComponentList() {
        return componentList;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.basic_recycler_view_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final T component = componentList.get(position);

        viewHolder.getTextView().setText(component.toString());
    }

    @Override
    public int getItemCount() {
        return componentList.size();
    }
}
