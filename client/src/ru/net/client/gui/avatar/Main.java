package ru.net.client.gui.avatar;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String name = "выаыаывфымвывыфммывмфмывмвыфвмвмывмвывмфывмвмфывмвымвыф";
        String avatar = null; //Строковая переменная с аватаром
        try {
            avatar = AvatarHelper.createBase64Avatar(Math.abs(name.hashCode())); // извлекаем из имени профиля hashCode
        } catch (IOException e) {                                                // на случай, если он получился отрицательным
            e.printStackTrace();                                                   // делаем его по модулю.
        }
        System.out.println(AvatarHelper.BASE64_PREFIX + avatar);
    }
}
