package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Scanner;

public class Organization extends Contact implements Serializable {
    private String name;
    private String address;


    public Organization(String name, String address, String number) {
        setName(name);
        setAddress(address);
        setTimeCreated(LocalDateTime.now());
        setTimeLastEdit(LocalDateTime.now());
        setNumber(number);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public static Contact addContact() {
        String name;
        String address;
        String number;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the organization name: ");
        name = scanner.nextLine();
        System.out.println("Enter the address: ");
        address = scanner.nextLine();
        System.out.println("Enter the number: ");
        number = scanner.nextLine();
        Contact organization = new Organization(name, address, number);
        System.out.println("A record created!");
        System.out.println();
        return organization;
    }

    @Override
    void editContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a field (name, address, number): ");
        String choice = scanner.nextLine();
        switch (choice.toLowerCase(Locale.ROOT)) {
            case "name":
                System.out.println("Enter name: ");
                String name = scanner.nextLine();
                setName(name);
                timeLastEdit = LocalDateTime.now();
                break;
            case "address":
                System.out.println("Enter address: ");
                String address = scanner.nextLine();
                setAddress(address);
                timeLastEdit = LocalDateTime.now();
                break;
            case "number":
                System.out.println("Enter number: ");
                String number = scanner.nextLine();
                setNumber(number);
                timeLastEdit = LocalDateTime.now();
                break;
            default:
                System.out.println("Wrong input");
                break;
        }
    }

    @Override
    String listContact() {
        return getName();
    }

    @Override
    void getInfo() {
        System.out.println("Organization name: " + getName());
        System.out.println("Address: " + getAddress());
        System.out.println("Number: " + getNumber());
        System.out.println("Time created: " + getTimeCreated());
        System.out.println("Time last edit: " + getTimeLastEdit());
        System.out.println();
    }
}
