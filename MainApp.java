import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserServiceClass service = new UserServiceClass();
        UserClass loggedInUser = null;

        while (loggedInUser == null) {
            System.out.println("\n=== APPOINTMENT SYSTEM ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    System.out.print("Create password: ");
                    String password = sc.nextLine();

                    service.register(name, email, password);
                    break;

                case "2":
                    System.out.print("Enter email: ");
                    String loginEmail = sc.nextLine();

                    System.out.print("Enter password: ");
                    String loginPassword = sc.nextLine();

                    loggedInUser =
                        service.login(loginEmail, loginPassword);
                    break;

                case "3":
                    System.out.println("Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        System.out.println("Welcome, " + loggedInUser.getName() + "!");

        boolean running = true;
        DateTimeFormatter format =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        while (running) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Add Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. Check Reminders");
            System.out.println("5. Logout");
            System.out.print("Choose: ");

            String choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Appointment title: ");
                        String title = sc.nextLine();

                        System.out.print(
                            "Date and time (dd-MM-yyyy HH:mm): "
                        );
                        LocalDateTime dateTime =
                            LocalDateTime.parse(sc.nextLine(), format);

                        service.addAppointment(title, dateTime);
                        break;

                    case "2":
                        service.viewAppointments();
                        break;

                    case "3":
                        System.out.print("Appointment ID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        service.cancelAppointment(id);
                        break;

                    case "4":
                        service.checkReminders();
                        break;

                    case "5":
                        running = false;
                        loggedInUser = null;
                        System.out.println("Logged out successfully.");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid ID.");
            }
        }

        sc.close();
    }
}