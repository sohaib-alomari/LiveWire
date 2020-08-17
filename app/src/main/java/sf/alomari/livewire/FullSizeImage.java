package sf.alomari.livewire;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class FullSizeImage extends AppCompatActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_size_layout);

        ImageView img=(ImageView)findViewById(R.id.fullImage_id);

        Intent intent = getIntent();

        String value = intent.getStringExtra("url"); //if it's a string you stored.
        Picasso.get().load(value).into(img);





    }

}


