package bamtastic.flickerbrowser;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.jake.quiltview.QuiltView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity
      extends BaseActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView              mRecyclerView;
    private FlickrRecyclerViewAdapter mAdapter;

    public QuiltView quiltView;
    public ArrayList<ImageView> imageViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activateToolbar();

        quiltView = (com.jake.quiltview.QuiltView) findViewById(R.id.quilt);
        if (quiltView != null) {
            quiltView.setChildPadding(5);
        }
//        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProcessPhotos processPhotos = new ProcessPhotos("new+zealand", true);
        processPhotos.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class ProcessPhotos
          extends GetFlickrJsonData
    {
        public ProcessPhotos(String searchCriteria, boolean matchAll) {
            super(searchCriteria, matchAll);
        }

        @Override
        public void execute() {
            super.execute();
            ProcessData processData = new ProcessData();
            processData.execute();
        }

        public class ProcessData
              extends DownloadJsonData
        {
            @Override
            protected void onPostExecute(String webData) {
//                super.onPostExecute(webData);
//                mAdapter = new FlickrRecyclerViewAdapter(MainActivity.this, getMPhotos());
//                mRecyclerView.setAdapter(mAdapter);

                for (Photo photo : getMPhotos()) {
                    ImageView newImageView = new ImageView(MainActivity.this);
                    Picasso.with(MainActivity.this)
                          .load(photo.getmImage())
                          .into(newImageView);
                    imageViews.add(newImageView);
                }
                quiltView.addPatchImages(imageViews);
            }
        }
    }
}
