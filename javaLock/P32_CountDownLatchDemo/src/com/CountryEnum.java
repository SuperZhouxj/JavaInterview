package com;

public enum CountryEnum {
    ONE(1,"齐"),
    TWO(2,"楚"),
    THREE(3,"燕"),
    FOUR(4,"赵"),
    FIVE(5,"韩"),
    SIX(6,"魏");

    private Integer code;
    private String message;

    CountryEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode(){
        return code;
    }
    public  String getMessage(){
        return message;
    }

    public static CountryEnum check(Integer index){
        for (CountryEnum element : CountryEnum.values()) {
           if (element.getCode() == index){
               return element;
           }
        }
        return null;
    }

}
