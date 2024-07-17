package com.mladin.forum.email.twofactor;

import com.mladin.forum.email.ForumEmailManager;
import com.mladin.forum.publisher.ForumEventPublisherService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.logging.Logger;

@Component
public class ForumEmailTwoFactorManager {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Random random = new Random();
    private HashMap<String, Properties> sessions = new HashMap<>();
    private ScheduledExecutorService sessionsTimer = Executors.newScheduledThreadPool(20);

    @Autowired
    private ForumEmailManager forumEmailManager;

    @Autowired
    private ForumEventPublisherService forumEventPublisherService;

    public void initializeSession(String email, HttpServletResponse httpServletResponse, ForumEmailTwoFactorType forumEmailTwoFactorType, Object persistentData) {
        UUID sessionUUID = grabNewUUID();
        int code = grabNewCode();

        Cookie twoFactorCookie = new Cookie("TWO-FACTOR", sessionUUID.toString());
        twoFactorCookie.setMaxAge(60);
        twoFactorCookie.setSecure(true);
        twoFactorCookie.setHttpOnly(true);

        httpServletResponse.addCookie(twoFactorCookie);

        ForumEmailTwoFactorSession forumEmailTwoFactorSession = new ForumEmailTwoFactorSession(sessionUUID, code, forumEmailTwoFactorType, persistentData);

        Properties sessionProperties = new Properties();
        sessionProperties.put("session", forumEmailTwoFactorSession);
        sessionProperties.put("future", scheduleExpire(sessionUUID));

        sessions.put(sessionUUID.toString(), sessionProperties);
        forumEmailManager.sendSimpleEmailMessage(email, "Forum | Email Verification", "Your verification code is: " + code + ".");

        logger.info("Initialized session with UUID: " + sessionUUID.toString() + ".");
    }

    public boolean hasSession(HttpServletRequest httpServletRequest) {
        if(WebUtils.getCookie(httpServletRequest, "TWO-FACTOR") != null) {
            return true;
        }else {
            return false;
        }
    }

    public boolean sessionExpired(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, "TWO-FACTOR");
        if(cookie.getValue() != null && sessions.containsKey(cookie.getValue())) {
            return false;
        }else {
            invalidateSessionClient(httpServletResponse);
            logger.info("Invalided session " + ((cookie.getValue() != null) ? "with UUID: " + cookie.getValue() : ", no UUID was retrieved."));
            return true;
        }
    }

    public boolean confirmSession(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, int code) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, "TWO-FACTOR");
        if(cookie.getValue() != null && sessions.containsKey(cookie.getValue())) {
            Properties sessionProperties = sessions.get(cookie.getValue());
            ForumEmailTwoFactorSession forumEmailTwoFactorSession = (ForumEmailTwoFactorSession) sessionProperties.get("session");

            if(forumEmailTwoFactorSession.getCode() == code) {
                forumEventPublisherService.getPublisher("ACCOUNT_CREATION").publishEvent(forumEmailTwoFactorSession.getPersistentData());

                invalidateSessionServer(forumEmailTwoFactorSession.getSessionUUID());
                invalidateSessionClient(httpServletResponse);

                logger.info("Session UUID: " + forumEmailTwoFactorSession.getSessionUUID() + " completed successfully the two factor challenge.");
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public void invalidateSessionClient(HttpServletResponse httpServletResponse) {
        Cookie twoFactorCookie = new Cookie("TWO-FACTOR", null);
        twoFactorCookie.setMaxAge(0);
        twoFactorCookie.setSecure(true);
        twoFactorCookie.setHttpOnly(true);
        httpServletResponse.addCookie(twoFactorCookie);
    }

    public void invalidateSessionServer(UUID sessionUUID) {
        Properties sessionProperties = sessions.get(sessionUUID.toString());
        ScheduledFuture<?> sessionFuture = (ScheduledFuture<?>) sessionProperties.get("future");
        sessionFuture.cancel(true);

        sessions.remove(sessionUUID.toString());
    }

    public ScheduledFuture<?> scheduleExpire(UUID sessionUUID) {
        logger.info("Scheduled session with UUID: " + sessionUUID.toString() + " to expire in one minute.");
        return sessionsTimer.schedule(new Runnable() {
            @Override
            public void run() {
                sessions.remove(sessionUUID.toString());
                logger.info("Session with UUID " + sessionUUID.toString() + " expired.");
            }
        }, 1, TimeUnit.MINUTES);
    }

    public UUID grabNewUUID() {
        UUID requestedUUID = UUID.randomUUID();
        while(sessions.containsKey(requestedUUID)) {
            requestedUUID = UUID.randomUUID();
        }

        return requestedUUID;
    }

    public int grabNewCode() {
        return random.nextInt(100000, 999999);
    }
}
