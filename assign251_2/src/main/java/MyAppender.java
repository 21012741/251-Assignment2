import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MyAppender extends AppenderSkeleton{
    private static MyAppender Arraylist;
    private int maxsize = 101;
    private long GetDiscardedLogCount = 0;

    private static List<LoggingEvent> eventsList;
    private Layout layout;

    //SingletonPattern
    private MyAppender(List<LoggingEvent> list) {
        MyAppender.eventsList = list;
        Arraylist = this;
    }

    //it needs to be made sure by some synchronization method that only one thread can access the resource at a given point in time
    public static synchronized MyAppender getArraylist(List<LoggingEvent> list) {
        if (Arraylist == null) {
            Arraylist = new MyAppender(list);
        }
        return Arraylist;
    }



        @Override
        //1
        protected void append(LoggingEvent event) {
            eventsList.add(event);
            if (eventsList.size() >= maxsize) {
                GetDiscardedLogCount++;
                eventsList.remove(0);
            }
        }

        //getcurrentlogs
        public List<LoggingEvent> getCurrentLogs() {
            return Collections.unmodifiableList(eventsList);
        }

        public List<String> getEventStrings() {
            List<String> eventstrings = getCurrentLogs().stream().map(loggingEvent -> this.layout.format(loggingEvent)).collect(Collectors.toList());
            List<String> EventStrings = Collections.unmodifiableList(eventstrings);
            return EventStrings;
        }

        public void printlogs() {
            List<String> eventstrings = getEventStrings();
            for (String event:eventstrings) {
                System.out.println(event);
        }
            eventsList.clear();

        }

        //2
        public void close() {
            Arraylist = null;
            GetDiscardedLogCount = 0;
            eventsList = null;
            layout = null;
        }

        //3
        public boolean requiresLayout() {
            return false;
        }


    }

