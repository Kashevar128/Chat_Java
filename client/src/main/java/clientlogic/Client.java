package clientlogic;

import gui.ClientGuiController;
import ru.net.network.*;

import java.io.IOException;

public class Client implements TCPConnectionListener { // делаем наследоваие от JFrame и осуществляем интерфейсы ActionListener и TCPConnectionListener

    private static final String IP_ADDR = "192.168.0.104";// 192.168.0.104 172.22.34.61- доп. IP // Переменная c IP машины
    private static final int PORT = 8189; // Переменная с портом

    private ClientGuiController controller;
    private TCPConnection connection; // Поле для экземпляра канала
    private String loginUser;
    private ID id_user;

    public Client(ClientGuiController controller, String login) throws IOException {
        this.controller = controller;
        loginUser = login;

        try { // Блок для обхода исключений
            connection = new TCPConnection(this, IP_ADDR, PORT); // Создаем TCP - соединение
        } catch (IOException e) {
            e.printStackTrace();
        }
        id_user = new ID(connection.getSocket().getLocalAddress().toString(), connection.getSocket().getLocalPort());
    }

    public TCPConnection getConnection() {
        return connection;
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) { // Расписываем интерфейсы для работы со стороны клиента, методы синхронизировать не надо, т.к. с ними работаем только сам клиент
        System.out.println("Connection ready...");
    }

    @Override
    public void onReceivePackage(TCPConnection tcpConnection, Message msg) {
        controller.print(msg);
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
        Message pack = new Message(msg, loginUser, TypeMessage.VERBAL_MESSAGE, id_user);
        connection.sendMessage(pack);
    }

}
