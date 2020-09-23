package sample.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String args[]) {
        // DIコンテナを構築し、Configurationファイルを渡す
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            // Beanを取得

            // SpringPerson person = context.getBean(SpringPerson.class, "bbbbb");
            // System.out.println(person.getGreeting());
        }

    }
}