package Servers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import Commands.AddEmployeeCommand;
import Commands.Command;
import Commands.ConfirmPasswordCommand;
import Commands.StartEndShiftCommand;
import Commands.SubmitShiftsCommand;

public class ManageClientsRequestsServer {
    // the members
    private ServerSocket serverSocket;
    private HashMap<String, Command> commandsByKey;
    
    public ManageClientsRequestsServer() {
        commandsByKey = new HashMap<String, Command>();
        commandsByKey.put("AddEmployee", new AddEmployeeCommand());
        commandsByKey.put("SubmitShifts", new SubmitShiftsCommand());
        commandsByKey.put("ConfirmPassword", new ConfirmPasswordCommand());
        commandsByKey.put("StartEndShift", new StartEndShiftCommand());
    }
    
    /*
     * This function manage all the clients requests from the server.
     */
    public void manageClientsRequests() {
        // start the server on new thread
        new Thread(() -> {
            try {
                int port = getPortFromFile("configuration.txt");
                serverSocket = new ServerSocket(port);
                while (true) {
                    Socket socket = serverSocket.accept();
                    // to receive the data from the socket
                    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String msg = bufferedReader.readLine();
                    
                    /*
                     * There are few options to the message format from the client:
                     *     1. "AddEmployee: id#password#first name#last name#age#job type". Message in this
                     *                      format is to add employee.
                     *     2. "SubmitShifs: id#1/0#1/0#1/0#1/0#1/0#1/0#1/0#1/0#1/0#1/0#1/0#1/0#1/0#1/0".
                     *                      Message in this format is to submit shifts, the first shift is
                     *                      Sunday morning, second is for Sunday evening, third is for
                     *                      Monday morning, and so on.
                     *     3. "ConfirmPassword: id#password". Message in this format is to confirm the password if it
                     *                          correct.
                     *     4. "StartEndShift: id#1/0". Message in this format is to start or end shift of that
                     *                        employee.
                     */
                    int endCommandNameIndex = msg.indexOf(':');
                    // if the message is not in the correct format than prints message
                    if (endCommandNameIndex == -1) {
                        System.out.println("The message sent to the server is not in the correct format");
                        continue;
                    }
                    String commandName = msg.substring(0, endCommandNameIndex);
                    // get the data itself
                    String data = msg.substring(commandName.length() + ": ".length(), msg.length());
                    Command command = commandsByKey.get(commandName);
                    // if there is no such command than prints message
                    if (command == null) {
                        System.out.println("There is no such command:   " + commandName);
                        continue;
                    }
                    String response = command.doCommand(data);
                    OutputStream os = socket.getOutputStream();
                    if (response != null) {
                        os.write(response.getBytes());
                    } else {
                        os.write("null".getBytes());
                    }
                    os.flush();
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    /*
     * This function gets the port number from the configuration file.
     */
    public int getPortFromFile(String fileName) {
        File file = new File(fileName);
        try {
            @SuppressWarnings("resource")
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("port")) {
                    return Integer.parseInt(line.split(" ")[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // default port
        return 11111;
    }
}