package zero.zd.daily_event_logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Event {

    private UUID mId;
    private String mEvent;
    private Date mDate;

    public Event(String event, Date date) {
        this(event, date, UUID.randomUUID());
    }

    public Event(String event, Date date, UUID uuid) {
        mId = uuid;
        mEvent = event;
        mDate = date;
    }

    public UUID getId() {
        return mId;
    }

    public String getEvent() {
        return mEvent;
    }

    public void setEvent(String event) {
        mEvent = event;
    }

    public Date getDate() {
        return mDate;
    }

    public String getStringDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, MMM dd, yy  k:m", Locale.ENGLISH);
        return simpleDateFormat.format(mDate);
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
