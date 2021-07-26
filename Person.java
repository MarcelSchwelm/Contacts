package contacts;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

public class Person extends Contact implements Serializable {
    private String name;
    private String surname;
    private LocalDate birthdate;
    private char gender;

    public Person(String name, String surname, String number, LocalDate birthdate, char gender) {
        setName(name);
        setSurname(surname);
        setBirthdate(birthdate);
        setGender(gender);
        setTimeCreated(LocalDateTime.now());
        setTimeLastEdit(LocalDateTime.now());
        setNumber(number);
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public static Contact addContact() {
        String name;
        String surname;
        String number;
        LocalDate birthdate;
        char gender;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name:");
        name = scanner.nextLine();
        System.out.println("Enter the surname:");
        surname = scanner.nextLine();
        System.out.println("Enter the birth date:");
        try {
            birthdate = LocalDate.parse(scanner.nextLine());
        } catch (DateTimeParseException e) {
            System.out.println("Bad birth date");
            birthdate = LocalDate.of(1, 1, 1);
        }
        System.out.println("Enter the gender (M, F):");
        try {
            gender = scanner.nextLine().toUpperCase(Locale.ROOT).charAt(0);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Bad gender!");
            gender = 'N';
        }
        System.out.println("Enter the number:");
        number = scanner.nextLine();
        Contact person = new Person(name, surname, number, birthdate, gender);
        System.out.println("A record created!");
        System.out.println();
        return person;
    }

    @Override
    void editContact() {
        System.out.println("Select a field (name, surname, birth, gender, number): ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        switch (choice.toLowerCase(Locale.ROOT)) {
            case "name":
                System.out.println("Enter name: ");
                String name = scanner.nextLine();
                setName(name);
                timeLastEdit = LocalDateTime.now();
                break;
            case "surname":
                System.out.println("Enter surname: ");
                String surname = scanner.nextLine();
                setSurname(surname);
                timeLastEdit = LocalDateTime.now();
                break;
            case "birth":
                System.out.println("Enter birth: ");
                LocalDate birth = LocalDate.parse(scanner.nextLine());
                setBirthdate(birth);
                timeLastEdit = LocalDateTime.now();
                break;
            case "gender":
                System.out.println("Enter gender: ");
                char gender = scanner.nextLine().toUpperCase(Locale.ROOT).charAt(0);
                setGender(gender);
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
        return getName() + " " + getSurname();
    }

    @Override
    void getInfo() {
        System.out.println("Name: " + getName());
        System.out.println("Surname: " + getSurname());
        if (getBirthdate().equals(LocalDate.of(1, 1, 1))) {
            System.out.println("Birth date: [no data]");
        } else System.out.println("Birth date: " + getBirthdate());
        if (getGender() == 'M' || getGender() == 'F') {
            System.out.println("Gender: " + getGender());
        } else System.out.println("Gender: [no data]");
        System.out.println("Number: " + getNumber());
        System.out.println("Time created: " + getTimeCreated());
        System.out.println("Time last edit: " + getTimeLastEdit());
        System.out.println();
    }
}
