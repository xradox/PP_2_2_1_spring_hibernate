package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("select u from User u", User.class);
      return query.getResultList();
   }

   @Override
   public User getUserByCarParams(String model, int series) {
      TypedQuery<Car> query = sessionFactory.getCurrentSession()
              .createQuery("select c from Car c where c.model = :model and c.series = :series", Car.class)
              .setParameter("model", model).setParameter("series", series);
      List<Car> cars = query.getResultList();
      if (!cars.isEmpty()) {
         return cars.get(0).getUser();
      }
      return null;
   }

}
