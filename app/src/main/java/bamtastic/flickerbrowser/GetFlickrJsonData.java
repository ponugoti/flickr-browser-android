package bamtastic.flickerbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetFlickrJsonData
      extends GetRawData
{

    private static final String TAG = GetFlickrJsonData.class.getSimpleName();
    private List<Photo> mPhotos;
    private Uri         mDestinationUri;

    public GetFlickrJsonData(String searchCriteria, boolean matchAll) {
        super(null);
        createAndUpdateUri(searchCriteria, matchAll);
        mPhotos = new ArrayList<>();
    }

    public boolean createAndUpdateUri(String searchCriteria, boolean matchAll) {
        final String FLICKR_BASE_URI        = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAGS_PARAM             = "tags";
        final String TAG_MODE_PARAM         = "tagmode";
        final String FORMAT_PARAM           = "format";
        final String NO_JSON_CALLBACK_PARAM = "nojsoncallback";

        mDestinationUri = Uri.parse(FLICKR_BASE_URI).buildUpon()
              .appendQueryParameter(TAGS_PARAM, searchCriteria)
              .appendQueryParameter(TAG_MODE_PARAM, matchAll ? "all" : "any")
              .appendQueryParameter(FORMAT_PARAM, "json")
              .appendQueryParameter(NO_JSON_CALLBACK_PARAM, "1")
              .build();

        return mDestinationUri != null;
    }

    public void processResult() {
        if (getmDownloadStatus() != DownloadStatus.OK) {
            Log.e(TAG, "processResult: Error downloading raw file");
            return;
        }

        final String FLICKR_ITEMS     = "items";
        final String FLICKR_TITLE     = "title";
        final String FLICKR_MEDIA     = "media";
        final String FLICKR_PHOTO_URL = "m";
        final String FLICKR_AUTHOR    = "author";
        final String FLICKR_AUTHOR_ID = "author_id";
        final String FLICKR_LINK      = "link";
        final String FLICKR_TAGS      = "tags";

        try {
            JSONObject jsonData   = new JSONObject(getmData());
            JSONArray  itemsArray = jsonData.getJSONArray(FLICKR_ITEMS);

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jsonPhoto = itemsArray.getJSONObject(i);

                String     title     = jsonPhoto.getString(FLICKR_TITLE);
                String     author    = jsonPhoto.getString(FLICKR_AUTHOR);
                String     authorId  = jsonPhoto.getString(FLICKR_AUTHOR_ID);
                String     link      = jsonPhoto.getString(FLICKR_LINK);
                String     tags      = jsonPhoto.getString(FLICKR_TAGS);
                JSONObject jsonMedia = jsonPhoto.getJSONObject(FLICKR_MEDIA);
                String     photoUrl  = jsonMedia.getString(FLICKR_PHOTO_URL);

                Photo photoObject = new Photo(title, author, authorId, link, tags, photoUrl);
                mPhotos.add(photoObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "processResult: Error processing JSON data");
        }
    }

    public class DownloadJsonData
          extends DownloadRawData
    {

        @Override
        protected String doInBackground(String... params) {
            return super.doInBackground(params);
        }

        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();
        }
    }
}
