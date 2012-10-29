package controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("testing")
public class Testing {
  @GET
  @Path("bob")
  public String eatme() {
    return "bob";
  }
}
