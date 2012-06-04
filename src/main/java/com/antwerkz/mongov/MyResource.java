package com.antwerkz.mongov;

import javax.servlet.annotation.WebListener;

/**
 * Example resource class hosted at the URI path "/myresource"
 */
//@Path("/myresource")
@WebListener
public class MyResource { //implements ServletContextListener {
  /**
   * Method processing HTTP GET requests, producing "text/plain" MIME media type.
   *
   * @return String that will be send back as a response of type "text/plain".
   */
  //    @GET
  //    @Produces("text/plain")
  public String getIt() {
    return "Hi there!";
  }

 /* @Override
  public void contextInitialized(ServletContextEvent sce) {
    System.out.println("MyResource.contextInitialized");
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    System.out.println("MyResource.contextDestroyed");
  }
*/
}
