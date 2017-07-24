package model.event;

import java.time.LocalDateTime;

/**
 * This class represents an event of mailId delivery
 *
 * @author Hector
 * @version 2017/5/20
 */
public class MailDeliveryEvent extends Event {

    /**
     * The id of delivered Mail
     */
    private int mailId;

    /**
     * Constructor
     *
     * @param staffId
     * @param timeStamp
     * @param mailId
     */
    public MailDeliveryEvent(int id, int staffId, LocalDateTime timeStamp, int mailId) {
        super(id, staffId, timeStamp);
        this.mailId = mailId;
    }

    /**
     * @return the id of the delivered mail
     */
    public int getMailId() {
        return mailId;
    }

    @Override
    public String toString() {
        return "MailDeliveryEvent{" +
                "id=" + id +
                ", mailId=" + mailId +
                "}";
    }
}
