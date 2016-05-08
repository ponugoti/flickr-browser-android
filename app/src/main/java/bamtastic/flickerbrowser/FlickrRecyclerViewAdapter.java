package bamtastic.flickerbrowser;

import android.app.Activity;
import android.content.Context;

import java.util.List;

public class FlickrRecyclerViewAdapter
//      extends RecyclerView.Adapter<FlickrImageViewHolder>
    extends Activity
{
    private List<Photo> mPhotosList;
    private Context     mContext;

    public FlickrRecyclerViewAdapter(Context context, List<Photo> photosList) {
        mContext = context;
        mPhotosList = photosList;
    }

//    @Override
//    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, null);
//        return new FlickrImageViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(FlickrImageViewHolder holder, int position) {
//        Photo photoItem = mPhotosList.get(position);
//        Picasso.with(mContext).load(photoItem.getmImage())
//              .error(R.drawable.placeholder)
//              .placeholder(R.drawable.placeholder)
//              .into(holder.thumbnail);
//        holder.title.setText(photoItem.getmTitle());
//    }
//
//    @Override
//    public int getItemCount() {
//        return (mPhotosList != null) ? mPhotosList.size() : 0;
//    }
}
