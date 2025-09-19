package org.designpattern;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileName = "demo.user";
        File file = new File(fileName);
        UserFile users = new UserFile(file);
        for (User user : users) {
            System.out.println(user);
        }



    }
}
