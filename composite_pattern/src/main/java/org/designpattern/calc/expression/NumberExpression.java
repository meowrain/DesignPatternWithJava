package org.designpattern.calc.expression;

public class NumberExpression implements Expression{
    private int value;
    public NumberExpression(int value){
        this.value = value;
    }
    @Override
    public int getValue() {
        return this.value;
    }
}
