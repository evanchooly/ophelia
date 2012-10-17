package controllers;

import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import models.User;
import models.User;
import play.cache.Cache;
import play.data.validation.Validation;
import play.libs.Files;
import play.libs.IO;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@With(Deadbolt.class)
public class AuthenticationController extends Controller {
  private static final String CONTEXT_NAME = "-context";
  static final String twitterKey;
  static final String twitterSecret;
  public static final List<String> OPERATIONS;

  static {
    try {
      Properties props = new Properties();
      InputStream stream = AuthenticationController.class.getResourceAsStream("/oauth.conf");
      try {
        props.load(stream);
      } finally {
        stream.close();
      }
      twitterKey = props.getProperty("twitter.key");
      twitterSecret = props.getProperty("twitter.secret");
      stream = AuthenticationController.class.getResourceAsStream("/operations.list");
      try {
        OPERATIONS = IO.readLines(stream);
      } finally {
        stream.close();
      }

    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @Before(unless = "callback")
  public static void oauth() {
    try {
      TwitterContext twitterContext = getTwitterContext();
      if (twitterContext == null || twitterContext.screenName == null) {
        TwitterContext context = new TwitterContext();
        Cache.set(session().getId() + CONTEXT_NAME, context);
        RequestToken requestToken = context.twitter.getOAuthRequestToken(request().uri() + "/callback");
        redirect(requestToken.getAuthenticationURL());
      }
    } catch (TwitterException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static void login() {
    Application.index();
  }

  public static void callback(String oauth_token, String oauth_verifier) {
    try {
      getTwitterContext().authenticate(oauth_token, oauth_verifier);
      if (User.findAll().isEmpty()) {
          User User = new User("", "", getTwitterContext().screenName, "auto added bot owner");
        User.save();
        index();
      }
      Application.index();
    } catch (TwitterException e) {
      System.out.println("e = " + e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @Get("/User")
  @Restrict(JavabotRoleHolder.BOT_User)
  public static void index() {
    Application.Context context = new Application.Context();
    List<User> Users = User.find("order by userName").fetch();
    render(context, Users);
  }

  @Restrict(JavabotRoleHolder.BOT_User)
  public static void config() {
    Application.Context context = new Application.Context();
    Config config = (Config) Config.findAll().get(0);
    List<String> operations = OPERATIONS;
    render(context, config, operations);
  }

  @Restrict(JavabotRoleHolder.BOT_User)
  public static void saveConfig(Config config) {
    List<Config> all = Config.findAll();
    Config old = (Config) all.get(0);
    if (!old.operations.equals(config.operations)) {
      Config.updateOperations(old.operations, config.operations);
    }
    config.id = old.id;
    Config updated = config.merge();
    while (updated.url.endsWith("/")) {
      updated.url = updated.url.substring(0, updated.url.length() - 1);
    }
    updated.save();
    index();
  }

  @Restrict(JavabotRoleHolder.BOT_User)
  public static void javadoc() {
    Application.Context context = new Application.Context();
    List<Api> apis = Api.find("order by name").fetch();
    render(context, apis);
  }

  @Restrict(JavabotRoleHolder.BOT_User)
  public static void addJavadoc(String name, String packages, String baseUrl, File file) throws IOException {
    File savedFile = File.createTempFile("javadoc", ".jar");
    Files.copy(file, savedFile);
    new ApiEvent(Api.find("byName", name).<Api>first() == null, UserController.getTwitterContext().screenName,
      name, packages, baseUrl, savedFile).save();
    javadoc();
  }

  @Restrict(JavabotRoleHolder.BOT_User)
  public static void deleteApi(Long id) throws IOException {
    ApiEvent event = new ApiEvent(id, UserController.getTwitterContext().screenName);
    event.save();
    javadoc();
  }

  @Restrict(JavabotRoleHolder.BOT_User)
  public static void addUser(String ircName, String hostName, String twitter) {
    if (!twitter.isEmpty()) {
      new User(ircName, hostName, twitter, getTwitterContext().screenName).save();
    }
    index();
  }

  @Restrict(JavabotRoleHolder.BOT_User)
  public static void deleteUser(Long id) {
    User User = User.findById(id);
    if (User != null && !User.botOwner) {
      User.delete();
    }
    index();
  }

  @Post("/updateUser")
  @Restrict(JavabotRoleHolder.BOT_User)
  public static void updateUser(String userName, String ircName) {
    List<User> list = User.find("userName = ?", userName).fetch();
    User User = list.get(0);
    User.ircName = ircName;
    User.save();
    index();
  }

  @Get("/addChannel")
  @Restrict(JavabotRoleHolder.BOT_User)
  public static void addChannel() {
    Application.Context context = new Application.Context();
    Channel channel = new Channel();
    renderTemplate("UserController/editChannel.html", context, channel);
  }

  @Get("/showChannel")
  @Restrict(JavabotRoleHolder.BOT_User)
  public static void showChannel(String name) {
    Application.Context context = new Application.Context();
    Channel channel = Channel.find("name = ?1", name).<Channel>first();
    renderTemplate("UserController/editChannel.html", context, channel);
  }

  @Get("/editChannel")
  public static void editChannel(Channel channel) {
    Application.Context context = new Application.Context();
    render(context, channel);
  }

  @Get("/saveChannel")
  @Restrict(JavabotRoleHolder.BOT_User)
  public static void saveChannel(@Valid Channel channel) {
    if (Validation.hasErrors()) {
      params.flash(); // add http parameters to the flash scope
      Validation.keep(); // keep the errors for the next request
      editChannel(channel);
    }
    channel.updated = new Date();
    channel.save();
    index();
  }

  @Get("/toggleLock")
  @Restrict(JavabotRoleHolder.BOT_User)
  public static void toggleLock(Long id) {
    Factoid factoid = Factoid.findById(id);
    factoid.locked = !factoid.locked;
    factoid.save();
    renderJSON(factoid.locked);
  }

  @Get("/register")
  @Restrict(JavabotRoleHolder.BOT_User)
  public static void registerUser(String id) {
    NickRegistration registration = NickRegistration.find("byUrl", id).first();
    if (registration != null && getTwitterContext().screenName.equals(registration.twitterName)) {
      User User = User.find("byUsername", registration.twitterName).first();
      if (User != null) {
        User.ircName = registration.nick;
        User.hostName = registration.host;
        User.save();
      }
      registration.delete();
    }
    index();
  }

  public static TwitterContext getTwitterContext() {
    return (TwitterContext) Cache.get(session.getId() + CONTEXT_NAME);
  }

}