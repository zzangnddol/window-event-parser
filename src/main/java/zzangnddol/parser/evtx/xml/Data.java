package zzangnddol.parser.evtx.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Data {
    private String name;
    private String value;

    @Override
    public String toString() {
        return name + "=" + value;
    }

    public String getName() {
        return name;
    }

    @XmlAttribute(name="Name")
    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    @XmlValue
    public void setValue(String value) {
        this.value = value;
    }

}
