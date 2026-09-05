#include <iostream>
#include "auth.cpp"

int main() {
    if (login("admin", "password123")) {
        std::cout << "Login successful\n";
    } else {
        std::cout << "Login failed\n";
    }

    return 0;
}