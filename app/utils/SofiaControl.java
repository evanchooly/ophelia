package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

class SofiaControl extends Control {
  @Override
  public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader,
    boolean reload) throws IllegalAccessException, InstantiationException, IOException {
    ResourceBundle bundle = null;
    String bundleName = toBundleName(baseName, locale);
    if(bundleName.contains("_")) {
      bundleName = bundleName.replaceFirst("_", ".");
    }
    final String resourceName = bundleName;
    final ClassLoader classLoader = loader;
    final boolean reloadFlag = reload;
    InputStream stream = null;
    try {
      stream = AccessController.doPrivileged(
        new PrivilegedExceptionAction<InputStream>() {
          public InputStream run() throws IOException {
            InputStream is = null;
            if (reloadFlag) {
              URL url = classLoader.getResource(resourceName);
              if (url != null) {
                URLConnection connection = url.openConnection();
                if (connection != null) {
                  // Disable caches to get fresh data for
                  // reloading.
                  connection.setUseCaches(false);
                  is = connection.getInputStream();
                }
              }
            } else {
              is = classLoader.getResourceAsStream(resourceName);
            }
            return is;
          }
        });
    } catch (PrivilegedActionException e) {
      throw (IOException) e.getException();
    }
    if (stream != null) {
      try {
        bundle = new PropertyResourceBundle(stream);
      } finally {
        stream.close();
      }
    }

    return bundle;
  }
}
