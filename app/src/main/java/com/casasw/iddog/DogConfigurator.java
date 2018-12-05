package com.casasw.iddog;

import java.lang.ref.WeakReference;


public enum DogConfigurator {
    INSTANCE;

    public void configure(DogActivity activity) {

        DogRouter router = new DogRouter();
        router.activity = new WeakReference<>(activity);

        DogPresenter presenter = new DogPresenter();
        presenter.output = new WeakReference<DogActivityInput>(activity);

        DogInteractor interactor = new DogInteractor();
        interactor.output = presenter;

        if (activity.output == null) {
            activity.output = interactor;
        }
        if (activity.router == null) {
            activity.router = router;
        }
    }
}
