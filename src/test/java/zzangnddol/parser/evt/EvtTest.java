package zzangnddol.parser.evt;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class EvtTest {

    @Test
    public void testGotoLastRecord() throws IOException {
        assertThat(
                getLastRecord("target/test-classes/eventfile/SecEvent.Evt").fullString(),
                is("EVT[Security] 861 2015-04-20T01:05:17Z [-,C:\\WINDOWS\\system32\\svchost.exe,1108,NETWORK SERVICE,NT AUTHORITY,Yes,No,IPv4,UDP,63798,No,No]"));
        assertThat(
                getLastRecord("target/test-classes/eventfile/SecEvent2.Evt").fullString(),
                is("EVT[Security] 592 2015-04-22T06:11:03Z [1372,C:\\totalcmd\\TOTALCMD.EXE,1636,IEUser,IE8WINXP,(0x0,0xA84E)]"));
        assertThat(
                getLastRecord("target/test-classes/eventfile/SecEvent3.Evt").fullString(),
                is("EVT[Security] 592 2015-04-27T02:58:42Z [2732,C:\\Program Files\\FileZilla FTP Client\\fzsftp.exe,532,IEUser,IE8WINXP,(0x0,0xBBA4)]"));
    }

    private Record getLastRecord(String fileName) throws IOException {
        File file = new File(fileName);
        Evt evt  = new Evt(file);
        evt.gotoLastRecord();
        return evt.getCurrentRecorder();
    }
}
