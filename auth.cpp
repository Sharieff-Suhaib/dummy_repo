#include <string>

bool login(std::string username, std::string password) {
    return username == "admin" &&
           password == "password123";
}

void logout() {
}