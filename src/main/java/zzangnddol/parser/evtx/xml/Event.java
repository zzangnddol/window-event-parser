package zzangnddol.parser.evtx.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import zzangnddol.parser.evtx.Record;

@XmlRootElement(name="Event")
public class Event {
    private Record record;

    private System system;
    private EventData eventData;
    private UserData userData;

    public System getSystem() {
        return system;
    }

    @XmlElement(name="System")
    public void setSystem(System system) {
        this.system = system;
    }

    public EventData getEventData() {
        return eventData;
    }

    @XmlElement(name="EventData")
    public void setEventData(EventData eventData) {
        this.eventData = eventData;
    }

    public Record getRecord() {
        return record;
    }

    @XmlTransient
    public void setRecord(Record record) {
        this.record = record;
    }

    @Override
    public String toString() {
        String result = system.toString() + "|" +
                (eventData == null ? "" : eventData.toString()) +
                (userData == null ? "" : userData.toString());
        return result.replaceAll("\\s+", ",");
    }

    public UserData getUserData() {
        return userData;
    }

    @XmlElement(name="UserData")
    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
