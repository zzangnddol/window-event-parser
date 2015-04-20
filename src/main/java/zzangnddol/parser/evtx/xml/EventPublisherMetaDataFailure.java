package zzangnddol.parser.evtx.xml;

import javax.xml.bind.annotation.XmlElement;

public class EventPublisherMetaDataFailure {
    private Error error;
    private String eventId;
    private String publisherName;
    private String publisherGuid;
    private String processId;

    @Override
    public String toString() {
        return error + "," + eventId + "," + publisherName + "," + publisherGuid + "," + processId;
    }

    public Error getError() {
        return error;
    }

    @XmlElement(name="Error")
    public void setError(Error error) {
        this.error = error;
    }

    public String getEventId() {
        return eventId;
    }

    @XmlElement(name="EventID")
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    @XmlElement(name="PublisherName")
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherGuid() {
        return publisherGuid;
    }

    @XmlElement(name="PublisherGuid")
    public void setPublisherGuid(String publisherGuid) {
        this.publisherGuid = publisherGuid;
    }


    public String getProcessId() {
        return processId;
    }

    @XmlElement(name="ProcessID")
    public void setProcessId(String processId) {
        this.processId = processId;
    }

}
