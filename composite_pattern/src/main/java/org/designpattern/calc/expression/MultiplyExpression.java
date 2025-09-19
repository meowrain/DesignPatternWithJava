package org.designpattern.calc.expression;

public class MultiplyExpression extends BinaryOperatorExpression {

    public MultiplyExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int getValue() {
        return left.getValue() * right.getValue();
    }
}