package zzangnddol.parser.evtx.xml;

import javax.xml.bind.annotation.XmlAttribute;

public class TimeCreated {
    private String systemTime;

    @Override
    public String toString() {
        return systemTime;
    }

    public String getSystemTime() {
        return systemTime;
    }

    @XmlAttribute(name="SystemTime")
    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

}
