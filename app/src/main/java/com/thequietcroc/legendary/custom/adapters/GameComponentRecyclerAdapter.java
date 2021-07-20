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
    private Consumer<T> checkboxOnClickConsumer;
    private Consumer<T> infoButtonConsumer;

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
    }

    public List<T> getComponentList() {
        return componentList;
    }

    public void setCheckboxOnClickConsumer(final Consumer<T> checkboxOnClickConsumer) {
        this.checkboxOnClickConsumer = checkboxOnClickConsumer;
    }

    public void setInfoButtonAction(final Consumer<T> infoButtonConsumer) {
        this.infoButtonConsumer = infoButtonConsumer;
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
            final T component = componentList.get(viewHolder.getAdapterPosition());

            component.setEnabled(((CheckBox)v).isChecked());

            if (null != checkboxOnClickConsumer) {
                checkboxOnClickConsumer.accept(component);
            }
        });

        viewHolder.getGameComponentName().setOnClickListener(v -> viewHolder.getGameComponentEnabledCheckbox().performClick());

        viewHolder.getGameComponentInfoButton().setOnClickListener(v -> {
            final T gameComponent = componentList.get(viewHolder.getAdapterPosition());

            if (null != infoButtonConsumer) {
                infoButtonConsumer.accept(gameComponent);
            }
        });

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
