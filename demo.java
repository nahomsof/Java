
class Student{
    void display(){
        System.out.println("This is student:");
    }
}
class memo extends Student{
    
    void display(){
        System.out.println("This is memo");
        
    }
}

public class demo {
    public void main(String[] args){
            Student s = new Student();
            memo m = new memo();
    }
    
}
