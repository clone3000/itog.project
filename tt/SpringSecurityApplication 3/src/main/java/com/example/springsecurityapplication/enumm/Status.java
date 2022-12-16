package com.example.springsecurityapplication.enumm;

public enum Status {
    Принят, Оформлен, Ожидает, Получен, Отменен, undefined;

    public static Status valueOf(int num){
        switch (num){
            case 1: return Принят;
            case 2: return Оформлен;
            case 3: return Ожидает;
            case 4: return Получен;
            case 5: return Отменен;
            default: return undefined;
        }
    }
}
