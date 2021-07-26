package contacts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    static Path path;

    public static void main(String[] args) {
        Contacts contacts = null;

        if (args.length==0) {
            System.out.println("Won't work with files");
            contacts = new Contacts();
        } else {
            path = Paths.get("C:", "testordner", args[0]);
            if (Files.isRegularFile(path)) {
                System.out.println("Open " + path);
                contacts = new Contacts(path);
                contacts.setPath(path);
            }
            if (Files.notExists(path)) {
                try {
                    Files.createFile(path);
                    System.out.println("Created a file " + path);
                    contacts = new Contacts();
                    contacts.setPath(path);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Couldn't create File " + path);
                }
            }
        }
        menu(contacts);
    }

    public static void menu(Contacts contacts) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("[menu] Enter action (add, list, search, count, exit): ");
            String input = scanner.nextLine();
            switch (input.toLowerCase(Locale.ROOT)) {
                case "add":
                    System.out.println("Enter the type (person, organization):");
                    input = scanner.nextLine().toLowerCase(Locale.ROOT);
                    if ("person".equals(input)) {
                        contacts.addRecord(Person.addContact());
                        break;
                    }
                    if ("organization".equals(input)) {
                        contacts.addRecord(Organization.addContact());
                        break;
                    }
                case "remove":
                    contacts.removeRecord();
                    break;
                case "edit":
                    contacts.editRecord();
                    break;
                case "count":
                    contacts.countRecord();
                    break;
                case "list":
                    contacts.listRecords();
                    contacts.info();
                    break;
                case "exit":
                    exit();
                    break;
                case "search":
                    contacts.searchRecord();
                    break;
                default:
                    System.out.println("Could not match your choice");
                    break;
            }
        }
    }

    private static void exit() {
        System.exit(0);
    }
}
