package ru.android.lab5.journals;

import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JournalAPI {

    @GET("{fileName}")
    Call<ResponseBody> downloadJournalFile(@Path("fileName") String fileName);
}
