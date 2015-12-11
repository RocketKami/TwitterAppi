package WebApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterConsumer extends HttpServlet {

    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        OAuthConsumer consumer = null;
        OAuthAccessor accessor = null;
        try {
            consumer = CookieConsumer.getConsumer("twitter", getServletContext());
            accessor = CookieConsumer.getAccessor(request, response, consumer);

            String TWITTER_CONSUMER_KEY = consumer.consumerKey;
            String TWITTER_SECRET_KEY = consumer.consumerSecret;
            String TWITTER_ACCESS_TOKEN = accessor.accessToken;
            String TWITTER_ACCESS_TOKEN_SECRET = accessor.tokenSecret;
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
                    .setOAuthConsumerSecret(TWITTER_SECRET_KEY)
                    .setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
                    .setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"es\"><head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("	<link href='http://fonts.googleapis.com/css?family=Lato' rel='stylesheet' type='text/css'>\n"
                    + "	<link rel=\"stylesheet\" href=\"css/style.css\">\n"
                    + "	<link rel=\"stylesheet\" href=\"css/bootstrap.css\">");
            out.println("<link rel=\"stylesheet\" href=\"css/flat-ui.css\">\n");
           
            out.println("<body><header>\n"
                    + "		<img src =\"Stalker-b.jpg \" width = 800 >\n"
                    + "		<br>\n"
                    + "		<br>\n"
                    + "	</header>\n"
                    + "	<section>\n"
                    + "		<p> \n"
                    + "		    <input id=\"user\" type=\"text\" placeholder=\"Nombre de usuario\"/>"
                    + "		</p>\n"
                    + "		<h4 style=\"font-family:Comic Sans MS\">Timeline</h4>\n");
            List<Status> tweets = twitter.getUserTimeline();
            tweets.stream().forEach((tweet) -> {
                out.println("		<div class=\"tweet\">\n"
                        + "			<div class=\"info\">\n"
                        + "				<p class=\"user\">\n"
                        + "					<span class=\"name\">" + tweet.getUser().getName() + "</span>\n"
                        + "					<span class=\"username\">" + tweet.getUser().getScreenName() + "</span>\n"
                        + "					<span class=\"date\">" + tweet.getCreatedAt() + "</span>\n"
                        + "				</p>\n"
                        + "				<p class=\"text\">" + tweet.getText() + "</p>\n"
                        + "			</div>\n"
                        + "		</div>\n");
            });
            out.println(
                    "	</section>\n"
                    + "	<footer>\n"
                    + "			<a href = \"https://www.facebook.com/\">Desarrolladores</a>\n"
                    + "	</footer></body>");
            out.println("</html>");
          
        } catch (Exception e) {
            CookieConsumer.handleException(e, request, response, consumer);
        }
    }

    private static final long serialVersionUID = 1L;
}
