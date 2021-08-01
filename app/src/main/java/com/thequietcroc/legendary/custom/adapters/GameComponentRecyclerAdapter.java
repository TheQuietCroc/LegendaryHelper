package com.thequietcroc.legendary.custom.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.models.gamecomponents.BaseGameComponent;

import java.util.List;
import java.util.function.Consumer;

public class GameComponentRecyclerAdapter<T extends BaseGameComponent>
        extends RecyclerView.Adapter<GameComponentRecyclerAdapter.ViewHolder> {

    private final List<T> componentList;
    private final boolean displayInfoButton;
    private Consumer<ViewHolder> checkboxOnClickConsumer = vh -> {};
    private Consumer<ViewHolder> infoButtonOnClickConsumer = vh -> {};

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView gameComponentName;
        private final CheckBox gameComponentEnabledCheckbox;
        private final Button gameComponentInfoButton;

        public ViewHolder(final View view) {
            super(view);

            gameComponentName = view.findViewById(R.id.gameComponentName);
            gameComponentEnabledCheckbox = view.findViewById(R.id.gameComponentEnabledCheckbox);
            gameComponentInfoButton = view.findViewById(R.id.gameComponentInfoButton);
        }

        public TextView getGameComponentName() {
            return gameComponentName;
        }

        public CheckBox getGameComponentEnabledCheckbox() {
            return gameComponentEnabledCheckbox;
        }

        public Button getGameComponentInfoButton() {
            return gameComponentInfoButton;
        }
    }

    public GameComponentRecyclerAdapter(final List<T> componentList) {
        this.componentList = componentList;
        this.displayInfoButton = true;
    }

    public GameComponentRecyclerAdapter(final List<T> componentList,
            final boolean displayInfoButton) {
        this.componentList = componentList;
        this.displayInfoButton = displayInfoButton;
    }

    public List<T> getComponentList() {
        return componentList;
    }

    public void setCheckboxOnClickCosumer(final Consumer<ViewHolder> checkboxOnClickConsumer) {
        this.checkboxOnClickConsumer = checkboxOnClickConsumer;
    }

    public void setInfoButtonOnClickConsumer(final Consumer<ViewHolder> infoButtonOnClickConsumer) {
        this.infoButtonOnClickConsumer = infoButtonOnClickConsumer;
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {
        // Create a new view, which defines the UI of the list item
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.game_component_recycler_view_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.getGameComponentEnabledCheckbox().setOnClickListener(v -> {
            checkboxOnClickConsumer.accept(viewHolder);
        });

        viewHolder.getGameComponentName().setOnClickListener(v -> viewHolder.getGameComponentEnabledCheckbox().performClick());

        if (displayInfoButton) {
            viewHolder.getGameComponentInfoButton().setOnClickListener(v -> {
                infoButtonOnClickConsumer.accept(viewHolder);
            });
        } else
            viewHolder.getGameComponentInfoButton().setVisibility(View.GONE);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final T component = componentList.get(position);

        viewHolder.getGameComponentName().setText(component.getName());
        viewHolder.getGameComponentEnabledCheckbox().setChecked(component.isEnabled());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return componentList.size();
    }

}
