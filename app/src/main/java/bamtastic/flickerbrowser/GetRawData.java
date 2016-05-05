package bamtastic.flickerbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

enum DownloadStatus { IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK }

public class GetRawData {

    private static final String TAG = GetRawData.class.getSimpleName();
    private String mRawUrl;
    private String mData;
    private DownloadStatus mDownloadStatus;

    public GetRawData(String mRawUrl) {
        this.mRawUrl = mRawUrl;
        this.mDownloadStatus = DownloadStatus.IDLE;
    }

    public void reset() {
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mRawUrl = null;
        this.mData = null;
    }

    public String getmData() {
        return mData;
    }

    public DownloadStatus getmDownloadStatus() {
        return mDownloadStatus;
    }

    public void execute() {
        this.mDownloadStatus = DownloadStatus.PROCESSING;
        DownloadRawData downloadRawData = new DownloadRawData();
        downloadRawData.execute(mRawUrl);
    }

    public class DownloadRawData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            if (params == null) { return null; }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            InputStream inputStream;
            StringBuffer buffer;
            String line;

            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                inputStream = urlConnection.getInputStream();
                if (inputStream == null) { return null; }

                buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                return buffer.toString();
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: Error fetching from server", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "doInBackground: Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String webData) {
            mData = webData;
//            Log.v(TAG, "Data returned: " + mData);
            if (mData == null) {
                if (mRawUrl == null) {
                    mDownloadStatus = DownloadStatus.NOT_INITIALISED;
                } else {
                    mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                }
            } else {
                mDownloadStatus = DownloadStatus.OK;
            }
        }
    }
}

