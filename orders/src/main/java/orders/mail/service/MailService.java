/*
 * Copyright (c) 2022. No party of this code may be reused without permision from the author Edford Jongwe or from Aphrnx LLC,
 */

package orders.mail.service;

import java.time.Instant;

public interface MailService {

    void sendHtmlEmail(String template, String receiver, String message, Instant sendTime, String sender, String subject);
}
