package ru.android.lab41.models;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MusicsRadioURL {

    @POST("api_get_current_song.php")
    Call<MusicsRadioResponse> getNameMusic(@Body UserRadioData data);
}
