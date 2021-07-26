package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;

abstract class Contact implements Serializable {
    protected String number = "";
    protected LocalDateTime timeCreated;
    protected LocalDateTime timeLastEdit;

    abstract void editContact();

    abstract String listContact();

    abstract void getInfo();

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeLastEdit() {
        return timeLastEdit;
    }

    public void setTimeLastEdit(LocalDateTime timeLastEdit) {
        this.timeLastEdit = timeLastEdit;
    }

    public String getNumber() {
        if ("".equals(number) || number == null) {
            return "[no data]";
        } else {
            return number;
        }
    }

    public void setNumber(String number) {
        if (checkNumber(number)) {
            this.number = number;
        } else {
            this.number = "";
            System.out.println("Wrong number format!");
        }
    }

    public boolean hasNumber() {
        return (!"".equals(number));
    }

    protected boolean checkNumber(String number) {
        String[] split = number.split("[\\s-]");
        int groupCounter = 0;
        boolean brackets = false;
        for (String string : split) {
            groupCounter++;
            if (string.equals("()")) {
                return false;
            }
            if (groupCounter == 1) {
                if (!string.matches("[+()]?[a-zA-Z0-9()]*")) {
                    return false;
                }
                if (string.contains("(") || string.contains(")")) {
                    brackets = true;
                }
            }
            if (groupCounter == 2 && !brackets) {
                if (!string.matches("[a-zA-Z0-9()]{2,}")) {
                    return false;
                }
            }
            if (groupCounter == 2 && brackets) {
                if (!string.matches("[a-zA-Z0-9]{2,}")) {
                    return false;
                }
            }
            if (groupCounter > 2) {
                if (!string.matches("[a-zA-Z0-9]{2,}")) {
                    return false;
                }
            }
        }
        return true;
    }
}
