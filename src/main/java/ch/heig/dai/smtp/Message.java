package ch.heig.dai.smtp;

/**
 * Record with the contents of a message
 * Note: we used a record because we only need a default constructor and two parameters with getters and setters
 * @param subject of the email
 * @param body of the email
 */
public record Message(String subject, String body) {}
