public class Auth {

    public static boolean login(String username, String password) {
        return username.equals("admin") &&
               password.equals("password123");
    }

    public static void logout() {
        System.out.println("User logged out");
    }
}