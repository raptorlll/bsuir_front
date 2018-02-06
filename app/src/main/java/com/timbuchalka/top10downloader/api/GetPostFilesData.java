package java.com.timbuchalka.top10downloader.api;

import android.os.AsyncTask;
import android.util.Log;
import com.timbuchalka.top10downloader.models.Role;
import com.timbuchalka.top10downloader.models.ConsultantInformation;
import com.timbuchalka.top10downloader.api.crud.convertor.ConsultantInformationConvertor;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GetPostFilesData
        extends AsyncTask<String, Void, Set<ConsultantInformation>>
        implements FetcherAbstract.OnDownloadComplete {

    private static final String TAG = "GetPostFilesData";
    private ConsultantInformation consultantInformation;
    private File file;

    private Set<ConsultantInformation> mLogin;
    private String mBaseURL;

    private final OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    public interface OnDataAvailable {
        void onDataAvailable(Set<ConsultantInformation> data, DownloadStatus status);
    }

    public GetPostFilesData(OnDataAvailable callBack, File file, ConsultantInformation consultantInformation) {
        Log.d(TAG, "GetLoginData called");
        this.file = file;
        this.consultantInformation = consultantInformation;
        mCallBack = callBack;
    }

    @Override
    protected Set<ConsultantInformation> doInBackground(String... params) {
        ConsultantInformationConvertor<ConsultantInformation> convertor =
                new ConsultantInformationConvertor<ConsultantInformation>();
        String json = convertor.convertElement(consultantInformation);

        FetcherAbstract RawDataFetcher = new MultipartFileFetcher(this, file, json);
        RawDataFetcher.execute();


        Log.d(TAG, "doInBackground ends");
        return mLogin;
    }


    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete starts. Status = " + status);
        Set<ConsultantInformation> token;
        if(status == DownloadStatus.OK) {
            try {
                Collection<ConsultantInformation> convertedData = ApiCrudFactory.convertCollection(ConsultantInformation.class, data);
                mLogin = convertedData;
                mCallBack.onDataAvailable(mLogin, status);
            } catch(JSONException jsone) {
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing Json data " + jsone.getMessage());
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }

        mCallBack.onDataAvailable(mLogin, status);
    }
}





















