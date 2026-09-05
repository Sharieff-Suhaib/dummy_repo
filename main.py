from auth import login


def main():
    username = "admin"
    password = "password123"

    if login(username, password):
        print("Login successful")
    else:
        print("Login failed")


if __name__ == "__main__":
    main()