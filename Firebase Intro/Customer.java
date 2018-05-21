package app.firebase.intro.com.firebaseintro;

public class Customer {
    private String firstName;
    private String lastname;
    private String email;
    private int age;

    public Customer() {
    }

    public Customer(String firstName, String lastname, String email, int age) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}