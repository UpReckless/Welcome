package com.welcome.studio.welcome.ui.profile.history;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Royal on 16.01.2017.
 */
@HistoryScope
@Module
public class HistoryModule {
    private HistoryView view;
    HistoryModule(HistoryView view){
        this.view=view;
    }

    @HistoryScope
    @Provides
    public HistoryView provideHistoryView(){return view;}

    @HistoryScope
    @Provides
    public HistoryPresenter provideHistoryPresenter(HistoryView view){return new HistoryPresenterImpl(view);}

    @HistoryScope
    @Provides
    public AdapterView provideAdapterView(HistoryView view){return new HistoryAdapter(view);}
}
