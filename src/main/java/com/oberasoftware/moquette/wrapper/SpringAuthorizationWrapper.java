package com.oberasoftware.moquette.wrapper;

import io.moquette.spi.impl.subscriptions.Topic;
import io.moquette.spi.security.IAuthorizator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;

/**
 * @author Renze de Vries
 */
public class SpringAuthorizationWrapper implements IAuthorizator {
    private static final Logger LOG = LoggerFactory.getLogger(SpringAuthorizationWrapper.class);

    private boolean securityEnabled = true;

    private IAuthorizator getAuthorizer() {
        if(securityEnabled) {
            try {
                return SpringContextProvider.getApplicationContext().getBean(IAuthorizator.class);
            } catch(BeansException e) {
                LOG.info("Could not load a IAuthenticator bean, disabling authenticator security");
                securityEnabled = false;
            }
        }

        //default is always null and disabled
        return null;
    }

    @Override
    public boolean canWrite(Topic topic, String user, String client) {
        IAuthorizator authorizator = getAuthorizer();
        return authorizator == null || authorizator.canWrite(topic, user, client);
    }

    @Override
    public boolean canRead(Topic topic, String user, String client) {
        IAuthorizator authorizator = getAuthorizer();
        return authorizator == null || authorizator.canRead(topic, user, client);
    }
}
