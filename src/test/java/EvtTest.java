import zzangnddol.parser.evt.Evt;
import zzangnddol.parser.evt.Record;


public class EvtTest {
    public static void main(String[] args) throws Exception {
        Evt evt = new Evt("target/test-classes/eventfile/SecEvent2.Evt");
        Record record = null;
        while((record = evt.nextRecord()) != null) {
            System.out.println(record.fullString());
        }
    }
}
