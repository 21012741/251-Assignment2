import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import java.io.StringWriter;
import java.time.LocalDateTime;




public class VelocityLayout extends Layout {

    private String VelocityPattern;
    VelocityContext Context = new VelocityContext();
    VelocityEngine Engine = new VelocityEngine();

    public VelocityLayout(String VelocityPattern){
        this.VelocityPattern = VelocityPattern;
    }

    @Override
    public String format(LoggingEvent event) {
        Engine.init();
        Context.put("c", event.getLoggerName());
        Context.put("d", LocalDateTime.now().toString());
        Context.put("m", event.getMessage());
        Context.put("p", event.getLevel().toString());
        Context.put("t", event.getThreadName());
        Context.put("n", System.lineSeparator());
//        i.   c (category)
//        ii.  d (date using the default toString() representation)
//        iii. m (message)
//        iv.  p (priority)
//        v.   t (thread)
//        vi.  n (line separator)

        StringWriter Writer = new StringWriter();
        Engine.evaluate(Context, Writer, "", VelocityPattern);

        return Writer.toString();
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

    @Override
    public void activateOptions() {

    }

    public String getPattern() {
        return VelocityPattern;
    }

    public void setPattern(String VelocityPattern) {
        this.VelocityPattern = VelocityPattern;
    }
}
