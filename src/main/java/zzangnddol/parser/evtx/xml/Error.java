package zzangnddol.parser.evtx.xml;

import javax.xml.bind.annotation.XmlAttribute;

public class Error {
    private String code;

    public String getCode() {
        return code;
    }

    @XmlAttribute
    public void setCode(String code) {
        this.code = code;
    }

}
