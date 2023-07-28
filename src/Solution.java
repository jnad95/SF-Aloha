import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Solution {

    public static void main(String args[]) throws Exception {
        Scanner in = new Scanner(System.in);
        while (true) {
            String commandStr = in.nextLine();
            Map<String, List<String>> input = inputProcessor.PROCESS(commandStr);
            Command command = CommandFactory.getCommand(input.get("COMMAND").get(0));
            command.execute(input.get("OPTIONS"), input.get("PARAMS"));
        }
    }
}


class CommandFactory {

    static Command getCommand(String commandStr) {
        if (Objects.equals(commandStr, "pwd")) {
            return new PWD();
        } else if (Objects.equals(commandStr, "ls")) {
            return new Ls();
        } else if (Objects.equals(commandStr, "mkdir")) {
            return new MkDir();
        } else if (Objects.equals(commandStr, "cd")) {
            return new CD();
        } else if (Objects.equals(commandStr, "touch")) {
            return new Touch();
        } else if (Objects.equals(commandStr, "quit")) {
            return new Quit();
        }
        return null;
    }
}

class inputProcessor {
    static Map<String, List<String>> PROCESS(String commandStr) {
        Map<String, List<String>> inp = new HashMap<>();
        String[] keys = commandStr.split(" ");
        inp.put("COMMAND", List.of(keys[0]));
        List<String> options = new ArrayList<String>();
        List<String> params = new ArrayList<String>();
        for (int i = 1; i < keys.length; i++) {
            if (keys[i].startsWith("-")) {
                options.add(keys[i]);
            } else {
                params.add(keys[i]);
            }
        }
        inp.put("OPTIONS", options);
        inp.put("PARAMS", params);
        return inp;
    }
}

interface Command {

    void execute(List<String> options, List<String> params);
}

/**
 * command quit
 */
class Quit implements Command {

    @Override
    public void execute(List<String> options, List<String> params) {
        System.out.println("Executing Quit command");
        System.exit(1);
    }
}

/**
 * pwd
 */
class PWD implements Command {

    @Override
    public void execute(List<String> options, List<String> params) {
        System.out.println("Executing PWD command");
        System.out.println(System.getProperty("user.dir"));
    }
}

/**
 * ls
 */
class Ls implements Command {

    @Override
    public void execute(List<String> options, List<String> params) {
        System.out.println("Executing Ls command");
        Path dir = Paths.get(System.getProperty("user.dir"));

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.*")) {
            for (Path file : stream) {
                System.out.println(file.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * mkdir
 */
class MkDir implements Command {

    @Override
    public void execute(List<String> options, List<String> params) {
        System.out.println("Executing MkDir command");
        File f = new File(params.get(0));
        if (f.mkdir()) {
            System.out.println("file has been created");
        } else {
            System.out.println("File couldnt be created");
        }
    }
}

/**
 * cd
 */
class CD implements Command {

    @Override
    public void execute(List<String> options, List<String> params) {
        System.out.println("Executing CD command");
    }
}

/**
 * touch
 */
class Touch implements Command {

    @Override
    public void execute(List<String> options, List<String> params) {
        System.out.println("Executing Touch command");
    }
}
