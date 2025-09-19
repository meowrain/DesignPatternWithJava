
好的，我们来详细介绍一下装饰器模式 (Decorator Pattern)。

### 一、什么是装饰器模式？

**核心思想：** **动态地**给一个对象添加一些额外的职责（功能），就增加功能来说，装饰器模式相比生成子类更为灵活。

简单来说，装饰器模式就像给对象“穿衣服”。原始对象是“你”这个人，衣服（比如外套、围巾、帽子）就是“装饰器”。

*   你可以不穿任何衣服（使用原始对象）。
*   你可以只穿一件外套（用一个装饰器包装）。
*   你可以在穿了外套之后再戴上围巾（用另一个装饰器包装已包装好的对象）。
*   每一件“衣服”（装饰器）都增加了新的功能（保暖、美观等），但“你”的核心本质（还是你这个人）没有改变。
*   你可以自由组合这些“衣服”，而且穿脱都很方便（动态添加/撤销功能）。

装饰器模式是一种**结构型设计模式**，它允许你通过将对象放入包含行为的特殊“包装器”对象中来为原对象绑定新的行为。

### 二、为什么需要装饰器模式？

假设我们有一个`TextView`组件，用于显示文本。现在，我们需要为它增加一些功能，比如：
*   带边框的`TextView`
*   带滚动条的`TextView`
*   既有边框又有滚动条的`TextView`
*   先有边框再有滚动条，和先有滚动条再有边框（可能显示效果不同）

如果使用**继承**来实现，会遇到什么问题？

1.  **类爆炸**：
    *   `BorderTextView extends TextView`
    *   `ScrollableTextView extends TextView`
    *   `BorderAndScrollableTextView extends TextView`
    *   `ScrollableAndBorderTextView extends TextView`
    *   如果再来一个“带阴影”的功能，组合会变得更多，类的数量会急剧膨胀，难以维护。

2.  **静态性**：一旦创建了一个 `BorderTextView` 的实例，它就永远是带边框的，你无法在运行时去掉这个边框或给它增加一个滚动条。功能是在编译时就确定了的，不够灵活。

**装饰器模式就是为了解决这些问题而生的。** 它采用**组合（Composition）**而非继承的方式，可以在运行时动态地、透明地给对象添加功能。

### 三、装饰器模式的结构

装饰器模式通常包含以下四个主要角色：

1.  **抽象组件 (Component)**：
    *   定义一个对象的接口，可以给这些对象动态地添加职责。它规范了原始对象和装饰器对象。
    *   在我们的例子中，就是 `TextView` 的一个抽象接口，比如 `VisualComponent`，里面有一个 `draw()` 方法。

2.  **具体组件 (Concrete Component)**：
    *   被装饰的原始对象，它实现了抽象组件接口。
    *   例如，一个只负责显示基本文本的 `BasicTextView` 类。

3.  **抽象装饰器 (Decorator)**：
    *   **它既继承（或实现）了抽象组件接口，又持有一个抽象组件的实例（HAS-A 关系）**。这是装饰器模式的关键！
    *   实现抽象组件接口是为了确保它能“伪装”成被装饰的对象，让客户端可以统一对待。
    *   持有组件实例是为了能够调用被装饰对象（“包裹在里面的那个”）的原始方法。
    *   它的方法实现通常是调用内部组件的同名方法，并在此前后添加自己的新行为。

4.  **具体装饰器 (Concrete Decorator)**：
    *   实现了抽象装饰器，负责向组件添加具体的职责。
    *   例如，`BorderDecorator` (边框装饰器) 和 `ScrollbarDecorator` (滚动条装饰器)。

**结构图如下：**

```
+--------------------+
|     Component      |<-- an operation() method
| (VisualComponent)  |
+--------------------+
        ^
        | inherits
+-------+----------------+
|                        |
+--------------------+   +--------------------+
| ConcreteComponent  |   |     Decorator      |
|  (BasicTextView)   |   | (WidgetDecorator)  |
+--------------------+   +--------------------+
| +operation()       |   | -component: Component |
+--------------------+   +--------------------+
                             ^
                             | inherits
           +-----------------+-----------------+
           |                                   |
+----------------------+             +-------------------------+
| ConcreteDecoratorA   |             |   ConcreteDecoratorB      |
|  (BorderDecorator)   |             | (ScrollbarDecorator)    |
+----------------------+             +-------------------------+
| +operation()         |             | +operation()            |
| +addedBehavior()     |             +-------------------------+
+----------------------+
```

### 四、Java 代码示例

我们用一个更经典的“咖啡”例子来说明。一杯基础咖啡（黑咖啡），可以加牛奶、加糖、加摩卡等。

**1. 定义抽象组件 (Component)**
*所有咖啡（包括加了调料的）的基类。*

```java
// 抽象组件：饮料
public abstract class Beverage {
    String description = "Unknown Beverage";

    public String getDescription() {
        return description;
    }

    public abstract double cost(); // 计算价格
}
```

**2. 创建具体组件 (Concrete Component)**
*基础的、没加任何东西的咖啡。*

```java
// 具体组件：浓缩咖啡
public class Espresso extends Beverage {
    public Espresso() {
        description = "Espresso";
    }

    @Override
    public double cost() {
        return 1.99; // 浓缩咖啡的价格
    }
}

// 具体组件：黑咖啡
public class HouseBlend extends Beverage {
    public HouseBlend() {
        description = "House Blend Coffee";
    }

    @Override
    public double cost() {
        return 0.89; // 黑咖啡的价格
    }
}
```

**3. 创建抽象装饰器 (Decorator)**
*所有调料装饰器的基类。*

```java
// 抽象装饰器：调料
public abstract class CondimentDecorator extends Beverage {
    // 持有一个 Beverage 对象的引用 (被装饰者)
    protected Beverage beverage;

    // 所有的调料装饰器都必须重新实现 getDescription() 方法
    public abstract String getDescription();
}
```

**4. 创建具体装饰器 (Concrete Decorator)**
*具体的调料：牛奶、摩卡。*

```java
// 具体装饰器：牛奶
public class Milk extends CondimentDecorator {
    public Milk(Beverage beverage) {
        this.beverage = beverage; // 把被装饰者传进来
    }

    @Override
    public String getDescription() {
        // 在被装饰者的描述上加上自己的描述
        return beverage.getDescription() + ", Milk";
    }

    @Override
    public double cost() {
        // 价格 = 被装饰者的价格 + 牛奶的价格
        return beverage.cost() + 0.10;
    }
}

// 具体装饰器：摩卡
public class Mocha extends CondimentDecorator {
    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Mocha";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.20;
    }
}
```

**5. 客户端使用**

```java
public class CoffeeShop {
    public static void main(String[] args) {
        // 1. 点一杯最简单的浓缩咖啡，不加任何东西
        Beverage beverage1 = new Espresso();
        System.out.println(beverage1.getDescription() + " $" + beverage1.cost());

        // 2. 点一杯黑咖啡，然后想给它加点料
        Beverage beverage2 = new HouseBlend();
        // 用 Mocha 装饰它
        beverage2 = new Mocha(beverage2);
        // 再用 Milk 装饰它 (注意，现在装饰的是已经加了Mocha的咖啡)
        beverage2 = new Milk(beverage2);
        System.out.println(beverage2.getDescription() + " $" + beverage2.cost());

        // 3. 点一杯浓缩咖啡，加双份摩卡和一份牛奶
        Beverage beverage3 = new Espresso();
        beverage3 = new Mocha(beverage3); // 第一份摩卡
        beverage3 = new Mocha(beverage3); // 第二份摩卡
        beverage3 = new Milk(beverage3);  // 一份牛奶
        System.out.println(beverage3.getDescription() + " $" + String.format("%.2f", beverage3.cost()));
    }
}
```

**输出：**
```
Espresso $1.99
House Blend Coffee, Mocha, Milk $1.19
Espresso, Mocha, Mocha, Milk $2.49
```
这个例子完美地展示了装饰器模式的灵活性。你可以像套娃一样，一层一层地将功能包装上去，而且顺序和数量都可以自由组合。

### 五、在 Java 中的应用

最经典的例子就是 **Java I/O 流**。

*   `InputStream` 是抽象组件。
*   `FileInputStream`, `ByteArrayInputStream` 是具体组件，代表了原始的数据源。
*   `FilterInputStream` 是抽象装饰器，它持有一个 `InputStream`。
*   `BufferedInputStream`, `DataInputStream` 是具体装饰器。

当你写下这样的代码时，你就在使用装饰器模式：
```java
// FileInputStream 是具体组件 (被装饰者)
InputStream in = new FileInputStream("myFile.txt");

// BufferedInputStream 是一个具体装饰器，为输入流添加了缓冲功能
in = new BufferedInputStream(in);

// DataInputStream 是另一个具体装饰器，添加了读取基本数据类型的功能
DataInputStream dis = new DataInputStream(in);

// 读取一个 int
int data = dis.readInt();
```

### 总结

*   **优点**：
    *   **高度灵活性**：比继承更灵活，可以在运行时动态添加或删除功能。
    *   **避免类爆炸**：可以用少量类实现各种功能组合，而不是为每种组合创建一个子类。
    *   **开闭原则**：对扩展开放（可以添加新的装饰器），对修改关闭（无需修改现有代码）。
    *   **职责分离**：每个装饰器只关心自己的功能，符合单一职责原则。

*   **缺点**：
    *   **小对象过多**：使用装饰器模式会产生许多看上去类似的小对象，增加代码的复杂性。
    *   **排错困难**：对于一个被多层装饰的对象，追踪和调试其行为可能会变得比较困难。

总而言之，当你需要为一个对象动态地添加功能，并且这些功能可以自由组合时，装饰器模式是一个非常强大和优雅的解决方案。