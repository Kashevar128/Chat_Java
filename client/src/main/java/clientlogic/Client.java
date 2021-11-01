package clientlogic;

import gui.ClientGuiController;
import ru.net.network.Message;
import ru.net.network.TCPConnection;
import ru.net.network.TCPConnectionListener;

import java.io.IOException;

public class Client implements TCPConnectionListener { // делаем наследоваие от JFrame и осуществляем интерфейсы ActionListener и TCPConnectionListener

    private static final String IP_ADDR = "172.22.34.61";// 192.168.0.104- доп. IP // Переменная c IP машины
    private static final int PORT = 8189; // Переменная с портом

    private ClientGuiController controller;
    private TCPConnection connection; // Поле для экземпляра канала
    private String loginUser;

    public Client(ClientGuiController controller, String login) throws IOException {
        this.controller = controller;
        loginUser = login;
        try { // Блок для обхода исключений
            connection = new TCPConnection(this, IP_ADDR, PORT); // Создаем TCP - соединение
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        String localIp = tcpConnection.getSocket().getLocalAddress().toString();
        int localPort = tcpConnection.getSocket().getLocalPort();
        Message pack = new Message(msg, loginUser, localIp, localPort);
        connection.sendMessage(pack);
        System.out.println(localIp);
        System.out.println(localPort);
    }

}
