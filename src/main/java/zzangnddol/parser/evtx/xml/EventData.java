package zzangnddol.parser.evtx.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class EventData {
    private List<Data> dataList;

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        if (dataList != null && dataList.size() > 0) {
            for (Data data : dataList) {
                if (buffer.length() > 0) buffer.append("|");
                buffer.append(data.toString());
            }
        }
        return buffer.toString();
    }

    public List<Data> getDataList() {
        return dataList;
    }

    @XmlElement(name="Data")
    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

}
