package com.iposb.utils;

import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.web.mgt.CookieRememberMeManager;

/**
 * This extension of the CookieRememberMeManager used a random cipher key for
 * better security.
 * 
 * 參考自：https://shiro-user.582556.n2.nabble.com/Randomized-key-for-RememberMe-token-td7579078.html
 * 
 */
public class RandomRememberMeManager
    extends CookieRememberMeManager {

    /**
     * Default constructor.  Sets a new random cipher key.
     */
    public RandomRememberMeManager() {
        super();
        setCipherKey(new AesCipherService().generateNewKey().getEncoded());
    }

}