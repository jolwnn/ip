import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String filePath = "./data/duke.txt";
        Storage storage = new Storage(filePath);
        List<Task> tasks;

        try {
            tasks = storage.load();
            if (!tasks.isEmpty()) {
                System.out.println("Memory restored.");
            }
        } catch (IOException e) {
            System.out.println("Error encountered while loading previous tasks: " + e.getMessage());
            tasks = new ArrayList<>();
        }

        String greeting = "   *        *        *        __o    *       *\n"
                + "*      *       *        *    /_| _     *\n"
                + "  FF  *    FF      *        O'_)/ \\  *    *\n"
                + "  o')____  o')____    __*   V   \\  ) __  *\n"
                + "   \\ ___ )--\\ ___ )--( (    (___|__)/ /*     *\n"
                + " *  |   |    |   |  * \\ \\____| |___/ /  *\n"
                + "    |*  |    |   |     \\____________/       *\n";
        String festiveMessage =
                "Merry★* 。 • ˚ ˚ ˛ ˚ ˛ •\n" +
                        "•。★Christmas★ 。* 。\n" +
                        "° 。 ° ˛˚˛ * Π__。˚\n" +
                        "˚ ˛ •˛•˚ */__/~＼。˚ ˚ ˛\n" +
                        "˚ ˛ •˛• ˚ ｜ 田田 ｜門｜ ˚\n" +
                        "And a happy new year!";
        System.out.println(greeting + "Hello, I am Rudolf, Santa's trusty red-nose reindeer");
        System.out.println("Christmas is nearing, and I am here to help you with your christmas preparations.");
        System.out.println("Here's how you can chat with me:");
        System.out.println("1. Add a ToDo: todo <description>");
        System.out.println("2. Add a Deadline: deadline <description> /by <date/time>");
        System.out.println("3. Add an Event: event <description> /from <start date/time> /to <end date/time>");
        System.out.println("4. List all tasks: list");
        System.out.println("5. Mark a task as done: mark <task number>");
        System.out.println("6. Unmark a task: unmark <task number>");
        System.out.println("7. Delete a task: delete <task number>");
        System.out.println("8. Exit: bye");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        String input = "";
        boolean isChanged = false;

        while (!input.equals("bye")) {
            input = scanner.nextLine();
            System.out.println("____________________________________________________________");

            if (input.equals("list")) {
                isChanged = false;
                if (tasks.isEmpty()) {
                    System.out.println("Ho Ho Ho! No tasks in your list yet. Add some tasks to get started.");
                } else {
                    System.out.println("Ho Ho Ho! Here are the tasks in your Christmas list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                }
            } else if (input.startsWith("mark")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsDone();
                        isChanged = true;
                        System.out.println("Sleigh! I've marked this task as done:");
                        System.out.println("  " + tasks.get(index));
                    } else {
                        System.out.println("Oops! That task number doesn't exist. Please try again.");
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Sorry, I don't understand. Did you mean: mark <task number>");
                }
            } else if (input.startsWith("unmark")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsNotDone();
                        isChanged = true;
                        System.out.println("Alright-o, I've marked this task as not done yet:");
                        System.out.println("  " + tasks.get(index));
                    } else {
                        System.out.println("Oops! That task number doesn't exist. Please try again.");
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Sorry, I don't understand. Did you mean: unmark <task number>");
                }
            } else if (input.startsWith("todo")) {
                if (input.length() <= 5) {
                    System.out.println("Oops! The description of a todo cannot be empty.");
                } else {
                    String description = input.substring(5).trim();
                    tasks.add(new ToDo(description));
                    isChanged = true;
                    if (description.isEmpty()) {
                        System.out.println("Oops! The description of a todo cannot be empty.");
                    } else {
                        System.out.println("Gotcha. I've added this task:");
                        System.out.println("  " + tasks.get(tasks.size() - 1));
                        System.out.println("Now you have " + tasks.size() + " tasks in the list. Jingle bells!");
                    }
                }
            } else if (input.startsWith("deadline")) {
                String[] substrings = input.split(" /by ");
                if (substrings.length == 2) {
                    if (substrings[0].length() <= 9) {
                        System.out.println("Oops! The description of a deadline cannot be empty.");
                    } else {
                        String description = substrings[0].substring(9).trim();
                        String by = substrings[1].trim();
                        if (description.isEmpty()) {
                            System.out.println("Oops! The description of a deadline cannot be empty.");
                        } else {
                            try {
                                tasks.add(new Deadline(description, by));
                                isChanged = true;
                                System.out.println("Gotcha. I've added this task:");
                                System.out.println("  " + tasks.get(tasks.size() - 1));
                                System.out.println("Now you have " + tasks.size() + " tasks in the list. Let it snow!");
                            } catch (DateTimeParseException e) {
                                System.out.println("Please enter the date and time in the format yyyy-MM-dd HHmm.");
                            }
                        }
                    }
                } else {
                    System.out.println("Sorry, I don't understand. Did you mean: deadline <description> /by <date/time>");
                }
            } else if (input.startsWith("event")) {
                String[] substrings = input.split(" /from ");
                if (substrings.length == 2) {
                    if (substrings[0].length() <= 6) {
                        System.out.println("Oops! The description of an event cannot be empty.");
                    } else {
                        String description = substrings[0].substring(6).trim();
                        String[] fromTo = substrings[1].split(" /to ");
                        if (fromTo.length == 2) {
                            String from = fromTo[0].trim();
                            String to = fromTo[1].trim();
                            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                                System.out.println("Oops! The description, start time, or end time of an event cannot be empty.");
                            } else {
                                try {
                                    tasks.add(new Event(description, from, to));
                                    isChanged = true;
                                    System.out.println("Gotcha. I've added this task:");
                                    System.out.println("  " + tasks.get(tasks.size() - 1));
                                    System.out.println("Now you have " + tasks.size() + " tasks in the list. Feliz Navidad!");
                                } catch (DateTimeParseException e) {
                                    System.out.println("Please enter the date and time in the format yyyy-MM-dd HHmm.");
                                }
                            }
                        } else {
                            System.out.println("Sorry, I don't understand. Did you mean: event <description> /from <start date/time> /to <end date/time>");
                        }
                    }
                } else {
                    System.out.println("Sorry, I don't understand. Did you mean: event <description> /from <start date/time> /to <end date/time>");
                }
            } else if (input.startsWith("delete")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task removedTask = tasks.remove(index);
                        isChanged = true;
                        System.out.println("Aww okay. I've removed this task:");
                        System.out.println("  " + removedTask);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    } else {
                        System.out.println("Oops! That task number doesn't exist. Please try again.");
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Sorry, I don't understand. Did you mean: delete <task number>");
                }
            } else if (input.equals("bye")) {
                isChanged = false;
                System.out.println("Bye~ Hope to see you again!\n" + festiveMessage);
            } else {
                isChanged = false;
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
            if (isChanged) {
                try {
                    storage.save(tasks);
                    isChanged = false;
                } catch (IOException e) {
                    System.out.println("Oh no! It seems like an error was encountered while saving tasks: " + e.getMessage());
                }
            }

            System.out.println("____________________________________________________________");
        }
    }
}
