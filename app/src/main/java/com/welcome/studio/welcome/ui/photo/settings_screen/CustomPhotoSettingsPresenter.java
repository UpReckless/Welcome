package com.welcome.studio.welcome.ui.photo.settings_screen;

import android.icu.util.Calendar;
import android.util.Log;

import com.welcome.studio.welcome.model.interactor.PhotoInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.photo.PhotoRouter;
import com.welcome.studio.welcome.util.tagview.Tag;

import javax.inject.Inject;

/**
 * Created by Royal on 12.02.2017. !
 */

class CustomPhotoSettingsPresenter extends BasePresenter<CustomPhotoSettingsView,PhotoRouter> {
    private PhotoInteractor photoInteractor;

    @Inject
    CustomPhotoSettingsPresenter(PhotoInteractor photoInteractor){
        this.photoInteractor=photoInteractor;
    }

    @Override
    public void onStart() {
        getView().initUi();
    }

    @Override
    public void onStop() {

    }

    void tagClick(Tag tag) {
        tag.isDeletable=true;
        getView().showTag(tag);
    }

    void btnShareClick() {
        getView().openDialog();
    }

    void share(String desc, boolean dressCode,String[]tagsName,int hour, int minute) {
        photoInteractor.sharePost(desc,tagsName,dressCode,calculateTime(hour,minute))
                .subscribe(value->getRouter().navigateToWall());
    }

    private long calculateTime(int hour, int minute){
        Calendar currentCalendar=Calendar.getInstance();
        Log.e("currentDay",currentCalendar.getTime().getMonth()+"");
        Log.e("currentHour",currentCalendar.getTime().getHours()+"");
        Log.e("currentMinute",currentCalendar.getTime().getMinutes()+"");

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        if (currentCalendar.getTimeInMillis()>=calendar.getTimeInMillis()){
            try {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
            }catch (Exception e){
                calendar.set(Calendar.DAY_OF_MONTH,1);
            }
            if (currentCalendar.getTimeInMillis()>calendar.getTimeInMillis()){
                calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)==12?1:calendar.get(Calendar.MONTH)+1);
                if (currentCalendar.getTimeInMillis()>calendar.getTimeInMillis())
                    calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR)+1);
            }
        }
        Log.e("destDay",calendar.get(Calendar.DAY_OF_MONTH)+"");
        Log.e("destHour",calendar.get(Calendar.HOUR_OF_DAY)+"");
        Log.e("destMinute",calendar.get(Calendar.MINUTE)+"");
        return calendar.getTimeInMillis();
    }
}
