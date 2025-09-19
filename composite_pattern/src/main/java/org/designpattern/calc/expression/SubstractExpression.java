package org.designpattern.calc.expression;

public class SubstractExpression extends BinaryOperatorExpression {

    public SubstractExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int getValue() {
        return left.getValue() - right.getValue();
    }
}