package clientlogic;

import gui.Avatar;
import gui.ClientGuiController;
import ru.net.network.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client implements TCPConnectionListener { // делаем наследоваие от JFrame и осуществляем интерфейсы ActionListener и TCPConnectionListener

    private static String IP_ADDR = null;// 192.168.0.104 172.22.34.61- доп. IP // Переменная c IP машины
    private ArrayList<ClientProfile> usersList = null;

    static {
        try {
            IP_ADDR = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static final int PORT = 8189; // Переменная с портом

    private ClientGuiController controller;
    private TCPConnection connection; // Поле для экземпляра канала
    private String loginUser;
    private ClientProfile myClientProfile;

    public ClientProfile getMyClientProfile() {
        return myClientProfile;
    }

    public Client(ClientGuiController controller, String name) throws IOException {
        this.controller = controller;
        loginUser = name;
        controller.name.setText(name);

        try { // Блок для обхода исключений
            connection = new TCPConnection(IP_ADDR, PORT, this); // Создаем TCP - соединение
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(IP_ADDR);
        this.myClientProfile = new ClientProfile(loginUser, Avatar.createAvatar(loginUser));
    }

    public TCPConnection getConnection() {
        return connection;
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) { // Расписываем интерфейсы для работы со стороны клиента, методы синхронизировать не надо, т.к. с ними работаем только сам клиент
        System.out.println("Connection ready...");
        Message pack = new Message(loginUser,null, TypeMessage.SERVICE_MESSAGE_ADD_NAME);
        connection.sendMessage(pack);
    }

    @Override
    public void onReceivePackage(TCPConnection tcpConnection, Message msg) {
        messageHandler(msg, msg.getTypeMessage());
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        System.out.println("Connection " + tcpConnection + " close");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("Server not found");
    }

    @Override
    public void onSendPackage(TCPConnection tcpConnection, String msg) {
        Message pack = new Message(msg, myClientProfile);
        connection.sendMessage(pack);
    }

    @Override
    public void messageHandler(Message msg, TypeMessage typeMessage) {
        switch (typeMessage) {
            case VERBAL_MESSAGE:
                controller.print(msg);
                break;
            case SERVICE_MESSAGE_UPDATE_LIST_USERS:
                usersList = (ArrayList<ClientProfile>) msg.getObjT();
                controller.printListUsers(getUsersList());
        }
    }

    public String getLoginUser() {
        return loginUser;
    }

    public ArrayList<ClientProfile> getUsersList() {
        return usersList;
    }
}
