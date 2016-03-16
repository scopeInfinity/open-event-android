package org.fossasia.openevent.dbutils;

import org.fossasia.openevent.api.APIClient;
import org.fossasia.openevent.api.Urls;
import org.fossasia.openevent.api.processor.EventListResponseProcessor;
import org.fossasia.openevent.api.processor.MicrolocationListResponseProcessor;
import org.fossasia.openevent.api.processor.SessionListResponseProcessor;
import org.fossasia.openevent.api.processor.SpeakerListResponseProcessor;
import org.fossasia.openevent.api.processor.SponsorListResponseProcessor;
import org.fossasia.openevent.api.processor.TrackListResponseProcessor;
import org.fossasia.openevent.api.processor.VersionApiProcessor;

/**
 * User: MananWason
 * Date: 31-05-2015
 * <p/>
 * A singleton to keep track of download
 */
public final class DataDownloadManager {
    static DataDownloadManager instance;

    APIClient client = new APIClient();

    private DataDownloadManager() {
    }

    public static DataDownloadManager getInstance() {
        if (instance == null) {
            instance = new DataDownloadManager();
        }
        return instance;
    }

    public void downloadEvents() {
        client.getOpenEventAPI().getEvents(new EventListResponseProcessor());
    }

    public void downloadSpeakers() {
        client.getOpenEventAPI().getSpeakers(Urls.EVENT_ID, new SpeakerListResponseProcessor());
    }

    public void downloadSponsors() {
        client.getOpenEventAPI().getSponsors(Urls.EVENT_ID, new SponsorListResponseProcessor());
    }

    public void downloadSession() {
        client.getOpenEventAPI().getSessions(Urls.EVENT_ID, new SessionListResponseProcessor());
    }

    public void downloadTracks() {
        client.getOpenEventAPI().getTracks(Urls.EVENT_ID, new TrackListResponseProcessor());
    }

    public void downloadMicrolocations() {
        client.getOpenEventAPI().getMicrolocations(Urls.EVENT_ID, new MicrolocationListResponseProcessor());
    }

    public void downloadVersions() {
        client.getOpenEventAPI().getVersion(Urls.EVENT_ID, new VersionApiProcessor());
    }
}
