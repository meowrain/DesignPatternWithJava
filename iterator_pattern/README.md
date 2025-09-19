
好的，我们来详细介绍一下迭代器模式 (Iterator Pattern)。

### 一、什么是迭代器模式？

**核心思想：** 提供一种**顺序访问**一个聚合对象（比如列表、数组、集合）中各个元素的方法，而**无需暴露**该对象的内部表示。

简单来说，迭代器模式就是将**遍历**（或称“迭代”）这个行为从集合对象本身中分离出来，封装到一个独立的“迭代器”对象中。这样一来，客户端代码只需要与迭代器交互，就可以遍历集合，而不需要知道集合内部是如何存储数据的（是数组？链表？还是哈希表？）。

这就好比看电视。你不需要知道电视机内部复杂的电路是如何工作的，你只需要使用遥控器（迭代器），通过“下一个频道”和“上一个频道”按钮（`next()`、`hasNext()` 方法），就可以浏览所有的频道（集合中的元素）。遥控器为你屏蔽了电视内部的复杂性。

### 二、为什么需要迭代器模式？

1.  **封装集合内部结构**：客户端代码不需要关心集合的具体实现。例如，今天你用 `ArrayList` 存储数据，明天为了性能优化换成了 `LinkedList`。如果客户端代码直接依赖于 `ArrayList` 的索引 `for (int i=0; ...)` 来遍历，那么更换实现后，所有客户端代码可能都需要修改。而使用迭代器，客户端代码保持不变，因为迭代器提供了统一的遍历接口。

2.  **提供统一的遍历接口**：无论集合是 `List`、`Set` 还是 `Map`，我们都可以通过 `iterator()` 方法获得一个迭代器，并使用相同的 `hasNext()` 和 `next()` 方法来遍历它们，这大大简化了客户端代码。

3.  **支持多种遍历方式**：一个集合可以提供多种不同的迭代器。例如，一个树结构可以提供前序遍历迭代器、中序遍历迭代器和后序遍历迭代器。

4.  **职责分离**：集合对象（聚合）的核心职责是存储和管理数据。遍历的职责则交给迭代器。这符合**单一职责原则**，使得代码更加清晰，易于维护和扩展。

### 三、迭代器模式的结构

迭代器模式通常包含以下四个主要角色：

1.  **抽象迭代器 (Iterator)**：
    *   定义了访问和遍历元素所需的操作接口。
    *   通常包含：`hasNext()` (判断是否还有下一个元素), `next()` (获取下一个元素并移动指针), `remove()` (移除当前元素，可选)。

2.  **具体迭代器 (Concrete Iterator)**：
    *   实现了抽象迭代器接口。
    *   负责具体的遍历逻辑，它需要记录当前遍历的位置。
    *   它持有一个对具体聚合对象的引用，以便能够访问其中的数据。

3.  **抽象聚合 (Aggregate)**：
    *   定义了创建相应迭代器对象的接口，通常是一个名为 `createIterator()` 或 `iterator()` 的方法。

4.  **具体聚合 (Concrete Aggregate)**：
    *   实现了抽象聚合接口。
    *   它是一个具体的集合类，存储了一组元素，并能创建出对应的具体迭代器实例。

**结构图如下：**

```
+----------------+       creates       +--------------------+
|   Aggregate    |------------------->|      Iterator      |
|----------------|                     |--------------------|
| +createIterator() |                     | +hasNext()         |
+----------------+                     | +next()            |
        ^                              +--------------------+
        |                                      ^
        | inherits                             | inherits
        |                                      |
+--------------------+                     +------------------------+
| ConcreteAggregate  |       creates       |   ConcreteIterator     |
|--------------------|------------------->|------------------------|
| -elements          |                     | -aggregate: ConcreteAggregate |
| +createIterator()  |                     | -cursor: int           |
+--------------------+                     | +hasNext()             |
                                           | +next()                |
                                           +------------------------+
```

### 四、Java 代码示例

我们用一个简单的书架 (`BookShelf`) 来作为例子。

**1. 定义抽象迭代器 (Iterator)**
*Java 自带了 `java.util.Iterator`，我们直接使用它。但为了演示，我们也可以自己定义一个。*

```java
// 抽象迭代器接口
public interface Iterator<E> {
    boolean hasNext();
    E next();
}
```

**2. 定义抽象聚合 (Aggregate)**

```java
// 抽象聚合接口
public interface Aggregate<E> {
    Iterator<E> createIterator();
}
```

**3. 创建具体聚合 (Concrete Aggregate)**
*书架，内部用一个 List 来存储书籍。*

```java
import java.util.ArrayList;
import java.util.List;

// 具体聚合类：书架
public class BookShelf implements Aggregate<Book> {
    private List<Book> books;

    public BookShelf() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public Book getBookAt(int index) {
        return books.get(index);
    }

    public int getLength() {
        return books.size();
    }

    @Override
    public Iterator<Book> createIterator() {
        return new BookShelfIterator(this); // 创建并返回自己的迭代器
    }
}
```

**4. 创建具体迭代器 (Concrete Iterator)**
*书架的迭代器，负责遍历书架上的书。*

```java
// 具体迭代器类：书架迭代器
public class BookShelfIterator implements Iterator<Book> {
    private BookShelf bookShelf;
    private int index; // 记录当前遍历的位置

    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        // 如果当前索引小于书的总数，说明还有下一本
        return index < bookShelf.getLength();
    }

    @Override
    public Book next() {
        // 获取当前索引的书，然后将索引后移
        Book book = bookShelf.getBookAt(index);
        index++;
        return book;
    }
}

// 书籍类
class Book {
    private String name;
    public Book(String name) { this.name = name; }
    public String getName() { return name; }
}
```

**5. 客户端使用**

```java
public class org.designpattern.Main {
    public static void main(String[] args) {
        BookShelf bookShelf = new BookShelf();
        bookShelf.addBook(new Book("设计模式"));
        bookShelf.addBook(new Book("Java核心技术"));
        bookShelf.addBook(new Book("代码整洁之道"));

        // 获取迭代器
        Iterator<Book> it = bookShelf.createIterator();

        // 使用迭代器遍历，客户端完全不知道书架内部是用ArrayList还是数组实现的
        System.out.println("开始遍历书架：");
        while (it.hasNext()) {
            Book book = it.next();
            System.out.println(book.getName());
        }
    }
}
```

**输出：**
```
开始遍历书架：
设计模式
Java核心技术
代码整洁之道
```

### 五、在 Java 中的应用

迭代器模式在 Java 集合框架 (Java Collections Framework) 中得到了广泛的应用。

*   所有 `Collection` 接口的实现类（如 `ArrayList`, `LinkedList`, `HashSet`）都实现了 `Iterable` 接口。
*   `Iterable` 接口中只有一个方法 `iterator()`，它就相当于我们例子中的 `createIterator()`，返回一个 `Iterator` 实例。
*   `java.util.Iterator` 就是标准的抽象迭代器接口。

因此，在 Java 中，你可以这样写：
```java
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");

Iterator<String> iterator = list.iterator(); // 获取迭代器
while (iterator.hasNext()) {
    String element = iterator.next();
    System.out.println(element);
}
```

更方便的是，Java 的 `for-each` 循环（增强 for 循环）其实就是一个语法糖，其底层实现就是依赖迭代器模式。

```java
// 这段代码在编译后，几乎就等同于上面使用 while 循环的代码
for (String element : list) {
    System.out.println(element);
}
```
只要一个类实现了 `Iterable` 接口，它就可以被用于 `for-each` 循环。

### 总结

*   **优点**：
    *   **封装性**：隐藏了集合的内部实现。
    *   **单一职责**：将遍历逻辑从集合中分离出来。
    *   **通用性**：为不同的集合提供了统一的遍历接口。
    *   **可扩展性**：可以为同一个集合轻松添加新的遍历方式（例如倒序遍历）。

*   **缺点**：
    *   对于简单的遍历，可能会引入额外的类，增加了一点复杂性。但在大多数情况下，其带来的好处远大于这点复杂性。

迭代器模式是行为型设计模式中非常基础且重要的一个，它深刻体现了面向对象设计中的封装和委托原则。