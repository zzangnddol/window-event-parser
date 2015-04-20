package zzangnddol.parser.evtx.xml;

import javax.xml.bind.annotation.XmlAttribute;

public class Provider {
    private String name;
    private String guid;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "|" + guid;
    }

    @XmlAttribute(name="Name")
    public void setName(String name) {
        this.name = name;
    }

    public String getGuid() {
        return guid;
    }

    @XmlAttribute(name="Guid")
    public void setGuid(String guid) {
        this.guid = guid;
    }
}
