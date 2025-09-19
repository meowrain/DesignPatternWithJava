package org.designpattern.calc.expression;

public class BinaryOperatorExpression implements Expression{
    Expression left;
    Expression right;
    protected BinaryOperatorExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public int getValue() {
        return 0;
    }
}
