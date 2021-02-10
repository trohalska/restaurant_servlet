package ua.servlet.restaurant.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Logger logger = LogManager.getLogger(Command.class);

    String execute(HttpServletRequest request);
}

