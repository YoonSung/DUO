package tdd.duo.domain;

import javax.persistence.*;

/**
 * Created by yoon on 15. 3. 25..
 */
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String email;

    @Column
    private String name;
/* TODO
    enum SEX {

        MEN('m'),
        WOMEN('w');

        private final char charValue;

        SEX(char sex) {
            this.charValue = sex;
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition="ENUM('MEN', 'WOMEN')")
    private SEX sex;
*/
    @Column
    private int age;

    public User(){}

    public User(String email, String name, int age) {
        this.email = email;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
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
