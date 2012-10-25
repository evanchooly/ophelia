public class ApplicationTest {
  /*
    @Test
      public void query() {
          final Map<String, String> params = new HashMap<>();

          params.put("query", "db.Messages.find();");
          running(fakeApplication(), new Runnable() {
              @Override
              public void run() {
                  Result result = callAction(
                          controllers.routes.ref.Application.query(),
                          new FakeRequest().withFormUrlEncodedBody(params)
                  );
                  Assert.assertEquals(200, Helpers.status(result));
                  Assert.assertEquals("application/json", Helpers.contentType(result));
              }
          });
      }

      public void badQuery() {
          Map<String, String> params = new HashMap<>();
          params.put("query", "bob");
          Response response = POST("/query", params);
          System.out.println("response = " + response.contentType);
          assertEquals(new Integer(400), response.status);
          assertContentType("application/json", response);
          assertCharset(play.Play.defaultWebEncoding, response);
      }
  */
}