public class SingletonFactory {
    private Singleton instance;
    public Singleton getInstance() {
        if(null == instance) {
            synchronized (this) {
                if(null == instance) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}