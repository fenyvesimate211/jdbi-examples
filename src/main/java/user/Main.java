package user;
import org.jdbi.v3.core.*;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());

        try(Handle handle = jdbi.open()){
            UserDao dao = handle.attach(UserDao.class);
            dao.createTable();

            User felhasznalo = User.builder()
                    .name("James Bond")
                    .username("007")
                    .password("Secret")
                    .email("james.bond@spyagent.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1920-11-11"))
                    .enabled(true)
                    .build();

            User felhasznalo2 = User.builder()
                    .name("Bond James")
                    .username("008")
                    .password("Secret123")
                    .email("bond.james@spyagent.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1817-11-11"))
                    .enabled(true)
                    .build();

            System.out.println(dao.findById(dao.insert(felhasznalo)));
            System.out.println(dao.findByUsername(felhasznalo.getUsername()));

            System.out.println(dao.findById(dao.insert(felhasznalo2)));
            System.out.println(dao.findByUsername(felhasznalo2.getUsername()));

            dao.list().forEach(System.out::println);

            dao.delete(felhasznalo);
            dao.delete(felhasznalo2);

        }
    }
}
