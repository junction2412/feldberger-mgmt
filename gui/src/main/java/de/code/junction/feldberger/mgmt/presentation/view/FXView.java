package de.code.junction.feldberger.mgmt.presentation.view;

public abstract class FXView<V extends ViewModel> implements View<V> {

    private final V viewModel;

    public FXView(V viewModel) {

        this.viewModel = viewModel;
    }

    @Override
    public V viewModel() {

        return viewModel;
    }
}
