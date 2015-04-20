package zzangnddol.parser.evtx.xml;

public class Execution {
    private String processId;
    private String threadId;

    public String getProcessId() {
        return processId;
    }

    @Override
    public String toString() {
        return processId + "|" + threadId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

}
