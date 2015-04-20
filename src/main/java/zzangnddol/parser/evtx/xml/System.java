package zzangnddol.parser.evtx.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="System")
public class System {
    private Provider provider;
    private long eventId;
    private String version;
    private String level;
    private String task;
    private String opcode;
    private String keywords;
    private TimeCreated timeCreated;
    private long eventRecordId;
    private String correlation;
    private Execution execution;
    private String channel;
    private String computer;
    private String security;

    @Override
    public String toString() {
        return provider.toString() + "|"+
                eventId + "|" +
                version + "|" +
                level + "|" +
                task + "|" +
                opcode + "|" +
                keywords + "|" +
                timeCreated.toString() + "|" +
                eventRecordId + "|" +
                correlation + "|" +
                execution + "|" +
                channel + "|" +
                computer + "|" +
                security;
    }

    public long getEventId() {
        return eventId;
    }

    @XmlElement(name="EventID")
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getVersion() {
        return version;
    }

    @XmlElement(name="Version")
    public void setVersion(String version) {
        this.version = version;
    }

    public String getLevel() {
        return level;
    }

    @XmlElement(name="Level")
    public void setLevel(String level) {
        this.level = level;
    }

    public String getTask() {
        return task;
    }

    @XmlElement(name="Task")
    public void setTask(String task) {
        this.task = task;
    }

    public String getOpcode() {
        return opcode;
    }

    @XmlElement(name="Opcode")
    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public String getKeywords() {
        return keywords;
    }

    @XmlElement(name="Keywords")
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public TimeCreated getTimeCreated() {
        return timeCreated;
    }

    @XmlElement(name="TimeCreated")
    public void setTimeCreated(TimeCreated timeCreated) {
        this.timeCreated = timeCreated;
    }

    public long getEventRecordId() {
        return eventRecordId;
    }

    @XmlElement(name="EventRecordID")
    public void setEventRecordId(long eventRecordId) {
        this.eventRecordId = eventRecordId;
    }

    public String getCorrelation() {
        return correlation;
    }

    @XmlElement(name="Correlation")
    public void setCorrelation(String correlation) {
        this.correlation = correlation;
    }

    public Execution getExecution() {
        return execution;
    }

    @XmlElement(name="Execution")
    public void setExecution(Execution execution) {
        this.execution = execution;
    }

    public String getChannel() {
        return channel;
    }

    @XmlElement(name="Channel")
    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getComputer() {
        return computer;
    }

    @XmlElement(name="Computer")
    public void setComputer(String computer) {
        this.computer = computer;
    }

    public String getSecurity() {
        return security;
    }

    @XmlElement(name="Security")
    public void setSecurity(String security) {
        this.security = security;
    }

    public Provider getProvider() {
        return provider;
    }

    @XmlElement(name="Provider")
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

}
