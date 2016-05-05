package bamtastic.flickerbrowser;

public class Photo {

    private String mTitle;
    private String mAuthor;
    private String mAuthorId;
    private String mLink;
    private String mTags;
    private String mImage;

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmAuthorId() {
        return mAuthorId;
    }

    public String getmLink() {
        return mLink;
    }

    public String getmTags() {
        return mTags;
    }

    public String getmImage() {
        return mImage;
    }

    @Override
    public String toString() {
        return "Photo{" +
              "mTitle='" + mTitle + '\'' +
              ", mAuthor='" + mAuthor + '\'' +
              ", mAuthorId='" + mAuthorId + '\'' +
              ", mLink='" + mLink + '\'' +
              ", mTags='" + mTags + '\'' +
              ", mImage='" + mImage + '\'' +
              '}';
    }
}
