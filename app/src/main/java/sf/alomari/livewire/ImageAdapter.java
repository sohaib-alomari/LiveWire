package sf.alomari.livewire;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {


    //public static String [] imagesArray;
    public static ArrayList<String> list= new ArrayList<>();
    private static onImageClickistener listener;

public ImageAdapter(onImageClickistener listener)
{

    this.listener=listener;
}


    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {



        Context context=viewGroup.getContext();
        int LayoutId=R.layout.adapter;
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(LayoutId,viewGroup,false);
        ImageHolder imgHolder=new ImageHolder(view);

        return imgHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {

        holder.bind(position,listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder  {

        ImageView img;
        public ImageHolder(@NonNull View view) {
            super(view);

            img=(ImageView) view.findViewById(R.id.image_id);



        }

        void bind (final int position, final onImageClickistener imgListener)
        {

            Picasso.get().load(list.get(position)).resize(600,600).into(img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgListener.onImageClick(position);
                }
            });


        }


    }



    public static void  ExtractFeaturesFromJson(String JsonResponse) {

        try {

            if(list.size()>0)
            {
                list.clear();
            }

            JSONObject jsonObject = new JSONObject(JsonResponse);
            JSONArray jsonArr = jsonObject.getJSONArray("data");
            Log.d("cize", String .valueOf( jsonArr.length()));
            String imagUrlString="";
           // imagesArray=new String [jsonArr.length()];

            for (int i = 0; i < jsonArr.length(); i++) {

                JSONObject dataObj = jsonArr.getJSONObject(i);
                JSONArray imagesJsonArr=dataObj.getJSONArray("images");
                JSONObject finalObj=imagesJsonArr.getJSONObject(0);
                imagUrlString=finalObj.getString("link");


                    int stringSize=imagUrlString.length();

                    String checkIfJpg=imagUrlString.substring(stringSize-4,stringSize);

                    Log.d("check",checkIfJpg);
                    if(checkIfJpg.equals(".jpg"))
                    {Log.d("namo",imagUrlString);
                        list.add(imagUrlString);
                    }


                //imagesArray[i]=imagUrlString;


            }

        }
        catch (Exception e) {

        }


    }



    public interface onImageClickistener{
        void onImageClick(int position);


    }



}
