package bamtastic.flickerbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FlickrRecyclerViewAdapter
      extends RecyclerView.Adapter<FlickrImageViewHolder>
{
    private List<Photo> mPhotosList;
    private Context     mContext;

    public FlickrRecyclerViewAdapter(Context context, List<Photo> photosList) {
        mContext = context;
        mPhotosList = photosList;
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, null);
        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder holder, int position) {
        Photo photoItem = mPhotosList.get(position);
        Picasso.with(mContext).load(photoItem.getmImage())
              .error(R.drawable.placeholder)
              .placeholder(R.drawable.placeholder)
              .into(holder.thumbnail);
        holder.title.setText(photoItem.getmTitle());
    }

    @Override
    public int getItemCount() {
        return (mPhotosList != null) ? mPhotosList.size() : 0;
    }

    public void loadnewData(List<Photo> newPhotos) {
        mPhotosList = newPhotos;
        notifyDataSetChanged();
    }
}
