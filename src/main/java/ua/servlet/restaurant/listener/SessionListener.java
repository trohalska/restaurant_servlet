package ua.servlet.restaurant.listener;

import ua.servlet.restaurant.command.CommandUtility;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * logout user if session ends - by default 30 minutes
 */
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        CommandUtility.deleteUserFromSession(httpSessionEvent.getSession());
    }
}
