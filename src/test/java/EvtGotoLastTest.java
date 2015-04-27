import java.io.File;

import zzangnddol.parser.evt.Evt;

public class EvtGotoLastTest {
    public static void main(String[] args) throws Exception {
        File f = new File("target/test-classes/eventfile/SecEvent2.Evt");
        Evt evt = new Evt(f);
        evt.gotoLastRecord();
        System.out.println(evt.getCurrentRecorder().fullString());
    }
}
