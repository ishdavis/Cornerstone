package hello.peter.hello;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Ishdavis on 1/17/2016.
 */
public class Group {

        public String string;
        public final List<String> children = new ArrayList<String>();

        public Group(String string) {
            this.string = string;
        }
}

