package com.welcome.studio.welcome.ui.profile.history;

import dagger.Subcomponent;

/**
 * Created by Royal on 16.01.2017.
 */
@HistoryScope
@Subcomponent(modules = HistoryModule.class)
public interface HistoryComponent {
    void inject(History history);
    void inject(HistoryPresenterImpl presenter);
    void inject(HistoryAdapter adapter);
}
