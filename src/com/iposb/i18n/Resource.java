package com.iposb.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class Resource
{
	static Logger logger = Logger.getLogger(Resource.class);
	
    /*=========================================================================
     *   Static Constants and Member Definitions
     *=========================================================================*/
    /**
     * private resources, load from property file on demand at first time
     */
    private static ResourceBundle  mTradChinese;
    private static ResourceBundle  mSimpChinese;
    /**
     * private resources, load from property file on demand at first time
     */
    private static ResourceBundle  mEnglish;

    /**
     * default locale setting
     */
    public static final String EN = "en_US";

    public static final String default_locale = EN;

    /**
     * Load property files on first function call of this class
     */
    static
    {
        try {
            mEnglish = ResourceBundle.getBundle("IPOSB", new Locale("en", "US"));
        }
        catch (MissingResourceException mre){
            logger.error("property files not found");
        }
    }

    public static void main(String[] args) {
        try {
	        logger.info(Resource.getString("ID_CONTENTVIEWER_EDITCONTENT", "en_US"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** #001
     *    Returns corret resourcebundle from locale string
     */
    private static ResourceBundle getResourceBundle(String locale) {
        if (locale == null)
            locale = default_locale;

        if(locale.equals(EN))
            return mEnglish;
        else
            return getResourceBundle(default_locale);
    }

    /** #001
     *    Returns a string value from a resource key.
     */
    public static String getString(String aKey, String locale)
    {
        ResourceBundle res;

        res = getResourceBundle(locale);
        if (res == null)
            return aKey;

        if (aKey == null)
            return "";

        try {
            return res.getString(aKey);
        }
        catch (MissingResourceException mre) {
            return aKey;
        }
    }
    
    /**
     *    Parameterized message with one argument.
     */
    public static String getString(String aKey, String aParam1, String locale)
    {
        Object[] args = { aParam1 };

        return MessageFormat.format(getString(aKey, locale), args);
    }

    /**
     *    Parameterized message with two arguments.
     */
    public static String getString(String aKey, String aParam1, String aParam2,
                                   String locale)
    {
        Object[] args = { aParam1, aParam2 };

        return MessageFormat.format(getString(aKey, locale), args);
    }

    /**
     *    Parameterized message with three arguments.
     */
    public static String getString(String aKey, String aParam1, String aParam2,
                                   String aParam3, String locale)
    {
        Object[] args = { aParam1, aParam2, aParam3 };

        return MessageFormat.format(getString(aKey, locale), args);
    }
}
