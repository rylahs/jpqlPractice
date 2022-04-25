package jpql;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpqltest");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            // Input
            for (int i = 0; i < 1000; i++) {
                Member member = new Member();
                member.setUsername("Rylah " + i);
                member.setAge(100 + i);
                em.persist(member);
            }
            em.flush();
            em.clear();
            /* 기본 쿼리 시작
            // 반환타입이 명확한 경우
            TypedQuery<Member> query1 = em.createQuery("select m from Member as m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member as m where m.id = 5L", String.class);
            TypedQuery<Member> query4 = em.createQuery("select m from Member as m where m.username = :username", Member.class);

            // 반환타입이 명확하지 않은 경우
            Query query3 = em.createQuery("select m.username, m.age from Member as m");


            // 결과 여러개 혹은 null
            System.out.println("=============== Query 1 ================");
            List<Member> resultList = query1.getResultList();
            for (Member member1 : resultList) {
                System.out.println("member.username = " + member1.getUsername());
                System.out.println("member.age = " + member1.getAge());
            }
            System.out.println("=============== Query 1 ================");
            System.out.println("=============== Query 2 ================");
            List<String> resultList1 = query2.getResultList();
            for (String s : resultList1) {
                System.out.println("s = " + s);
            }
            System.out.println("=============== Query 2 ================");

            System.out.println("=============== Query 4 ================");
            query4.setParameter("username", "Rylah 10");
            Member singleResult = query4.getSingleResult();
            System.out.println("singleResult = " + singleResult.getUsername());
            System.out.println("singleResult = " + singleResult.getAge());
            System.out.println("=============== Query 4 ================");
            // Chaining
            System.out.println("=============== Query 5 ================");
            List<Member> query5Result = em.createQuery("select m from Member as m where m.age > :age", Member.class)
                    .setParameter("age", 1080)
                    .getResultList();
            for (Member member : query5Result) {
                System.out.println("member.name : " + member.getUsername());
                System.out.println("member.age : " + member.getAge());
            }
            System.out.println("=============== Query 5 ================");
            // 기본쿼리 종료 */
            /* 프로젝션 시작

            // 엔티티 프로젝션
            List<Member> resList = em.createQuery("select m from Member as m ", Member.class).getResultList();

            Member findMember = resList.get(0);
            findMember.setAge(1);


            // Inner Join이 묵시적으로 이뤄짐
            //
            // 묵시적
            List<Team> teamListImplicit = em.createQuery("select m.team from Member as m", Team.class).getResultList();

            // 명시적
            List<Team> teamListExcplicit = em.createQuery("select m.team from Member as m join m.team t", Team.class).getResultList();


            // 임베디드 타입 프로젝션
            List<Address> orderResult = em.createQuery("select o.address from Order as o ", Address.class).getResultList();


            // 스칼라 타입 프로젝션
            List resultScar = em.createQuery("select m.username, m.age from Member as m").getResultList();

            Object o = resultScar.get(0);
            Object[] res = (Object[]) o;
            System.out.println("res[0] = " + res[0]);
            System.out.println("res[0] = " + res[1]);



            // DTO를 통한 조회
            // new를 통해서 새로 패키지명을 적고 해야된다. (생성자 호출)
            // 다만, querydsl에서 더 편리하게 작성 가능 (컴파일러에서 오류 검출)
            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList();

            MemberDTO memberDTO = resultList.get(10);
            System.out.println("memberDTO = " + memberDTO.getUsername());
            System.out.println("memberDTO = " + memberDTO.getAge());
            */
            /* 페이징 시작 */
            // setFirstResult(int startPosition) : 조회 시작 위치 (0부터 시작)
            // setMaxResult(int maxResult) : 조회할 데이터 수


            List<Member> result = em.createQuery("select m from Member as m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();
            System.out.println("result.size = " + result.size());
            for (Member memberList : result) {
                System.out.println("memberList = " + memberList);
            }

            /**/
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
