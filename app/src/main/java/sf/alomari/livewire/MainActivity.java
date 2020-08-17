package sf.alomari.livewire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ImageAdapter.onImageClickistener {

    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    Button searchBtn;
    EditText editText;
    private ProgressBar mLoadingIndicator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBtn=(Button) findViewById(R.id.searchButton_Id);
        searchBtn.setOnClickListener(this);
        editText=(EditText) findViewById(R.id.editText_Id);
        adapter=new ImageAdapter(this);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);


        recyclerView=(RecyclerView)findViewById(R.id.my_recycler_view);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
        DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(true);



        new imageQuery().execute();


    }

    @Override
    public  void onClick(View view) {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
        new imageQuery().execute();
        if(ImageAdapter.list.size()==0)
        {
            Toast.makeText(this,"No Results Were Found Try a different Search Word",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onImageClick(int position) {


        Intent intent= new Intent(MainActivity.this,FullSizeImage.class);
        intent.putExtra("url",ImageAdapter.list.get(position));
        MainActivity.this.startActivity(intent);

    }


    public class imageQuery extends AsyncTask<URL,Void,String>
    {

        String typedWord=String.valueOf(editText.getText());



        String url="https://api.imgur.com/3/gallery/search/?q="+typedWord;
        String result="";
        @Override
        protected String doInBackground(URL... urls) {

            if(typedWord.length()==0)
            {
                url="https://api.imgur.com/3/gallery/search/?q=birds";
            }

            OkHttpClient client = new OkHttpClient();
            try {
                result = NetworkUtils.run(url);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            mLoadingIndicator.setVisibility(View.GONE);
            ImageAdapter.ExtractFeaturesFromJson(s);


            recyclerView.setAdapter(adapter);

            Log.d("Result",s);
        }
    }
}

