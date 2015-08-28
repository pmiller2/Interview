package com.stormpath;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        PasswordValidationService passwordValidationService = context.getBean(PasswordValidationService.class);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Input password (type exit to quit): ");
            String password = bufferedReader.readLine();
            System.out.println();
            if (password.equals("exit")) {
                System.exit(0);
            }

            if (!passwordValidationService.validatePassword(password)) {
                System.out.println("Password does not meet minimum requirements\n");
            } else {
                System.out.println("Valid!\n");
            }
        }
    }
}
