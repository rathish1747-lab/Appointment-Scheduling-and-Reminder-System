import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class Appointment {
    int id;
    String title;
    LocalDateTime dateTime;
    boolean cancelled = false;

    Appointment(int id, String title, LocalDateTime dateTime) {
        this.id = id;
        this.title = title;
        this.dateTime = dateTime;
    }
}

public class UserServiceClass {

    private final List<UserClass> users = new ArrayList<>();
    private final List<Appointment> appointments = new ArrayList<>();

    private int nextUserId = 1;
    private int nextAppointmentId = 1;

    public boolean register(String name, String email, String password) {
        if (name.isBlank() || email.isBlank()) {
            System.out.println("Name and email are required.");
            return false;
        }

        for (UserClass user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Email already registered.");
                return false;
            }
        }

        if (!PasswordUtilClass.validatePassword(password)) {
            System.out.println("Password does not meet requirements.");
            return false;
        }

        users.add(new UserClass(nextUserId++, name, email, password));
        System.out.println("Registration successful!");
        return true;
    }

    public UserClass login(String email, String password) {
        for (UserClass user : users) {
            if (user.getEmail().equalsIgnoreCase(email)
                    && user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                return user;
            }
        }

        System.out.println("Invalid email or password.");
        return null;
    }

    public void addAppointment(String title, LocalDateTime dateTime) {
        if (title.isBlank() || !dateTime.isAfter(LocalDateTime.now())) {
            System.out.println("Enter a title and a future date/time.");
            return;
        }

        appointments.add(
            new Appointment(nextAppointmentId++, title, dateTime)
        );
        System.out.println("Appointment added successfully!");
    }

    public void viewAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
            return;
        }

        for (Appointment a : appointments) {
            System.out.println(
                "ID: " + a.id
                + " | Title: " + a.title
                + " | Date: " + a.dateTime
                + " | Status: " + (a.cancelled ? "Cancelled" : "Scheduled")
            );
        }
    }

    public void cancelAppointment(int id) {
        for (Appointment a : appointments) {
            if (a.id == id && !a.cancelled) {
                a.cancelled = true;
                System.out.println("Appointment cancelled.");
                return;
            }
        }

        System.out.println("Appointment not found or already cancelled.");
    }

    public void checkReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime limit = now.plusMinutes(60);
        boolean found = false;

        for (Appointment a : appointments) {
            if (!a.cancelled
                    && !a.dateTime.isBefore(now)
                    && !a.dateTime.isAfter(limit)) {
                System.out.println(
                    "REMINDER: " + a.title + " at " + a.dateTime
                );
                found = true;
            }
        }

        if (!found) {
            System.out.println("No appointments in the next 60 minutes.");
        }
    }
}