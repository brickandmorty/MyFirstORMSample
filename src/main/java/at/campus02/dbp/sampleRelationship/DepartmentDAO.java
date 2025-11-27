package at.campus02.dbp.sampleRelationship;

import at.campus02.dbp.HibernateUtil;
import at.campus02.dbp.SchrankDAO;
import at.campus02.dbp.model.Schrank;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DepartmentDAO {


        public Department findById(Long id) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                return session.get(Department.class, id);
            }
        }

        public Department save(Department department) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction tx = session.beginTransaction();
                session.persist(department);
                tx.commit();
                return department;
            }
        }
    public Employee save(Employee employee) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(employee);
            tx.commit();
            return employee;
        }
    }

    public Department findByIdWithEmployees(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select d from Department d " +
                    "left join fetch d.employees " +
                    "where d.id = :id";
            return session.createQuery(hql, Department.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }


    }

