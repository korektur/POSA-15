package vandy.mooc.presenter;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.File;
import java.lang.ref.WeakReference;

import vandy.mooc.common.BitmapUtils;

/**
 * @author korektur
 *         09/11/15.
 */
public class ImageGrayScaleAsyncTask extends AsyncTask<Uri, Void, Void> {

    private final Uri directoryPathname;
    private final Context context;
    private final WeakReference<ImagePresenter> presenter;
    private volatile Uri uri;
    private volatile Uri pathToImage;

    public ImageGrayScaleAsyncTask(Uri directoryPathname, Context context, ImagePresenter presenter, Uri uri) {
        this.directoryPathname = directoryPathname;
        this.context = context;
        this.presenter = new WeakReference<ImagePresenter>(presenter);
        this.uri = uri;
    }

    @Override
    protected Void doInBackground(Uri... params) {
        Uri uri = params[0];
        try {
            this.pathToImage = BitmapUtils.grayScaleFilter(context, uri, directoryPathname);
            File file = new File(uri.getPath());
            file.delete();
        } catch (NullPointerException e) {
            this.pathToImage = null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ImagePresenter imagePresenter = presenter.get();
        if (imagePresenter != null) {
                imagePresenter.onProcessingComplete(uri, pathToImage);
        }
    }
}
