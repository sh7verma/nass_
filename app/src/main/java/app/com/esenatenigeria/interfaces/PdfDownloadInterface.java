package app.com.esenatenigeria.interfaces;

import java.io.File;

/**
 * Created by dev on 27/4/18.
 */

public interface PdfDownloadInterface {
    void onDownloaded(File path, boolean b);
}
