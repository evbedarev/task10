//package homework.bedarev.task_03;
//
//import java.lang.reflect.Proxy;
//
//public class DynamicProxyTest {
//    public static void main(String[] args) {
//        User user = new User();
//        IUser userProxy = (IUser) Proxy.newProxyInstance(
//                User.class.getClassLoader(),
//                User.class.getInterfaces(),
//                new CacheInvocationHandler(user));
//        userProxy.setName("Гриша");
//        System.out.println(User.class.getInterfaces()[0]);
//        user.setName("Papa");
//        System.out.println(user.getName());
////        String h  = userProxy.getName() ;
////        System.out.println("Get name proxy: " + h);
////        System.out.println("Get name user: " + user.getName());
//
////        userProxy.rename("Vasiya");
////        System.out.println("Get name user: " + user.getName());
////        System.out.println("Get name proxy: " + userProxy.getName());
//    }
//}
