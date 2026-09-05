def login(username, password):
    return username == "admin" and password == "password123"


def logout():
    print("User logged out")