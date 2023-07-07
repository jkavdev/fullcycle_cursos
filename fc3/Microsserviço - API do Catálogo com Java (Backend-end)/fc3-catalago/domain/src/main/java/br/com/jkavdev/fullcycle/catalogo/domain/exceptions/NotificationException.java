package br.com.jkavdev.fullcycle.catalogo.domain.exceptions;

import br.com.jkavdev.fullcycle.catalogo.domain.validation.handler.Notification;

public class NotificationException extends DomainException {

    public NotificationException(final String aMessage, final Notification aNotification) {
        super(aMessage, aNotification.getErrors());
    }

    public static NotificationException with(final String message, final Notification notification) {
        return new NotificationException(message, notification);
    }
}
