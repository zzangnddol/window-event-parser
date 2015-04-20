package zzangnddol.parser.evtx.xml;

import javax.xml.bind.annotation.XmlElement;

public class UserData {
    private EventPublisherMetaDataFailure failure;

    public EventPublisherMetaDataFailure getFailure() {
        return failure;
    }

    @Override
    public String toString() {
        if (failure != null) return "failure=" + failure.toString();
        else return "";
    }

    @XmlElement(name="EventPublisherMetaDataFailure")
    public void setFailure(EventPublisherMetaDataFailure failure) {
        this.failure = failure;
    }
}
