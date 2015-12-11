package WebApp;

import net.oauth.OAuthException;

public class RedirectException extends OAuthException {

    public RedirectException(String targetURL) {
        super(targetURL);
    }

    public String getTargetURL() {
        return getMessage();
    }

    private static final long serialVersionUID = 1L;

}
