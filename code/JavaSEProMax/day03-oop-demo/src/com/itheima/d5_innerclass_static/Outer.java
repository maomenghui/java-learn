package com.itheima.d5_innerclass_static;

/**
 * 外部类
 */
public class Outer {

    public static int a = 100;
    private String hobby;
    /**
     * 学习静态成员内部类
     */
    public static class Inner{
        private String name;
        private int age;
        public static String schoolName;

        public void show(){
            System.out.println("名称：" + name);
            System.out.println(a);
            Outer o = new Outer();
            System.out.println(o.hobby);
        }

        public Inner() {
        }

        public Inner(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
