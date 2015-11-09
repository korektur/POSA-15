package vandy.mooc.presenter;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import vandy.mooc.MVP;
import vandy.mooc.model.ImageDownloadsModel;

/**
 * @author korektur
 * 09/11/15.
 */
public class ImageDownloadAsyncTask extends AsyncTask<Uri, Runnable, Uri> {

    private final Context context;
    private final ImageGrayScaleAsyncTask imageGrayScaleAsyncTask;
    private final Uri directoryPathname;
    private final MVP.ProvidedModelOps ops;

    public ImageDownloadAsyncTask(
            Context context, ImageGrayScaleAsyncTask imageGrayScaleAsyncTask,
            Uri directoryPathname, MVP.ProvidedModelOps ops) {
        this.context = context;
        this.imageGrayScaleAsyncTask = imageGrayScaleAsyncTask;
        this.directoryPathname = directoryPathname;
        this.ops = ops;
    }


    @Override
    protected Uri doInBackground(Uri... params) {
        return ops.downloadImage(context, params[0], directoryPathname);
    }

    @Override
    protected void onPostExecute(Uri uri) {
        imageGrayScaleAsyncTask.executeOnExecutor(THREAD_POOL_EXECUTOR, uri);
    }
}
