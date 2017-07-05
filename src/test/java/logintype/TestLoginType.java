package logintype;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * @author malex
 */
public class TestLoginType {

    @Test
    public void test() {

        //success
        assertTrue(assertLoginType("EMAIL", "GOOGLE"));
        assertTrue(assertLoginType("EMAIL", "FACEBOOK"));
        assertTrue(assertLoginType("GOOGLE", "EMAIL"));
        assertTrue(assertLoginType("FACEBOOK", "EMAIL"));


        //error
        assertFalse(assertLoginType("FACEBOOK", "EMAIL", "FACEBOOK"));
        assertFalse(assertLoginType("FACEBOOK", "FACEBOOK"));
        assertFalse(assertLoginType("FACEBOOK", "EMAIL", "GOOGLE"));
        assertFalse(assertLoginType("FACEBOOK", "FACEBOOK"));

        assertFalse(assertLoginType("EMAIL", "EMAIL", "GOOGLE"));
        assertFalse(assertLoginType("EMAIL", "EMAIL", "FACEBOOK"));

        assertFalse(assertLoginType("GOOGLE", "EMAIL", "GOOGLE"));
        assertFalse(assertLoginType("GOOGLE", "EMAIL", "FACEBOOK"));
        assertFalse(assertLoginType("GOOGLE", "FACEBOOK"));
        assertFalse(assertLoginType("GOOGLE", "GOOGLE"));
        assertFalse(assertLoginType("GOOGLE", "GOOGLE", "EMAIL"));

    }

    // List of excluding social networks
    private List<String> listLoginType = Arrays.asList("GOOGLE", "FACEBOOK");

    // class fo r a entity
    class LoginType {

        private String loginType;

        LoginType(String loginType) {
            this.loginType = loginType;
        }

        String getLoginMode() {
            return loginType;
        }
    }

    private List<LoginType> convertArrayToList(String... loginType) {

        List<LoginType> result = new ArrayList<>();

        for (String type : loginType) {
            result.add(new LoginType(type));
        }

        return result;
    }


    private boolean assertLoginType(String loginMode, String... loginTypeList) {

        List<LoginType> loginTypes = convertArrayToList(loginTypeList);

        // Number of social networks
        int numberSocialNetwork = 0;

        // check new loginType
        if(listLoginType.contains(loginMode)){
            numberSocialNetwork++;
        }

        // check user's loginType
        for (LoginType loginType : loginTypes) {

            // exclude repeat
            if (loginType.getLoginMode().equals(loginMode)) {
                // exception
                return false;
            }

            // Check current login type with exception types
            if (listLoginType.contains(loginType.getLoginMode())) {
                numberSocialNetwork++;
            }
        }

        // Exclude accounts with only a few networks
        if (numberSocialNetwork > 1) {
            // exception
            System.out.println("You already have a registered account using one of the social networks Facebook or GPLUS");
            return false;
        }

        return true;
    }

}