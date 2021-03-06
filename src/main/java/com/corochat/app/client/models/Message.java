package com.corochat.app.client.models;

import com.corochat.app.client.models.exceptions.MalformedMessageParameterException;
import com.corochat.app.client.utils.logger.Logger;
import com.corochat.app.client.utils.logger.LoggerFactory;
import com.corochat.app.client.utils.logger.level.Level;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <h1>The Message object</h1>
 * <p>
 *     A Message is an object that has:
 *     <ul>
 *         <li>A message</li>
 *         <li>An user pseudo</li>
 *         <li>A date</li>
 *     </ul>
 * </p>
 * //TODO Include diagram of Message
 *
 * @author Raphael Dray
 * @author Thierry Khamphousone
 * @version 0.0.8
 * @since 0.0.3
 * @see Date
 */
public class Message implements Model<MalformedMessageParameterException> {
    private static final Logger logger = LoggerFactory.getLogger(Message.class.getSimpleName());

    private String message;
    private String userPseudo;
    private Date date;

    /**
     * This constructor initialize these attributes
     * @param message The message of the user
     * @param userPseudo The user pseudo
     * @param date The date of the message sent
     */
    public Message(String message, String userPseudo, Date date) throws MalformedMessageParameterException {
        this.message = message;
        this.userPseudo = userPseudo;
        this.date = date;
        logger.log("New message has been created", Level.INFO);
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", userPseudo='" + userPseudo + '\'' +
                ", date=" + date +
                '}';
    }

    /**
     * Getter of the user message
     * @return String - The user message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Setter of the user message
     * @param message The user message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter of the user pseudo
     * @return String - The user pseudo
     */
    public String getUserPseudo() {
        return this.userPseudo;
    }

    /**
     * Setter of the user pseudo
     * @param userPseudo The user pseudo
     */
    public void setUserPseudo(String userPseudo) {
        this.userPseudo = userPseudo;
    }

    /**
     * Getter of the date of the message sent
     * @return Date - The date of the message sent
     * @see Date
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Setter of the date of the message sent
     * @param date The date of the message sent
     * @see Date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public void validate() throws MalformedMessageParameterException {
        List<String> errors = new ArrayList<>();

        if(!hasContent(this.message)) errors.add("message has no content."); //OK
        if(!hasContent(this.userPseudo)) errors.add("pseudo has no content."); //OK
        ensureNotNull(this.date,"date has no content", errors); //OK


        boolean passes = !this.userPseudo.matches("^[\\d !\"#$%&'()*+,-./\\\\:;<=>?@\\[\\]^_`{|}~].*"); //TO REVIEW
        if(!passes) errors.add("pseudo must not start with a number or special character");
        passes = this.date.compareTo(new Date())<=0;
        if(!passes) errors.add("date is not correct");

        if (!errors.isEmpty()) {
            MalformedMessageParameterException ex = new MalformedMessageParameterException();
            for (String error : errors)
                ex.addSuppressed(new MalformedMessageParameterException(error));
            throw ex;
        }
    }
}
