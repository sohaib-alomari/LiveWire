package sf.alomari.livewire;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {


    static OkHttpClient client = new OkHttpClient();

    public static   String run (String url) throws IOException

    {
        Request request = new Request.Builder()
                .url(url).header("Authorization","Client-ID b067d5cb828ec5a")
                .build();

        Response response = client.newCall(request).execute();

        return  (response.body()).string();
    }




}