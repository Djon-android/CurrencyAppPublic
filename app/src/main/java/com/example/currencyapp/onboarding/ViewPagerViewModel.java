package com.example.currencyapp.onboarding;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.currencyapp.api.ApiFactory;
import com.example.currencyapp.api.ApiService;
import com.example.currencyapp.data.AppDatabase;
import com.example.currencyapp.pojo.Valute;
import com.example.currencyapp.pojo.ValuteResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ViewPagerViewModel extends AndroidViewModel {

    private static AppDatabase database;
    private LiveData<List<Valute>> valutesLiveData;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<Throwable> errors;
//    private long oneDay = 86400000;
    // Test
    private long oneDay = 10000;

    public ViewPagerViewModel(@NonNull Application application) {
        super(application);
        database = AppDatabase.getInstance(application);
        valutesLiveData = database.valuteDao().getAllValutes();
        errors = new MutableLiveData<>();
    }

    public LiveData<List<Valute>> getValutesLiveData() {
        return valutesLiveData;
    }

    public LiveData<Throwable> getErrors() {
        return errors;
    }

    private void insertValutes(Valute valute) {
        new InsertValuteTask().execute(valute);
    }

    private static class InsertValuteTask extends AsyncTask<Valute, Void, Void> {
        @Override
        protected Void doInBackground(Valute... valutes) {
            if (valutes != null && valutes.length > 0) {
                database.valuteDao().insertValute(valutes[0]);
            }
            return null;
        }
    }

    private void deleteAllValutes() {
        new DeleteAllValutesTask().execute();
    }

    private static class DeleteAllValutesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            database.valuteDao().deleteAllValutes();
            return null;
        }
    }

    public void loadData(Boolean isDownloadNow) {
        if (isDownloadNow) {
            loadJSON();
            Log.i("pussy", "Это первый запуск данные загружаются в любом случае");
        } else {
            SharedPreferences prefs = getApplication().getSharedPreferences("startData", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            boolean isDay = prefs.getBoolean("isDay", false);
            long time = prefs.getLong("time", 0);
            int count = prefs.getInt("count", 0);
            if (isDay) {
                long timeNow = System.currentTimeMillis();
                if (timeNow > time + (count * oneDay)) {
                    loadJSON();
                    Log.i("pussy", "Загружаются данные из интернета");
                    editor.putLong("time", timeNow);
                    editor.apply();
                }
            } else {
                int countEnters = prefs.getInt("countEnters", 0);
                if (countEnters >= count) {
                    loadJSON();
                    Log.i("pussy", "Загружаются данные из интернета");
                    editor.putInt("countEnters", 1);
                    editor.apply();
                } else {
                    countEnters++;
                    editor.putInt("countEnters", countEnters);
                    editor.apply();
                }
            }
        }

    }

    private void loadJSON() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService.getValutes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ValuteResponse>() {
                    @Override
                    public void accept(ValuteResponse valuteResponse) throws Exception {
                        deleteAllValutes();
                        for (Valute valute : valuteResponse.getValute().values()) {
                            insertValutes(valute);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
                        Log.i("pussy", "Вылезла ошибка - " + throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void clearErrors() {
        errors.setValue(null);
    }

    @Override
    protected void onCleared() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }
}
