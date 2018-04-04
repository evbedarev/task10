package homework.bedarev.task_03;

public class StorageValue {
    private Class clazz;
    private Object value;

    public StorageValue(Class clazz, Object value) {
        this.clazz = clazz;
        this.value = value;
    }

    public Class getClazz() {
        return clazz;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StorageValue) {
            StorageValue storageValue = StorageValue.class.cast(obj);
            return storageValue.getClazz().cast(storageValue.getValue()).equals(this.getClazz().cast(this.getValue()));
        }
        return false;
    }
}
