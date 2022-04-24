package jpql;

import javax.persistence.*;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpqltest");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("Rylah");
            member.setAge(100);
            em.persist(member);

            // 기본 쿼리 시작
            // 반환타입이 명확한 경우
            TypedQuery<Member> query1 = em.createQuery("select m from Member as m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username, m.age from Member as m", String.class);

            // 반환타입이 명확하지 않은 경우
            Query query3 = em.createQuery("select m.username, m.age from Member as m");


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
