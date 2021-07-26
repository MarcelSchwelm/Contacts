package contacts;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Contacts implements Serializable {
    private List<Contact> contacts;
    private List<Contact> hits;
    private Contact access;
    private Path path;

    public Contacts() {
        this.contacts = new ArrayList<>();
    }

    public Contacts(Path path) {
        this.contacts = loadContacts(path);
    }

    public List<Contact> loadContacts(Path path) {
        BufferedInputStream bis;
        try (FileInputStream fis = new FileInputStream(String.valueOf(path))) {
            bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            contacts = (ArrayList<Contact>) ois.readObject();
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Couldn't load contacts");
        }
        return contacts;
    }

    public void saveContacs(Path path) {
        BufferedOutputStream bos;
        try (FileOutputStream fos = new FileOutputStream(String.valueOf(path))) {
            bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(contacts);
            System.out.println("Contact saved!");
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't save contacts");
        }
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void addRecord(Contact contact) {

        contacts.add(contact);
        if (path != null) {
            saveContacs(path);
        }
    }

    public void removeRecord() {
        Scanner scanner = new Scanner(System.in);
        if (contacts.size() == 0) {
            System.out.println("No records to remove!");
            return;
        }
        listRecords();
        System.out.print("Select a record: ");
        int input = scanner.nextInt();
        if (input > 0 && input <= contacts.size()) {
            contacts.remove(input - 1);
            if (path != null) {
                saveContacs(path);
            }
        } else {
            System.out.println("Wrong input");
        }
    }

    public void editRecord() {
        if (contacts.size() == 0) {
            System.out.println("No records to edit!");
            return;
        }
        listRecords();
        System.out.print("Select a record: ");
        Scanner scanner = new Scanner(System.in);
        int input = Integer.parseInt(scanner.nextLine());
        if (input > 0 && input <= contacts.size()) {
            contacts.get(input - 1).editContact();
            if (path != null) {
                saveContacs(path);
            }
        } else {
            System.out.println("Bad input");
        }
        System.out.println();
    }

    public void countRecord() {
        System.out.printf("The Phone Book has %d records.%n", contacts.size());
    }

    public void listRecords() {
        if (contacts.size() == 0) {
            System.out.println("No records to list!");
            return;
        }
        int listCounter = 0;
        for (Contact contact : contacts) {
            listCounter++;
            System.out.printf("%d. %s %n", listCounter, contact.listContact());
        }
    }

    public void info() {
        System.out.println("\n[list] Enter action ([number], back): ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if ("back".equals(input)) {
            Main.menu(this);
        } else {
            try {
                int number = Integer.parseInt(input);
                access = contacts.get(number - 1);
                access.getInfo();
                record();

            } catch (InputMismatchException e) {
                System.out.println("Bad index");
            }
        }
    }

    public void searchRecord() {
        hits = new ArrayList<>();
        int number;
        System.out.println("Enter search query: ");
        Scanner scanner = new Scanner(System.in);
        String query = scanner.nextLine();
        query = query.toLowerCase();
        for (Contact contact : contacts) {
            if (contact.getClass() == Person.class) {
                if (((Person) contact).getName().toLowerCase().contains(query) ||
                        ((Person) contact).getSurname().toLowerCase().contains(query) ||
                        (contact.getNumber().toLowerCase().contains(query))) {
                    hits.add(contact);
                }
            }
            if (contact.getClass() == Organization.class) {
                if (((Organization) contact).getName().toLowerCase().contains(query) ||
                        (contact.getNumber().toLowerCase().contains(query))) {
                    hits.add(contact);
                }
            }
        }
        System.out.printf("Found %d results:%n", hits.size());
        int listCounter = 0;
        for (Contact contact : hits) {
            listCounter++;
            System.out.printf("%d. %s %n", listCounter, contact.listContact());
        }
        System.out.println("\n[search] Enter action ([number], back, again): ");
        String input = scanner.nextLine();
        if ("again".equals(input)) {
            searchRecord();
        }
        if ("back".equals(input)) {
            Main.menu(this);
        } else {
            try {
                number = Integer.parseInt(input);
                access = hits.get(number - 1);
                record();

            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.println("Could not find the number: " + input);
            }
        }
    }

    private void record() {
        access.getInfo();
        System.out.println("\n[record] Enter action (edit, delete, menu):");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if ("menu".equals(input)) {
            Main.menu(this);
        }
        if ("delete".equals(input)) {
            System.out.println(contacts.remove(access) ? "Contact deleted" : "Contact not found");
            if (path != null) {
                saveContacs(path);
            }
        }
        if ("edit".equals(input)) {
            access.editContact();
            if (path != null) {
                saveContacs(path);
            }
        }
    }
}