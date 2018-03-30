package homework.bedarev.task_03;

public class User implements IUser {

    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void rename(String newName) {
        if(!newName.equals(name)){
            this.name = newName;
        }
    }
}
