package org.example.window;

import org.example.robot.RobotMap;

import javax.swing.*;

/**
 * Класс RobotFrame - фрейм для отображения панели с роботом.
 */
public class RobotFrame extends JFrame {
    private RobotMap robotMap; // Объект карты робота

    /**
     * Конструктор класса RobotFrame.
     *
     * @param robotPanel Панель, содержащая робота и карту.
     */
    public RobotFrame(RobotPanel robotPanel) {
        // Создание объекта карты робота
        robotMap = new RobotMap();

        // Установка размеров фрейма равными размерам карты
        setSize(robotMap.getWidth(), robotMap.getHeight());

        // Установка операции по закрытию фрейма
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Размещение фрейма по центру экрана
        setLocationRelativeTo(null);

        // Запрет на изменение размеров фрейма
        setResizable(false);

        // Установка заголовка фрейма
        setTitle("MyRobot");

        // Добавление панели с роботом на фрейм
        add(robotPanel);

        // Автоматическое упаковывание компонентов фрейма
        pack();

        // Установка видимости фрейма
        setVisible(true);
    }
}