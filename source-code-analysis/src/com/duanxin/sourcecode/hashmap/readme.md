### HashMap源码分析专题  
  
#### 基本介绍  
#### 详细分析  
* 重要的基本属性  
```java
/**
* 该表，初始化的第一次使用，并调整是必要的。 当分配，长度始终是二的幂。 
* 作为HashMap的基本存储单元，俗称桶
*/
transient Node<K,V>[] table;
/**
* 该容器中当前存储的key-value数量
*/
transient int size;
/**
* 记录此容器结构上的修改（例如，刷新）。
*/
transient int modCount;
/**
* 下一个要调整大小的大小值（容量*负载系数）
* 当大于当前值时自动执行扩容操作的下一次值
*/
int threshold;
/**
* 自定义的加载因子
*/
final float loadFactor;
```
* 重要的基本方法  
* 重要的内部类  
* 添加  
* 删除  
* 获取  
* 更改  
* 扩容